package dev.jaderss.expenses.transactions;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Range.Bound;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private static final Sort OLDEST_FIRST = Sort.by("recordedAt", "id");

    private final TransactionRepository repository;
    private final MongoTemplate mongo;
    private final Clock clock;

    public TransactionService(TransactionRepository repository, MongoTemplate mongo, Clock clock) {
        this.repository = repository;
        this.mongo = mongo;
        this.clock = clock;
    }

    public Transaction record(TransactionRequest request) {
        return repository.save(new Transaction(
                null,
                request.userId(),
                request.description().strip(),
                request.amount(),
                request.currency().getCurrencyCode(),
                null,
                clock.instant()));
    }

    public Transaction get(String id) {
        return repository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
    }

    public Transaction categorize(String id, String category) {
        return mongo.update(Transaction.class)
                .matching(where("id").is(id))
                .apply(Update.update("category", category.strip()))
                .withOptions(FindAndModifyOptions.options().returnNew(true))
                .findAndModify()
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    public List<Transaction> onDate(Long userId, LocalDate date) {
        Instant start = date.atStartOfDay(clock.getZone()).toInstant();
        Instant end = date.plusDays(1).atStartOfDay(clock.getZone()).toInstant();
        return repository.findByUserIdAndRecordedAtBetween(userId, Range.rightOpen(start, end), OLDEST_FIRST);
    }

    public List<Transaction> recent(Long userId, int days) {
        Instant since = clock.instant().minus(Duration.ofDays(days));
        return repository.findByUserIdAndRecordedAtBetween(
                userId, Range.of(Bound.inclusive(since), Bound.unbounded()), OLDEST_FIRST);
    }

    public List<String> categories(Long userId, String prefix) {
        return mongo
                .query(Transaction.class)
                .distinct("category")
                .matching(where("userId").is(userId).and("category").regex("^" + Pattern.quote(prefix.strip()), "i"))
                .as(String.class)
                .all()
                .stream()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
    }
}
