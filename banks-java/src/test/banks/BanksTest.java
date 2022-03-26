package test.banks;

import main.banks.entities.accounts.Account;
import main.banks.entities.banks.Bank;
import main.banks.entities.banks.BankBuilder;
import main.banks.entities.banks.MainBank;
import main.banks.entities.clients.BankClient;
import main.banks.entities.clients.Client;
import main.banks.entities.clients.passport.PassportInfo;
import main.banks.enums.AccountTypes;
import main.banks.tools.BanksException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class BanksTest {

    private MainBank mainBank;

    private final BigDecimal maxSumForDubiousClients = BigDecimal.valueOf(1000);
    private final BigDecimal creditCommission = BigDecimal.valueOf(5);
    private final BigDecimal creditLimit = BigDecimal.valueOf(-500);
    private final BigDecimal debitInterest = BigDecimal.valueOf(0.5);

    private final int dayShift = 15;
    
    @BeforeEach
    void setUp() {
        mainBank = new MainBank();
        mainBank.registerBank(new BankBuilder()
                .setMaxSumForDubiousClients(maxSumForDubiousClients)
                .setCreditCommission(creditCommission)
                .setCreditLimit(creditLimit)
                .setDebitInterest(debitInterest)
                .build());
    }

    @Test
    void transactionBetweenAccountsMoneyTransacted() throws BanksException {
        Bank bank = mainBank.getBank(0);

        Client client = new BankClient("Mikhail", "Koshkin");
        client.setBalance(1000);
        mainBank.addClient(client);

        final var openingBalance1 = BigDecimal.valueOf(30);
        final var openingBalance2 = BigDecimal.valueOf(40);
        final var transactionAmount = BigDecimal.valueOf(15.5);

        Account account1 = bank.addAccount(client, AccountTypes.CREDIT, openingBalance1);
        Account account2 = bank.addAccount(client, AccountTypes.DEBIT, openingBalance2);

        bank.transact(account1, account2, transactionAmount);

        assertEquals(account1.getBalance(), openingBalance1.subtract(transactionAmount));

        assertEquals(account2.getBalance(), openingBalance2.add(transactionAmount));
    }

    @Test
    void transactionCancelledMoneyReturned() throws BanksException {
        Bank bank = mainBank.getBank(0);

        Client client = new BankClient("Some", "Dude");
        client.setBalance(100);
        mainBank.addClient(client);

        final var openingBalance1 = BigDecimal.valueOf(30);
        final var openingBalance2 = BigDecimal.valueOf(50);
        final var transactionAmount = BigDecimal.valueOf(15.5);

        Account account1 = bank.addAccount(client, AccountTypes.CREDIT, openingBalance1);
        Account account2 = bank.addAccount(client, AccountTypes.DEBIT, openingBalance2);

        bank.transact(account1, account2, transactionAmount);
        bank.getTransactions().get(0).cancel();

        assertTrue(account1.getBalance().compareTo(openingBalance1) == 0);
        assertTrue(account2.getBalance().compareTo(openingBalance2) == 0);
    }

    @Test
    void withdrawalReplenishmentMoneyChanged() throws BanksException {
        Bank bank = mainBank.getBank(0);

        Client client = new BankClient("Mikhail", "Koshkin");
        client.setBalance(1000);
        mainBank.addClient(client);

        final var openingBalance = BigDecimal.valueOf(10);
        final var replenishmentAmount = BigDecimal.valueOf(30);
        final var withdrawalAmount = BigDecimal.valueOf(40);

        Account account = bank.addAccount(client, AccountTypes.DEBIT, openingBalance);

        bank.replenish(account, replenishmentAmount);
        bank.withdraw(account, withdrawalAmount);

        assertEquals(account.getBalance(), openingBalance.add(replenishmentAmount).subtract(withdrawalAmount));
    }

    @Test
    void creditAccountCommissionWithNegativeBalance() throws BanksException {
        Bank bank = mainBank.getBank(0);

        Client client = new BankClient("Mikhail", "Koshkin");
        client.setBalance(1000);
        mainBank.addClient(client);

        final var openingBalance = BigDecimal.valueOf(10);
        final var withdrawalAmount = BigDecimal.valueOf(50);

        Account account = bank.addAccount(client, AccountTypes.CREDIT, openingBalance);

        bank.withdraw(account, withdrawalAmount);

        mainBank.getTimeProvider().addTime(Period.ofDays(dayShift));

        mainBank.accruePercentage();

        assertEquals(account.getBalance(),
                openingBalance
                        .subtract(withdrawalAmount)
                        .subtract(creditCommission.multiply(
                                BigDecimal.valueOf(dayShift))));
    }

    @Test
    public void debitAccountInterestAccrual() throws BanksException {
        Bank bank = mainBank.getBank(0);

        Client client = new BankClient("Mikhail", "Koshkin");
        client.setBalance(1000);
        mainBank.addClient(client);

        final var openingBalance = BigDecimal.valueOf(10);

        Account account = bank.addAccount(client, AccountTypes.DEBIT, openingBalance);

        mainBank.getTimeProvider().addTime(Period.ofDays(dayShift));

        mainBank.accruePercentage();

        assertEquals(account.getBalance(),
                openingBalance.add(openingBalance
                        .multiply(debitInterest)
                        .multiply(BigDecimal.valueOf(dayShift / 365.0))));
    }

    @Test
    void depositAccountInterestAccrual() throws BanksException {
        Bank bank = mainBank.getBank(0);

        Client client = new BankClient("Mikhail", "Koshkin");
        client.setBalance(1000);
        mainBank.addClient(client);

        final var openingBalance = BigDecimal.valueOf(10);
        final var depositInterest = BigDecimal.valueOf(3);

        Account account = bank.addAccount(client, AccountTypes.DEPOSIT, openingBalance);

        mainBank.getTimeProvider().addTime(Period.ofDays(dayShift));

        mainBank.accruePercentage();

        assertTrue(0 == account.getBalance().compareTo(
                openingBalance.add(openingBalance
                        .multiply(depositInterest)
                        .multiply(BigDecimal.valueOf(dayShift / 365.0)))));
    }

    @Test
    void creditLimitChangedClientNotified() throws BanksException {
        Bank bank = mainBank.getBank(0);

        Client client = new BankClient("Mikhail", "Koshkin");
        client.setBalance(1000);
        mainBank.addClient(client);

        Account account = bank.addAccount(client, AccountTypes.CREDIT);
        account.setNotifyClient(true);

        bank.setCreditLimit(BigDecimal.valueOf(-200));

        assertEquals(client.getNotifications().size(), 1);
    }

    @Test
    public void ClientIsUntrustworthyCannotDoTheWithdrawal_ThrowException() throws BanksException {
        Bank bank = mainBank.getBank(0);

        Client client = new BankClient("Mikhail", "Koshkin");
        client.setBalance(2000);
        mainBank.addClient(client);

        final var openingBalance = BigDecimal.valueOf(1500);
        final var withdrawalAmount = BigDecimal.valueOf(1200);

        Account account = bank.addAccount(client, AccountTypes.DEBIT, openingBalance);

        assertThrows(BanksException.class, () ->
            bank.withdraw(account, withdrawalAmount));

        client.setAddress("Some address");
        client.setPassport(new PassportInfo("4444 555555"));

        bank.withdraw(account, withdrawalAmount);
    }

    @Test
    void cannotWithdrawFromDepositAccount_ThrowException() throws BanksException {
        Bank bank = mainBank.getBank(0);

        Client client = new BankClient("Mikhail", "Koshkin");
        client.setBalance(2000);
        mainBank.addClient(client);

        Account account = bank.addAccount(client, AccountTypes.DEPOSIT, BigDecimal.valueOf(20));

        assertThrows(BanksException.class, () ->
            bank.withdraw(account, BigDecimal.valueOf(10)));
    }

    @Test
    void CannotHaveNegativeBalanceOnDebit_ThrowException() throws BanksException {
        Bank bank = mainBank.getBank(0);

        Client client = new BankClient("Mikhail", "Koshkin");
        client.setBalance(2000);
        mainBank.addClient(client);

        final var openingBalance =  BigDecimal.valueOf(200);
        final var withdrawalAmount =  BigDecimal.valueOf(300);

        Account account = bank.addAccount(client, AccountTypes.DEBIT, openingBalance);

        assertThrows(BanksException.class, () ->
            bank.withdraw(account, withdrawalAmount));
    }
}