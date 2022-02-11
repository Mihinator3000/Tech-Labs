package console.banks.providers;

import main.banks.entities.clients.passport.PassportInfo;
import main.banks.tools.BanksException;

import java.math.BigDecimal;
import java.util.Scanner;

public class IOProvider {

    private final Scanner scanner;

    private static final String RESET = "\033[0m";
    private static final String RED_BOLD = "\033[1;31m";

    public IOProvider() {
        scanner = new Scanner(System.in);
    }

    public String nextString() {
        return scanner.nextLine();
    }

    public String nextString(String fieldName) {
        printInputRequirement(fieldName);
        return nextString();
    }

    public String notBlankInput() {
        while (true) {
            String input = nextString();
            if (!input.isBlank())
                return input;
        }
    }

    public String notBlankInput(String fieldName) {
        printInputRequirement(fieldName);
        return notBlankInput();
    }

    public BigDecimal nextDecimal() {
        while (true) {
            String input = nextString();
            if (input.isBlank())
                return BigDecimal.ZERO;

            try {
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                printError("Input is not a decimal");
            }
        }
    }

    public BigDecimal nextDecimal(String fieldName) {
        printInputRequirement(fieldName);
        return nextDecimal();
    }

    public PassportInfo nextPassport() {
        while (true) {

            String input = nextString();
            if (input.isBlank())
                return null;

            try {
                return new PassportInfo(input);
            } catch (BanksException e) {
                printError("Input is not a passport");
            }
        }
    }

    public PassportInfo nextPassport(String fieldName) {
        printInputRequirement(fieldName);
        return nextPassport();
    }

    public void printEmptyLine() {
        System.out.println();
    }

    public void printLine(String message) {
        System.out.println(message);
    }

    public void printLine(BigDecimal decimal) {
        printLine(decimal.toString());
    }

    public void printLines(String... messages) {
        for (var message : messages)
            printLine(message);
    }

    public void printError(String message) {
       printLine(RED_BOLD + message + RESET);
    }

    private void printInputRequirement(String fieldName) {
        printLine("Input " + fieldName + ":");
    }
}
