package service.passwordDao;

public class NoPasswordsException extends Exception {
    public NoPasswordsException() {
    }

    public NoPasswordsException(String message) {
        super(message);
    }

    public NoPasswordsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPasswordsException(Throwable cause) {
        super(cause);
    }

    protected NoPasswordsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
