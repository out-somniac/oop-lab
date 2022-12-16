package simulation.exceptions;

public class InvalidConfiguration extends Exception {
    public InvalidConfiguration(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidConfiguration(String message) {
        super(message);
    }
}