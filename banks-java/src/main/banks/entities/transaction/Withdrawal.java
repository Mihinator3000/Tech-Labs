package main.banks.entities.transaction;

import main.banks.entities.accounts.Account;
import main.banks.tools.BanksException;

import java.math.BigDecimal;

public class Withdrawal extends AbstractTransaction {

    public Withdrawal(Account account, BigDecimal amount) {
        super(account, amount);
    }

    @Override
    public AbstractTransaction execute() throws BanksException {
        account.setBalance(account.getBalance().subtract(amount));
        account.getClient().setBalance(account.getClient().getBalance().add(amount));
        return this;
    }

    @Override
    public void cancel() throws BanksException {
        super.cancel();
        new Replenishment(account, amount).execute();
    }
}
