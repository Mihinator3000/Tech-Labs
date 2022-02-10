package main.banks.entities.transaction;

import main.banks.entities.accounts.Account;
import main.banks.tools.BanksException;

import java.math.BigDecimal;

public abstract class AbstractTransaction {

    private final Cancellable cancellable;

    protected final Account account;
    protected final BigDecimal amount;

    protected AbstractTransaction(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;

        cancellable = new Cancellable();
    }

    public Account getAccount() {
        return account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isCancelled() {
        return cancellable.isCancelled();
    }

    public abstract AbstractTransaction execute() throws BanksException;

    public void cancel() throws BanksException {
        cancellable.cancel();
    }
}
