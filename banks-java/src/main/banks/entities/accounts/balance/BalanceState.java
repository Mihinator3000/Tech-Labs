package main.banks.entities.accounts.balance;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record BalanceState(BigDecimal balance, OffsetDateTime time) {

    public BigDecimal getBalance() {
        return balance;
    }

    public OffsetDateTime getTime() {
        return time;
    }
}
