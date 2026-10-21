package dev.jaderss.expenses.cards;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

@WebMvcTest(CardController.class)
class CardControllerTest {

    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private CardRepository repository;

    @ParameterizedTest
    @ValueSource(strings = {"12345678909", "123.456.789-09"})
    void returnsMaskedCardsForTheHolder(String cpf) {
        given(repository.findByHolderCpfOrderById("12345678909"))
                .willReturn(
                        List.of(new Card(1L, "Ada Example", "12345678909", "VISA", "4242", LocalDate.of(2029, 7, 31))));

        assertThat(search(cpf)).hasStatusOk().bodyJson().isEqualTo("""
                        [{"id": null, "userId": 1, "holderName": "Ada Example", "brand": "VISA",
                          "number": "**** **** **** 4242", "expiry": "2029-07"}]
                        """, JsonCompareMode.STRICT);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678900", "1234567890", "11111111111", "abc"})
    void rejectsInvalidCpfsWithoutQuerying(String cpf) {
        assertThat(search(cpf))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.errors.cpf")
                .isEqualTo("invalid Brazilian individual taxpayer registry number (CPF)");
        verifyNoInteractions(repository);
    }

    @Test
    void requiresACpf() {
        assertThat(mvc.post()
                        .uri("/cards/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.errors.cpf")
                .isEqualTo("must not be null");
    }

    @Test
    void keepsTheCpfOutOfToString() {
        assertThat(new CardSearch("12345678909")).hasToString("CardSearch[cpf=***]");
    }

    private MvcTestResult search(String cpf) {
        return mvc.post()
                .uri("/cards/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cpf\": \"%s\"}".formatted(cpf))
                .exchange();
    }
}
