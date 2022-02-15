package main.banks.providers;

import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;

public class TimeProvider {

    private Period timeShift = Period.ZERO;

    public OffsetDateTime now() {
        return OffsetDateTime.now(ZoneOffset.UTC).plus(timeShift);
    }

    public void addTime(Period timeShift) {
        this.timeShift = this.timeShift.plus(timeShift);
    }

    public Period getTimeShift() {
        return timeShift;
    }
}
