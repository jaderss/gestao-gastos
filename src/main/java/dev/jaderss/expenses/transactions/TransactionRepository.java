package dev.jaderss.expenses.transactions;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime from);

    List<Transaction> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime from, LocalDateTime to);

    List<Transaction> findByCategoryStartingWith(String prefix);
}
