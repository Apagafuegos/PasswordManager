package service.passwordDao;

public class NoSuchServiceException extends Exception {
    /**
     * Exception used when it does not exist a password for the introduced service.
     */
    public NoSuchServiceException() {
    }

    public NoSuchServiceException(String message) {
        super(message);
    }

    public NoSuchServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchServiceException(Throwable cause) {
        super(cause);
    }

    protected NoSuchServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
