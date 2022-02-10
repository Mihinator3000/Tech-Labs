package main.banks.entities.banks;

import java.math.BigDecimal;

public class BankBuilder {
    private final Bank bank;

    public BankBuilder() {
        bank = new Bank();
    }

    public BankBuilder setMaxSumForDubiousClients(BigDecimal maxSum) {
        bank.setMaxSumForDubiousClients(maxSum);
        return this;
    }

    public BankBuilder setCreditCommission(BigDecimal commission) {
        bank.setCreditCommission(commission);
        return this;
    }

    public BankBuilder setCreditLimit(BigDecimal limit) {
        bank.setCreditLimit(limit);
        return this;
    }

    public BankBuilder setDebitInterest(BigDecimal interest) {
        bank.setDebitInterest(interest);
        return this;
    }

    public Bank build() {
        return bank;
    }
}
