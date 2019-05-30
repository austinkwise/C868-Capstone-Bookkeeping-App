package main.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Transaction {
    private int transactionId;
    private String transactionType;
    private String transactionAccount;
    private LocalDateTime transactionDate;
    private String transactionCategory;
    private String transactionAmount;
    private String transactionDescription;

    public Transaction(int transactionId, String transactionType, String transactionAccount, LocalDateTime transactionDate, String transactionCategory, String transactionAmount, String transactionDescription) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.transactionAccount = transactionAccount;
        this.transactionDate = transactionDate;
        this.transactionCategory = transactionCategory;
        this.transactionAmount = transactionAmount;
        this.transactionDescription = transactionDescription;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionAccount() {
        return transactionAccount;
    }

    public void setTransactionAccount(String transactionAccount) {
        this.transactionAccount = transactionAccount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }
}
