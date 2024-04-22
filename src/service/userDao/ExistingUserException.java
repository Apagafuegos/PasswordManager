package service.userDao;

public class ExistingUserException extends Exception {
    /**
     * Exception used when it already exists a user with the introduced username (used when creating a new account).
     */
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
