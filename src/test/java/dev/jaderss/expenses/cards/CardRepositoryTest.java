package dev.jaderss.expenses.cards;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class CardRepositoryTest {

    @Autowired
    private CardRepository repository;

    @Test
    void findsEverySeededCardOfAHolderInIdOrder() {
        assertThat(repository.findByHolderCpfOrderById("12345678909"))
                .extracting(Card::getUserId, Card::getBrand, Card::getLastFour, Card::getExpiresOn)
                .containsExactly(
                        tuple(1L, "VISA", "4242", LocalDate.of(2029, 7, 31)),
                        tuple(1L, "MASTERCARD", "4444", LocalDate.of(2028, 2, 29)));
    }

    @Test
    void findsNothingForAnUnknownHolder() {
        assertThat(repository.findByHolderCpfOrderById("39053344705")).isEmpty();
    }
}
