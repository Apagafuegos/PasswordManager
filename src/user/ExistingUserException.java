package user;

public class ExistingUserException extends Exception {
    public ExistingUserException() {
    }

    public ExistingUserException(String message) {
        super(message);
    }

    public ExistingUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingUserException(Throwable cause) {
        super(cause);
    }

    protected ExistingUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
