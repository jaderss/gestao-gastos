package dev.jaderss.expenses.transactions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }

    public Page<Transaction> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Transaction> findById(String id) {
        return repository.findById(id);
    }

    public List<Transaction> findRecentByUser(Long userId, Integer days) {
        LocalDate from = LocalDate.now().minusDays(days);
        return repository.findByUserIdAndCreatedAtAfter(userId, LocalDateTime.from(from));
    }

    public List<Transaction> findByUserAndDate(Long userId, LocalDate date) {
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.atTime(LocalTime.MAX);
        return repository.findByUserIdAndCreatedAtBetween(userId, from, to);
    }

    public Transaction categorize(String id, String category) {
        Transaction transaction = repository.findById(id).orElseThrow();
        transaction.categorize(category);
        transaction = repository.save(transaction);
        return transaction;
    }

    public List<String> findCategories(String prefix) {
        return repository.findByCategoryStartingWith(prefix).stream()
                .map(Transaction::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }
}
