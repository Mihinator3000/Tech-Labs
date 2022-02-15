package console.banks.services;

import console.banks.providers.IOProvider;
import console.banks.tools.InputException;
import main.banks.entities.accounts.Account;
import main.banks.entities.banks.Bank;
import main.banks.entities.banks.MainBank;
import main.banks.entities.clients.Client;
import main.banks.entities.clients.passport.PassportInfo;
import main.banks.enums.AccountTypes;
import main.banks.tools.BanksException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public record ClientService(MainBank mainBank, Client client, IOProvider ioProvider) implements AbstractService {

    public void handleInput(String[] input) throws BanksException, InputException {
        switch (input[0]) {
            case "/add_account" -> addAccount(input);
            case "/delete_account" -> deleteAccount(UUID.fromString(input[1]));
            case "/replenish" -> replenish(
                    UUID.fromString(input[1]),
                    new BigDecimal(input[2]));
            case "/transact" -> transact(
                    UUID.fromString(input[1]),
                    UUID.fromString(input[2]),
                    new BigDecimal(input[3]));
            case "/withdraw" -> withdraw(
                    UUID.fromString(input[1]),
                    new BigDecimal(input[2]));
            case "/print_info" -> printInfo();
            case "/set_address" -> client.setAddress(input[1]);
            case "/set_passport" -> client.setPassport(new PassportInfo(input[1]));
            case "/set_balance" -> client.setBalance(new BigDecimal(input[1]));
            case "/not", "/print_notifications" -> client
                    .getNotifications().forEach(ioProvider::printLine);
            case "/clear", "/clear_notifications" -> client.clearNotifications();
            case "/notify" -> setNotifyAccount(input, true);
            case "/do_not_notify" -> setNotifyAccount(input, false);
            default -> throw new InputException();
        }
    }

    public void printAvailable() {
        ioProvider.printLines(
                "/add_account <account_type> <bank_id> {amount}",
                "   - create account of account_type in bank with bank_id with {start amount}",
                "/delete_account <id> - delete account with that id",
                "/replenish <id> <amount> - replenish account for stated amount",
                "/transact <id> <receiver_id> <amount> - transaction to account with receiver_id",
                "/withdraw <id> <amount> - withdraw from account",
                "/print_info - show all info about client",
                "/set_address <address>",
                "/set_passport <passport>",
                "/set_balance <amount>",
                "/not, /print_notifications - show all notifications",
                "/clear, /clear_notifications - clear all notifications",
                "/notify <id> - set notifications for account with stated id",
                "/do_not_notify <id> - set no notifications for account");
    }

    private void addAccount(String[] input) throws BanksException, InputException {
        Bank bank = mainBank.getBank(Integer.parseInt(input[1]));

        AccountTypes accountTypes = switch (input[2].toLowerCase()) {
            case "debit" -> AccountTypes.DEBIT;
            case "deposit" -> AccountTypes.DEPOSIT;
            case "credit" -> AccountTypes.CREDIT;
            default -> throw new InputException("No such account type exists");
        };

        var startAmount = input.length == 3
                ? BigDecimal.ZERO
                : new BigDecimal(input[3]);

        bank.addAccount(client, accountTypes, startAmount);
    }

    private void deleteAccount(UUID accountId) throws InputException {
        getBank(accountId).deleteAccount(accountId);
    }

    private void replenish(UUID accountId, BigDecimal amount) throws BanksException, InputException {
        Bank bank = getBank(accountId);
        bank.replenish(bank.getAccount(accountId), amount);
    }

    private void transact(UUID senderId, UUID receiverId, BigDecimal amount) throws BanksException, InputException {
        Bank bank = getBank(senderId);
        Account senderAccount = bank.getAccount(senderId);
        checkOwner(senderAccount);
        bank.transact(senderAccount, receiverId, amount);
    }

    private void withdraw(UUID accountId, BigDecimal amount) throws BanksException, InputException {
        Bank bank = getBank(accountId);
        Account account = bank.getAccount(accountId);
        checkOwner(account);
        bank.withdraw(account, amount);
    }

    private void printInfo() {
        ioProvider.printLines(
                "Name: " + client().getName(),
                "Surname: " + client().getSurname());

        String address = client.getAddress();
        ioProvider.printLine(
                "Address" +
                (address == null || address.isBlank()
                        ? " not set"
                        : ": " + address));

        PassportInfo passport = client.getPassport();
        ioProvider.printLine(
                "Passport" +
                (passport == null
                        ? " not set"
                        : ": " + passport));

        ioProvider.printLine("Balance: " + client.getBalance().setScale(2, RoundingMode.CEILING));

        mainBank.getBanks().forEach(b -> b
                .getAccounts()
                .stream()
                .filter(a -> a.getClient().equals(client))
                .forEach(a -> ioProvider.printLines(
                        "\n" + a.getType(),
                        "Id: " + a.getId(),
                        "Balance: " + a.getBalance().setScale(2, RoundingMode.CEILING),
                        "Notifications " + (a.getNotifyClient() ? "on" : "off"))));
    }

    private void setNotifyAccount(String[] input, boolean notify) throws InputException {
        var accountId = UUID.fromString(input[1]);
        getBank(accountId).getAccount(accountId).setNotifyClient(notify);
    }

    private void checkOwner(Account account) throws InputException {
        if (!(account.getClient().getPassport() != null && account.getClient().equals(client)))
            throw new InputException("Client is not owner of the current account");
    }

    private Bank getBank(UUID accountId) throws InputException {
        for (var bank : mainBank.getBanks())
            if (bank.getAccount(accountId) != null)
                return bank;

        throw new InputException("Incorrect account_id");
    }
}
