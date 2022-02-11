package main.banks.entities.clients;

import main.banks.entities.clients.passport.PassportInfo;
import main.banks.tools.BanksException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BankClient implements Client {

    private final String name;
    private final String surname;

    private String address;
    private PassportInfo passport;
    private BigDecimal balance;

    private List<String> notifications;

    public BankClient(String name, String surname) {
        this.name = name;
        this.surname = surname;

        notifications = new ArrayList<>();
    }

    public BankClient(String name, String surname, String address, PassportInfo passport) {
        this(name, surname);
        this.address = address;
        this.passport = passport;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PassportInfo getPassport() {
        return passport;
    }

    public void setPassport(PassportInfo passport) {
        this.passport = passport;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) throws BanksException {
        if (balance.compareTo(BigDecimal.ZERO) < 0)
            throw new BanksException("User's balance can not be less than 0");

        this.balance = balance;
    }

    public void setBalance(int balance) throws BanksException {
        setBalance(BigDecimal.valueOf(balance));
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void addNotification(String notification) {

        notifications.remove(notification);

        notifications.add(notification);
    }

    public void clearNotifications() {
        notifications.clear();
    }

    public boolean checkIfDubious() {
        return address == null || address.isBlank() || passport == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankClient that)) return false;
        return passport.equals(that.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passport);
    }
}
