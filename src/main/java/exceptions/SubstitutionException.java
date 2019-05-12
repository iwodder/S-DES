package exceptions;

import static exceptions.ExceptionMessages.substitutionExceptionDefault;

public class SubstitutionException extends Throwable {
    private String message;

    public SubstitutionException() {
        this.message = substitutionExceptionDefault;
    }

    public SubstitutionException(String message) {
        this.message = message;
    }
}
