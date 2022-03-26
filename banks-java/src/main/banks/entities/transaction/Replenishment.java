package main.banks.entities.transaction;

import main.banks.entities.accounts.Account;
import main.banks.tools.BanksException;

import java.math.BigDecimal;

public class Replenishment extends AbstractTransaction {

    public Replenishment(Account account, BigDecimal amount) {
        super(account, amount);
    }

    @Override
    public AbstractTransaction execute() throws BanksException {
        account.getClient().setBalance(account.getClient().getBalance().subtract(amount));
        account.setBalance(account.getBalance().add(amount));
        return this;
    }

    @Override
    public void cancel() throws BanksException {
        super.cancel();
        new Withdrawal(account, amount).execute();
    }
}
