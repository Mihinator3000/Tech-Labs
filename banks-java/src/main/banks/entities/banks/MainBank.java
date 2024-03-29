package main.banks.entities.banks;

import main.banks.entities.clients.Client;
import main.banks.entities.clients.passport.PassportInfo;
import main.banks.providers.TimeProvider;
import main.banks.tools.BanksException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainBank {

    private final TimeProvider timeProvider;

    private final List<Bank> banks;
    private final List<Client> clients;

    public MainBank() {
        timeProvider = new TimeProvider();
        banks = new ArrayList<>();
        clients = new ArrayList<>();
    }

    public Bank registerBank(Bank bank) {
        bank.setTimeProvider(timeProvider);
        banks.add(bank);
        return bank;
    }

    public Bank registerBank(BankBuilder bankBuilder) {
        return registerBank(bankBuilder.build());
    }

    public Bank getBank(int id) throws BanksException {
        if (id < 0 || id >= banks.size())
            throw new BanksException("Incorrect bank id");

        return banks.get(id);
    }

    public void deleteBank(Bank bank) {
        banks.remove(bank);
    }


    public List<Bank> getBanks() {
        return banks;
    }

    public List<Client> getClients() {
        return clients;
    }

    public Client addClient(Client client) {
        clients.add(client);
        return client;
    }

    public List<Client> findClients(String name, String surname) {
        return clients
                .stream()
                .filter(c -> c.getName().equals(name)
                        && c.getSurname().equals(surname))
                .toList();
    }

    public Client getClient(PassportInfo passportInfo) throws BanksException {
        if (passportInfo == null)
            throw new BanksException("Passport info is null");

        return clients
                .stream()
                .filter(c -> passportInfo.equals(c.getPassport()))
                .findFirst()
                .orElseThrow(() -> new BanksException("No client found"));
    }

    public void deleteClient(Client client) {
        clients.remove(client);
        banks.forEach(b -> b.findAccounts(client)
                .forEach(a -> b.getAccounts().remove(a)));
    }

    public void accruePercentage() {
        OffsetDateTime time = timeProvider.now();

        for (var bank : banks)
            bank.accrualOfInterestOrCommission(time);
    }

    public TimeProvider getTimeProvider() {
        return timeProvider;
    }
}
