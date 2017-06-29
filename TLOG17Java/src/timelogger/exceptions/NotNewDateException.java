package timelogger.exceptions;

public class NotNewDateException extends RuntimeException {

    public NotNewDateException() {
    }

    public NotNewDateException(String msg) {
        super(msg);
    }
}
