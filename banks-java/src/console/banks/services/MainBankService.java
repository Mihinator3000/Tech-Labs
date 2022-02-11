package console.banks.services;

import console.banks.providers.IOProvider;
import console.banks.tools.InputException;
import main.banks.entities.banks.Bank;
import main.banks.entities.banks.BankBuilder;
import main.banks.entities.banks.MainBank;
import main.banks.entities.clients.BankClient;
import main.banks.entities.clients.Client;
import main.banks.entities.clients.passport.PassportInfo;
import main.banks.tools.BanksException;

public record MainBankService(MainBank mainBank, IOProvider ioProvider) implements AbstractService {

    public void handleInput(String[] input) throws BanksException, InputException {
        switch (input[0]) {
            case "/add_bank" -> mainBank.registerBank(createBank());
            case "/add_client" -> mainBank.addClient(createClient());
            case "/delete_bank" -> mainBank.deleteBank(mainBank
                    .getBank(Integer.parseInt(input[1])));
            case "/delete_client" -> mainBank.deleteClient(mainBank
                    .getClient(new PassportInfo(input[1])));
            case "/accrue" -> mainBank.accruePercentage();
            default -> throw new InputException();
        }
    }

    public void printAvailable() {
        ioProvider.printLines(
                "/add_bank - create bank and add it to system",
                "/add_client - create client and add them to system",
                "/delete_bank <number> - delete bank",
                "/delete_client <passport> - delete client",
                "/accrue - accrue percents or commission");
    }

    private Bank createBank() {
        return new BankBuilder()
                .setMaxSumForDubiousClients(ioProvider.nextDecimal(
                        "max sum for dubious clients"))
                .setCreditCommission(ioProvider.nextDecimal(
                        "credit commission"))
                .setCreditLimit(ioProvider.nextDecimal(
                        "credit limit"))
                .setDebitInterest(ioProvider.nextDecimal(
                        "debit interest"))
                .build();
    }

    private Client createClient() throws BanksException {
        var client = new BankClient(
                ioProvider.notBlankInput("name"),
                ioProvider.notBlankInput("surname"));

        client.setBalance(ioProvider.nextDecimal("balance"));
        client.setAddress(ioProvider.nextString("address"));
        client.setPassport(ioProvider.nextPassport("passport info"));
        return client;
    }
}
