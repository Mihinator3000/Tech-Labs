package main.banks.entities.accounts;

import main.banks.entities.accounts.balance.BalanceState;
import main.banks.entities.banks.Bank;
import main.banks.entities.clients.Client;
import main.banks.enums.AccountTypes;
import main.banks.tools.BanksException;

import java.math.BigDecimal;

public class DepositAccount extends Account {

    private final BigDecimal interest;

    public DepositAccount(Bank bank, Client client, BigDecimal balance) throws BanksException {
        super(bank, client, balance);
        interest = getInterest(balance);
    }

    @Override
    public final void setBalance(BigDecimal balance) throws BanksException {
        if (balance.compareTo(this.balance) < 0)
            throw new BanksException("Account is not withdrawable");

        super.setBalance(balance);
    }

    public BigDecimal getInterest() {
        return interest;
    }

    @Override
    public void accrualOfPercents(BalanceState balanceState, int days) {
        try {
            setBalance(balance.add(balanceState.getBalance()
                    .multiply(interest)
                    .multiply(BigDecimal.valueOf(days / 365.0))));
        } catch (BanksException ignored) { }
    }

    @Override
    public AccountTypes getType() {
        return AccountTypes.DEPOSIT;
    }

    private static BigDecimal getInterest(BigDecimal amount) {
        return BigDecimal.valueOf(
                amount.compareTo(BigDecimal.valueOf(50000)) < 0 ? 3
                        : amount.compareTo(BigDecimal.valueOf(100000)) < 0 ? 3.5 : 4);
    }
}
