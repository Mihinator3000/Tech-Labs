package main.banks.entities.accounts.balance;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BalanceState(BigDecimal balance, LocalDateTime time) {

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
