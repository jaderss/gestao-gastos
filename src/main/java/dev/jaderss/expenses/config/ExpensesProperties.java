package dev.jaderss.expenses.config;

import jakarta.validation.constraints.NotNull;
import java.time.ZoneId;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("expenses")
public record ExpensesProperties(@NotNull ZoneId timeZone) {}
