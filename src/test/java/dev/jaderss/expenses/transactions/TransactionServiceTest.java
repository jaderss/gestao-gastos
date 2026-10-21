package dev.jaderss.expenses.transactions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dev.jaderss.expenses.TestcontainersConfiguration;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Currency;
import org.bson.types.Decimal128;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

@DataMongoTest
@Import({TestcontainersConfiguration.class, TransactionService.class, TransactionServiceTest.FixedClock.class})
class TransactionServiceTest {

    private static final Instant NOW = Instant.parse("2026-10-21T15:00:00Z");
    private static final Currency BRL = Currency.getInstance("BRL");

    @TestConfiguration(proxyBeanMethods = false)
    static class FixedClock {

        @Bean
        Clock clock() {
            return Clock.fixed(NOW, ZoneId.of("America/Sao_Paulo"));
        }
    }

    @Autowired
    private TransactionService service;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private MongoTemplate mongo;

    @BeforeEach
    void emptyCollection() {
        repository.deleteAll();
    }

    @Test
    void recordsAtTheClockTimeAndStoresTheAmountAsDecimal128() {
        Transaction recorded =
                service.record(new TransactionRequest(1L, "  Groceries  ", new BigDecimal("12.50"), BRL));

        assertThat(recorded.id()).isNotBlank();
        assertThat(service.get(recorded.id()))
                .isEqualTo(new Transaction(recorded.id(), 1L, "Groceries", new BigDecimal("12.50"), "BRL", null, NOW));
        assertThat(mongo.getCollection("transactions").find().first())
                .extracting(document -> document.get("amount"))
                .isEqualTo(new Decimal128(new BigDecimal("12.50")));
    }

    @Test
    void findsTransactionsOfALocalDayWithAHalfOpenRange() {
        save(1L, "2026-10-21T02:59:59.999Z");
        Transaction midnight = save(1L, "2026-10-21T03:00:00Z");
        Transaction lastMillisecond = save(1L, "2026-10-22T02:59:59.999Z");
        save(1L, "2026-10-22T03:00:00Z");
        save(2L, "2026-10-21T12:00:00Z");

        assertThat(service.onDate(1L, LocalDate.of(2026, 10, 21))).containsExactly(midnight, lastMillisecond);
    }

    @Test
    void findsTransactionsOfTheLastDaysUpToNow() {
        save(1L, NOW.minus(Duration.ofDays(7)).minusMillis(1).toString());
        Transaction oldest = save(1L, NOW.minus(Duration.ofDays(7)).toString());
        Transaction latest = save(1L, NOW.toString());
        save(2L, NOW.toString());

        assertThat(service.recent(1L, 7)).containsExactly(oldest, latest);
    }

    @Test
    void categorizesInPlace() {
        Transaction transaction = save(1L, NOW.toString());

        assertThat(service.categorize(transaction.id(), " Groceries "))
                .extracting(Transaction::category)
                .isEqualTo("Groceries");
        assertThat(service.get(transaction.id()).category()).isEqualTo("Groceries");
    }

    @Test
    void reportsUnknownTransactions() {
        assertThatThrownBy(() -> service.categorize("missing", "Groceries"))
                .isInstanceOf(TransactionNotFoundException.class)
                .hasMessage("Transaction missing does not exist");
        assertThatThrownBy(() -> service.get("missing")).isInstanceOf(TransactionNotFoundException.class);
    }

    @Test
    void suggestsTheUsersOwnCategoriesByCaseInsensitivePrefix() {
        categorized(1L, "Groceries");
        categorized(1L, "Groceries");
        categorized(1L, "gym");
        categorized(1L, "Rent");
        categorized(2L, "Gifts");
        save(1L, NOW.toString());

        assertThat(service.categories(1L, "G")).containsExactly("Groceries", "gym");
        assertThat(service.categories(1L, ".*")).isEmpty();
    }

    private Transaction save(Long userId, String recordedAt) {
        return repository.save(new Transaction(
                null, userId, "Coffee", new BigDecimal("8.90"), "BRL", null, Instant.parse(recordedAt)));
    }

    private void categorized(Long userId, String category) {
        service.categorize(save(userId, NOW.toString()).id(), category);
    }
}
