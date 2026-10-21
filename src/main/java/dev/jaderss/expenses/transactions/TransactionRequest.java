package dev.jaderss.expenses.transactions;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Currency;

public record TransactionRequest(
        @NotNull @Positive Long userId,
        @NotBlank @Size(max = 200) String description,

        @NotNull @Positive @Digits(integer = 15, fraction = 4)
        BigDecimal amount,

        @NotNull Currency currency) {

    @AssertTrue(message = "amount must not have more decimal places than the currency allows")
    boolean isAmountInCurrencyScale() {
        return amount == null
                || currency == null
                || amount.stripTrailingZeros().scale() <= Math.max(currency.getDefaultFractionDigits(), 0);
    }
}
