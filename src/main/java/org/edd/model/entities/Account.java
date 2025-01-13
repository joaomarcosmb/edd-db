package org.edd.model.entities;

import java.math.BigDecimal;

public class Account {
    private String accountNumber;
    private String customerId;
    private BigDecimal balance;
    private AccountType type;

    public enum AccountType {
        CHECKING, SAVINGS, INVESTMENT
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }
}
