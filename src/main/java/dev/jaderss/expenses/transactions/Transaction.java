package dev.jaderss.expenses.transactions;

import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("transactions")
@CompoundIndex(name = "user_recorded", def = "{'userId': 1, 'recordedAt': 1}")
public record Transaction(
        @Id String id,
        Long userId,
        String description,
        BigDecimal amount,
        String currency,
        String category,
        Instant recordedAt) {}
