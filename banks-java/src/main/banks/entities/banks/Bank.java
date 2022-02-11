package main.banks.entities.banks;

import main.banks.entities.accounts.Account;
import main.banks.entities.accounts.balance.BalanceState;
import main.banks.entities.accounts.creators.AccountCreator;
import main.banks.entities.accounts.creators.CreditAccountCreator;
import main.banks.entities.accounts.creators.DebitAccountCreator;
import main.banks.entities.accounts.creators.DepositAccountCreator;
import main.banks.entities.clients.Client;
import main.banks.entities.transaction.AbstractTransaction;
import main.banks.entities.transaction.Replenishment;
import main.banks.entities.transaction.Transaction;
import main.banks.entities.transaction.Withdrawal;
import main.banks.enums.AccountTypes;
import main.banks.providers.TimeProvider;
import main.banks.tools.BanksException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Bank {

    private TimeProvider timeProvider;
    private LocalDateTime previousAccrualTime;

    private BigDecimal maxSumForDubiousClients;
    private BigDecimal creditCommission;
    private BigDecimal creditLimit;
    private BigDecimal debitInterest;

    private final List<Account> accounts;
    private final List<AbstractTransaction> transactions;

    Bank() {
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public List<AbstractTransaction> getTransactions() {
        return transactions;
    }

    public BigDecimal getMaxSumForDubiousClients() {
        return maxSumForDubiousClients;
    }

    public void setMaxSumForDubiousClients(BigDecimal maxSumForDubiousClients) {
        this.maxSumForDubiousClients = maxSumForDubiousClients;
        Notify("Changed max sum for transaction for untrustworthy clients to "
                + maxSumForDubiousClients);
    }

    public BigDecimal getCreditCommission() {
        return creditCommission;
    }

    public void setCreditCommission(BigDecimal creditCommission) {
        this.creditCommission = creditCommission;
        Notify("Changed credit limit to " + creditLimit, AccountTypes.CREDIT);
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
        Notify("Changed credit limit to " + creditLimit, AccountTypes.CREDIT);
    }

    public BigDecimal getDebitInterest() {
        return debitInterest;
    }

    public void setDebitInterest(BigDecimal debitInterest) {
        this.debitInterest = debitInterest;
        Notify("Changed debit interest to " + debitInterest, AccountTypes.DEBIT);
    }

    public Account addAccount(Client client, AccountTypes accountTypes) throws BanksException {
        return addAccount(client, accountTypes, BigDecimal.ZERO);
    }

    public TimeProvider getTimeProvider() {
        return timeProvider;
    }

    void setTimeProvider(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
        previousAccrualTime = timeProvider.now();
    }

    public Account addAccount(Client client, AccountTypes accountType, BigDecimal balance) throws BanksException {
        client.setBalance(client.getBalance().subtract(balance));

        Account account = resolveAccountCreator(accountType).create(this, client, balance);
        accounts.add(account);
        return account;
    }

    public Account getAccount(UUID accountId) {
        return accounts
                .stream()
                .filter(u -> u.getId().equals(accountId))
                .findFirst()
                .orElse(null);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Account> findAccounts(Client client) {
        return accounts.stream().filter(u -> u.getClient().equals(client)).toList();
    }

    public void deleteAccount(Account account) {
        accounts.remove(account);
    }

    public void deleteAccount(UUID id) {
        deleteAccount(accounts
                .stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null));
    }

    public void replenish(Account account, BigDecimal amount) throws BanksException {
        transactions.add(new Replenishment(account, amount).execute());
    }

    public void transact(Account sender, Account receiver, BigDecimal amount) throws BanksException {
        checkIfDubious(sender, amount);
        transactions.add(new Transaction(sender, receiver, amount).execute());
    }

    public void transact(Account sender, UUID receiverId, BigDecimal amount) throws BanksException {
        Account receiver = accounts
                .stream()
                .filter(u -> u.getId().equals(receiverId))
                .findFirst()
                .orElseThrow(() -> new BanksException(
                        "Account with id " + receiverId + " was not found"));

        transact(sender, receiver, amount);
    }

    public void withdraw(Account account, BigDecimal amount) throws BanksException {
        checkIfDubious(account, amount);
        transactions.add(new Withdrawal(account, amount).execute());
    }

    void accrualOfInterestOrCommission(LocalDateTime currentTime) {
        for (var account : accounts)
            accrueInterestForAccount(account, currentTime);

        previousAccrualTime = currentTime;
    }

    private static AccountCreator resolveAccountCreator(AccountTypes accountType) {
        return switch (accountType) {
            case DEBIT -> new DebitAccountCreator();
            case DEPOSIT -> new DepositAccountCreator();
            case CREDIT -> new CreditAccountCreator();
        };
    }

    private void accrueInterestForAccount(Account account, LocalDateTime currentTime) {
        account.getHistory().add(new BalanceState(account.getBalance(), currentTime));

        List<BalanceState> balanceStates = new ArrayList<>();

        account.getHistory()
                .stream()
                .filter(u -> !previousAccrualTime.isAfter(u.getTime())
                        && !u.getTime().isAfter(currentTime))
                .collect(Collectors.groupingBy(u -> u.getTime().getDayOfMonth()))
                .values()
                .forEach(u -> balanceStates.add(u.get(u.size() - 1)));

        for (int i = 1; i < balanceStates.size(); i++) {
            var days = (int)ChronoUnit.DAYS.between(
                    balanceStates.get(i).getTime(),
                    balanceStates.get(i - 1).getTime());

            account.accrualOfPercents(balanceStates.get(i), days);
        }
    }

    private void checkIfDubious(Account account, BigDecimal amount) throws BanksException {
        if (amount.compareTo(maxSumForDubiousClients) > 0 && account.getClient().checkIfDubious())
            throw new BanksException("Client is untrustworthy");
    }

    private void Notify(String notificationMessage) {
        accounts.forEach(u -> u.NotifyClient(notificationMessage));
    }

    private void Notify(String notificationMessage, AccountTypes accountType) {
        accounts.stream()
                .filter(u -> u.isInstanceOf(accountType))
                .forEach(u -> u.NotifyClient(notificationMessage));
    }
}
