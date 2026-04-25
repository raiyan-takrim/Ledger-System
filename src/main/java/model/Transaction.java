package model;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    private UUID transactionId;
    private User user;
    private TransactionType type;
    private double amount;
    private Date transactionDate;
    private String description;

    public Transaction(User user, TransactionType type, double amount, Date transactionDate, String description) {
        this.transactionId = UUID.randomUUID();
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public User getUser() {
        return user;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public String getDescription() {
        return description;
    }
}