package main.banks.entities.accounts;

import main.banks.entities.accounts.balance.BalanceState;
import main.banks.entities.banks.Bank;
import main.banks.entities.clients.Client;
import main.banks.enums.AccountTypes;
import main.banks.tools.BanksException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Account {

    private final UUID id;
    private final Client client;
    protected final Bank bank;

    private boolean notifyClient;

    protected List<BalanceState> history;
    protected BigDecimal balance;

    protected Account(Bank bank, Client client, BigDecimal balance) throws BanksException {
        this.bank = bank;
        this.client = client;
        this.balance = BigDecimal.ZERO;
        id = UUID.randomUUID();
        history = new ArrayList<>();

        setBalance(balance);
    }

    public UUID getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) throws BanksException {
        this.balance = balance;
        history.add(new BalanceState(balance, bank.getTimeProvider().now()));
    }

    public void setNotifyClient(boolean notifyClient) {
        this.notifyClient = notifyClient;
    }

    public boolean getNotifyClient() {
        return notifyClient;
    }

    public List<BalanceState> getHistory() {
        return history;
    }

    public boolean isInstanceOf(AccountTypes type) {
        return switch (type) {
            case DEBIT -> this instanceof DebitAccount;
            case DEPOSIT -> this instanceof DepositAccount;
            case CREDIT -> this instanceof CreditAccount;
        };
    }

    public void NotifyClient(String notificationMessage) {
        if (notifyClient)
            client.addNotification(notificationMessage);
    }

    public abstract void accrualOfPercents(BalanceState balanceState, int days);

    public abstract String getType();
}
