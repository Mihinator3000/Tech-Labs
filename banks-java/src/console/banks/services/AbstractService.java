package console.banks.services;

import console.banks.tools.InputException;
import main.banks.tools.BanksException;

public interface AbstractService {

    void handleInput(String[] input) throws BanksException, InputException;

    void printAvailable();
}

