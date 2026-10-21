package dev.jaderss.expenses.transactions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    private static final Transaction COFFEE = new Transaction(
            "tx-1", 1L, "Coffee", new BigDecimal("8.90"), "BRL", null, Instant.parse("2026-10-21T15:00:00Z"));

    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private TransactionService service;

    @Test
    void recordsATransactionAndPointsToIt() {
        given(service.record(any())).willReturn(COFFEE);

        assertThat(mvc.post()
                        .uri("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"userId": 1, "description": "Coffee", "amount": 8.90, "currency": "BRL"}
                                """))
                .hasStatus(HttpStatus.CREATED)
                .hasHeader(HttpHeaders.LOCATION, "/transactions/tx-1")
                .bodyJson()
                .isEqualTo("""
                        {"id": "tx-1", "userId": 1, "description": "Coffee", "amount": 8.90, "currency": "BRL",
                         "category": null, "recordedAt": "2026-10-21T15:00:00Z"}
                        """, JsonCompareMode.STRICT);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
                    {"description": "Coffee", "amount": 8.90, "currency": "BRL"}             | userId                | must not be null
                    {"userId": 1, "description": " ", "amount": 8.90, "currency": "BRL"}     | description           | must not be blank
                    {"userId": 1, "description": "Coffee", "amount": 0, "currency": "BRL"}   | amount                | must be greater than 0
                    {"userId": 1, "description": "Coffee", "amount": 8.905, "currency": "BRL"} | amountInCurrencyScale | amount must not have more decimal places than the currency allows
                    {"userId": 1, "description": "Coffee", "amount": 8.90}                   | currency              | must not be null
                    """)
    void rejectsInvalidTransactions(String body, String field, String message) {
        assertThat(mvc.post()
                        .uri("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.errors." + field)
                .isEqualTo(message);
        verifyNoInteractions(service);
    }

    @Test
    void reportsAnUnknownTransactionAsNotFound() {
        given(service.get("missing")).willThrow(new TransactionNotFoundException("missing"));

        assertThat(mvc.get().uri("/transactions/missing"))
                .hasStatus(HttpStatus.NOT_FOUND)
                .bodyJson()
                .extractingPath("$.detail")
                .isEqualTo("Transaction missing does not exist");
    }

    @Test
    void categorizes() {
        given(service.categorize("tx-1", "Groceries")).willReturn(COFFEE);

        assertThat(mvc.put()
                        .uri("/transactions/tx-1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\": \"Groceries\"}"))
                .hasStatusOk();
    }

    @Test
    void rejectsLongCategories() {
        assertThat(mvc.put()
                        .uri("/transactions/tx-1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\": \"%s\"}".formatted("x".repeat(51))))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.errors.category")
                .isEqualTo("size must be between 0 and 50");
    }

    @Test
    void listsTransactionsOfADay() {
        given(service.onDate(1L, LocalDate.of(2026, 10, 21))).willReturn(List.of(COFFEE));

        assertThat(mvc.get().uri("/transactions?userId=1&date=2026-10-21"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[0].id")
                .isEqualTo("tx-1");
    }

    @Test
    void listsRecentTransactions() {
        given(service.recent(1L, 7)).willReturn(List.of(COFFEE));

        assertThat(mvc.get().uri("/transactions/recent?userId=1&days=7"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[0].id")
                .isEqualTo("tx-1");
    }

    @Test
    void suggestsCategories() {
        given(service.categories(1L, "gro")).willReturn(List.of("Groceries"));

        assertThat(mvc.get().uri("/transactions/categories?userId=1&prefix=gro"))
                .hasStatusOk()
                .bodyJson()
                .isEqualTo("[\"Groceries\"]");
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
                    /transactions/recent?userId=1&days=0          | days   | must be greater than or equal to 1
                    /transactions/recent?userId=1&days=367        | days   | must be less than or equal to 366
                    /transactions/recent?userId=-1&days=7         | userId | must be greater than 0
                    /transactions/categories?userId=1&prefix=     | prefix | must not be blank
                    """)
    void rejectsInvalidQueries(String uri, String parameter, String message) {
        assertThat(mvc.get().uri(uri))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.errors." + parameter)
                .isEqualTo(message);
        verifyNoInteractions(service);
    }

    @Test
    void rejectsMalformedDates() {
        assertThat(mvc.get().uri("/transactions?userId=1&date=21/10/2026")).hasStatus(HttpStatus.BAD_REQUEST);
        verifyNoInteractions(service);
    }
}
