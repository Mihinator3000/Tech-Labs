package main.banks.entities.transaction;

import main.banks.tools.BanksException;

public class Cancellable {

    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() throws BanksException {
        if (this.cancelled)
            throw new BanksException("Transaction is already cancelled");

        this.cancelled = true;
    }
}
