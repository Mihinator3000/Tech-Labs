package main.banks.entities.transaction;

import main.banks.entities.accounts.Account;
import main.banks.tools.BanksException;

import java.math.BigDecimal;

public abstract class AbstractTransaction {

    private boolean cancelled;

    protected final Account account;
    protected final BigDecimal amount;

    protected AbstractTransaction(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public abstract AbstractTransaction execute() throws BanksException;

    public void cancel() throws BanksException {
        if (this.cancelled)
            throw new BanksException("Transaction is already cancelled");

        this.cancelled = true;
    }
}
