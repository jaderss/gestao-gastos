package dev.jaderss.expenses.cards;

import java.time.YearMonth;

public record CardView(Long id, Long userId, String holderName, String brand, String number, YearMonth expiry) {

    static CardView of(Card card) {
        return new CardView(
                card.getId(),
                card.getUserId(),
                card.getHolderName(),
                card.getBrand(),
                "**** **** **** " + card.getLastFour(),
                YearMonth.from(card.getExpiresOn()));
    }
}
