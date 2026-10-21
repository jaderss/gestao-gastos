package dev.jaderss.expenses.cards;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record CardSearch(@NotNull @CPF String cpf) {

    String digits() {
        return cpf.replaceAll("\\D", "");
    }

    @Override
    public String toString() {
        return "CardSearch[cpf=***]";
    }
}
