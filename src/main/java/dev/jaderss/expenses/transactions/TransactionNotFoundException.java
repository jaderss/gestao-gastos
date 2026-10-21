package dev.jaderss.expenses.transactions;

public class TransactionNotFoundException extends RuntimeException {

    TransactionNotFoundException(String id) {
        super("Transaction %s does not exist".formatted(id));
    }
}
