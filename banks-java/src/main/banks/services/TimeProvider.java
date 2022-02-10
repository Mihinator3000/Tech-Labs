package main.banks.services;

import java.time.LocalDateTime;
import java.time.Period;

public class TimeProvider {

    private Period timeShift = Period.ZERO;

    public LocalDateTime now() {
        return LocalDateTime.now().plus(timeShift);
    }

    public void addTime(Period timeShift) {
        this.timeShift = this.timeShift.plus(timeShift);
    }

    public Period getTimeShift() {
        return timeShift;
    }
}
