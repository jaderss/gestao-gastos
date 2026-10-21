package dev.jaderss.expenses.transactions;

import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> findByUserIdAndRecordedAtBetween(Long userId, Range<Instant> recordedAt, Sort sort);
}
