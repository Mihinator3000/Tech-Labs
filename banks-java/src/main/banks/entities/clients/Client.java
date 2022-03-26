package main.banks.entities.clients;

import main.banks.entities.clients.passport.PassportInfo;
import main.banks.tools.BanksException;

import java.math.BigDecimal;
import java.util.List;

public interface Client {

    String getName();

    String getSurname();

    String getAddress();
    void setAddress(String address);

    PassportInfo getPassport();
    void setPassport(PassportInfo passport);

    BigDecimal getBalance();
    void setBalance(BigDecimal balance) throws BanksException;
    void setBalance(int balance) throws BanksException;

    List<String> getNotifications();
    void addNotification(String notification);
    void clearNotifications();

    boolean checkIfDubious();
}
