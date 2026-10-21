package dev.jaderss.expenses.cards;

import java.util.List;
import org.springframework.data.repository.Repository;

public interface CardRepository extends Repository<Card, Long> {

    List<Card> findByHolderCpfOrderById(String holderCpf);
}
