package main.banks.entities.accounts;

import main.banks.entities.accounts.balance.BalanceState;
import main.banks.entities.banks.Bank;
import main.banks.entities.clients.Client;
import main.banks.tools.BanksException;

import java.math.BigDecimal;


public class CreditAccount extends Account {

    public CreditAccount(Bank bank, Client client, BigDecimal balance) throws BanksException {
        super(bank, client, balance);
    }

    @Override
    public final void setBalance(BigDecimal balance) throws BanksException {
        if (balance.compareTo(bank.getCreditLimit()) < 0)
            throw new BanksException("Account balance can't be less than " + bank.getCreditLimit());

        super.setBalance(balance);
    }

    @Override
    public void accrualOfPercents(BalanceState balanceState, int days) {
        try {
            if (balanceState.getBalance().compareTo(BigDecimal.ZERO) < 0)
                setBalance(getBalance().subtract(BigDecimal.valueOf(days)
                        .multiply(bank.getCreditCommission())));
        }
        catch (BanksException e) {
            balance = bank.getCreditLimit();
        }
    }
}
