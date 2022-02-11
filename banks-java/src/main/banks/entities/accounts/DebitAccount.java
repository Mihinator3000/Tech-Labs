package main.banks.entities.accounts;

import main.banks.entities.accounts.balance.BalanceState;
import main.banks.entities.banks.Bank;
import main.banks.entities.clients.Client;
import main.banks.tools.BanksException;

import java.math.BigDecimal;

public class DebitAccount extends Account {

    public DebitAccount(Bank bank, Client client, BigDecimal balance) throws BanksException {
        super(bank, client, balance);
    }

    @Override
    public final void setBalance(BigDecimal balance) throws BanksException {
        if (balance.compareTo(BigDecimal.ZERO) < 0)
            throw new BanksException("Account balance can't be less than 0");

        super.setBalance(balance);
    }

    @Override
    public void accrualOfPercents(BalanceState balanceState, int days) {
        try {
            setBalance(balance.add(balanceState.getBalance()
                    .multiply(bank.getDebitInterest())
                    .multiply(BigDecimal.valueOf(days / 365.0))));
        } catch (BanksException ignored) { }
    }

    @Override
    public String getType() {
        return "Debit";
    }
}
