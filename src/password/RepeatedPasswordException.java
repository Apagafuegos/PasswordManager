package password;

public class RepeatedPasswordException extends Exception {
    public RepeatedPasswordException() {
    }

    public RepeatedPasswordException(String message) {
        super(message);
    }

    public RepeatedPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatedPasswordException(Throwable cause) {
        super(cause);
    }

    protected RepeatedPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
