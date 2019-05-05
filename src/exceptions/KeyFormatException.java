package exceptions;

public class KeyFormatException extends Throwable {
    private static String message;

    public KeyFormatException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
