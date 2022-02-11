package console.banks;

import console.banks.services.AbstractService;
import console.banks.services.BankService;
import console.banks.services.ClientService;
import console.banks.services.MainBankService;
import console.banks.providers.IOProvider;
import console.banks.tools.InputException;
import main.banks.entities.banks.Bank;
import main.banks.entities.banks.BankBuilder;
import main.banks.entities.banks.MainBank;
import main.banks.entities.clients.BankClient;
import main.banks.entities.clients.Client;
import main.banks.entities.clients.passport.PassportInfo;
import main.banks.enums.AccountTypes;
import main.banks.tools.BanksException;

import java.math.BigDecimal;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class ConsoleInterface {

    private final IOProvider ioProvider;

    private MainBank mainBank;

    private AbstractService currentService;

    public ConsoleInterface() {
        mainBank = new MainBank();
        ioProvider = new IOProvider();

        currentService = new MainBankService(mainBank, ioProvider);
    }

    public void run() {
        while (true) {

            var input = ioProvider
                    .notBlankInput()
                    .split(" ");

            if (input[0].charAt(0) != '/') {
                ioProvider.printError("Input is not a command");
            }

            if (input[0].equals("/e") || input[0].equals("/exit"))
                return;

            try {
                switch (input[0]) {
                    case "/h", "/help" -> printGuide();
                    case "/a", "/available" -> printAvailable();
                    case "/r", "/reset" -> mainBank = new MainBank();
                    case "/d", "/set_default" -> setDefaultState();
                    case "/add", "/add_days" -> mainBank.getTimeProvider().addTime(
                            Period.ofDays(Integer.parseInt(input[1])));
                    case "/time" -> ioProvider.printLine(
                            DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
                                    .format(mainBank.getTimeProvider().now()));
                    default -> handleServiceInput(input);
                }
            } catch (BanksException e) {
                ioProvider.printError(e.getMessage());
            } catch (InputException e) {
                ioProvider.printError(e.getMessage() == null
                        ? "Command does not exist"
                        : e.getMessage());
            } catch (NumberFormatException e) {
                ioProvider.printError("Incorrect number input");
            } catch (IllegalArgumentException e) {
                ioProvider.printError("Incorrect id input");
            } catch (ArrayIndexOutOfBoundsException e) {
                ioProvider.printError("Incorrect command arguments");
            }

            ioProvider.printEmptyLine();
        }
    }

    private void handleServiceInput(String[] input) throws BanksException, InputException {
        switch (input[0]) {
            case "/client" ->
                currentService = new ClientService(
                        mainBank,
                        (input.length == 2)
                                ? mainBank.getClient(new PassportInfo(input[1]))
                                : mainBank.getClients()
                                    .stream()
                                    .filter(u -> u.getName().equals(input[1])
                                            && u.getSurname().equals(input[2]))
                                    .findFirst()
                                    .orElseThrow(() -> new InputException("Client was not found")),
                        ioProvider);
            case "/bank" -> currentService = new BankService(
                    mainBank.getBank(Integer.parseInt(input[1])),
                    ioProvider);
            case "/main", "/main_bank" -> currentService = new MainBankService(
                    mainBank,
                    ioProvider);
            default -> currentService.handleInput(input);
        }
    }

    private void printGuide() {
        ioProvider.printLines(
                "Call /a or /available to show available commands,",
                "required parameters will be shown in angle brackets and optional in curly.\n",
                "When creating an entity leave input empty for default.");
    }

    private void printAvailable() {
        ioProvider.printLines(
                "/e, /exit - exit the application",
                "/h, /help - show help guide",
                "/a, /available - show available commands",
                "/r, /reset - reset the system",
                "/d, /set_default - set default state",
                "/add, /add_days <count> - add days to current date",
                "/time - show current application time",
                "/client <passport> || <name> <surname> - set input for client",
                "/bank <number> - set input for bank",
                "/main, /main_bank - set input for main bank");

        ioProvider.printEmptyLine();
        currentService.printAvailable();
    }

    private void setDefaultState() throws BanksException {
        mainBank = new MainBank();

        Bank bank = new BankBuilder()
                .setMaxSumForDubiousClients(BigDecimal.valueOf(1000))
                .setCreditCommission(BigDecimal.valueOf(5))
                .setCreditLimit(BigDecimal.valueOf(-500))
                .setDebitInterest(BigDecimal.valueOf(0.5))
                .build();

        mainBank.registerBank(bank);

        Client client = new BankClient("Mikhail", "Koshkin");
        client.setBalance(1000);
        client.setPassport(new PassportInfo("0123 456789"));
        mainBank.addClient(client);

        bank.addAccount(client, AccountTypes.DEPOSIT, BigDecimal.valueOf(10));
        bank.addAccount(client, AccountTypes.DEBIT, BigDecimal.valueOf(15.5));
        bank.addAccount(client, AccountTypes.CREDIT).setNotifyClient(true);

        currentService = new ClientService(mainBank, client, ioProvider);
    }
}
