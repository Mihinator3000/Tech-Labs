package main.banks.entities.accounts.creators;

import main.banks.entities.accounts.Account;
import main.banks.entities.accounts.DebitAccount;
import main.banks.entities.banks.Bank;
import main.banks.entities.clients.Client;
import main.banks.tools.BanksException;

import java.math.BigDecimal;

public record DebitAccountCreator() implements AccountCreator {

    public Account create(Bank bank, Client client, BigDecimal balance) throws BanksException {
        return new DebitAccount(bank, client, balance);
    }
}
