package main.banks.entities.transaction;

import main.banks.entities.accounts.Account;
import main.banks.tools.BanksException;

import java.math.BigDecimal;

public class Transaction extends AbstractTransaction {

    private final Account receiver;

    public Transaction(Account sender, Account receiver, BigDecimal amount) {
        super(sender, amount);

        this.receiver = receiver;
    }

    public Account getReceiver() {
        return receiver;
    }

    @Override
    public AbstractTransaction execute() throws BanksException {
        if (account == receiver)
            throw new BanksException("Can not transact to sender");

        account.setBalance(account.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));
        return this;
    }

    @Override
    public void cancel() throws BanksException {
        super.cancel();
        new Transaction(receiver, account, amount).execute();
    }
}
