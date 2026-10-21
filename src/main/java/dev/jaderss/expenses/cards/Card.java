package dev.jaderss.expenses.cards;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String holderName;

    private String holderCpf;

    private String brand;

    private String lastFour;

    private LocalDate expiresOn;

    protected Card() {}

    Card(Long userId, String holderName, String holderCpf, String brand, String lastFour, LocalDate expiresOn) {
        this.userId = userId;
        this.holderName = holderName;
        this.holderCpf = holderCpf;
        this.brand = brand;
        this.lastFour = lastFour;
        this.expiresOn = expiresOn;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getHolderName() {
        return holderName;
    }

    public String getBrand() {
        return brand;
    }

    public String getLastFour() {
        return lastFour;
    }

    public LocalDate getExpiresOn() {
        return expiresOn;
    }
}
