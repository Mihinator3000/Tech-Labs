package main.banks.entities.clients.passport;

import java.util.Objects;
import java.util.regex.Pattern;
import main.banks.tools.BanksException;

public class PassportInfo {

    private final int batch;
    private final int code;

    public PassportInfo(String passportInfo) throws BanksException {
        var matcher = Pattern.compile("^(\\d{4})(\\d{6})$")
                .matcher(passportInfo.replace(" ", ""));

        if (!matcher.find())
            throw new BanksException("Incorrect passport information");


        batch = Integer.parseInt(matcher.group(1));
        code = Integer.parseInt(matcher.group(2));
    }

    public int getBatch() {
        return batch;
    }

    public int getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PassportInfo that)) return false;
        return batch == that.batch && code == that.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(batch, code);
    }

    @Override
    public String toString() {
        return String.format("%04d %06d", batch, code);
    }
}
