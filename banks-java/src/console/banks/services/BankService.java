package console.banks.services;

import console.banks.providers.IOProvider;
import console.banks.tools.InputException;
import main.banks.entities.banks.Bank;

import java.math.BigDecimal;

public record BankService(Bank bank, IOProvider ioProvider) implements AbstractService {

    public void handleInput(String[] input) throws InputException {

        BigDecimal outputNumber = resolveGettersInput(input);
        if (outputNumber != null) {
            ioProvider.printLine(outputNumber);
            return;
        }

        handleSetters(input);
    }

    public void printAvailable() {
        ioProvider.printLines(
                "/get_dubious_clients_limit, /set_dubious_clients_limit <amount>",
                "/get_credit_commission, /set_credit_commission <amount>",
                "/get_credit_limit, /set_credit_limit <amount>",
                "/get_debit_interest, /set_debit_interest <amount>");
    }

    private BigDecimal resolveGettersInput(String[] input) {
        return switch(input[0]) {
            case "/get_dubious_clients_limit" -> bank.getMaxSumForDubiousClients();
            case "/get_credit_commission" -> bank.getCreditCommission();
            case "/get_credit_limit" -> bank.getCreditLimit();
            case "/get_debit_interest" -> bank.getDebitInterest();
            default -> null;
        };
    }

    private void handleSetters(String[] input) throws InputException{
        BigDecimal inputNumber = new BigDecimal(input[1]);

        switch (input[0]) {
            case "/set_dubious_clients_limit" -> bank.setMaxSumForDubiousClients(inputNumber);
            case "/set_credit_commission" -> bank.setCreditCommission(inputNumber);
            case "/set_credit_limit" -> bank.setCreditLimit(inputNumber);
            case "/set_debit_interest" -> bank.setDebitInterest(inputNumber);
            default -> throw new InputException();
        }
    }
}
