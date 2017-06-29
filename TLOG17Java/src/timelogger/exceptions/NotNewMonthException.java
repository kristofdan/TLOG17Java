package timelogger.exceptions;

public class NotNewMonthException extends RuntimeException {

    public NotNewMonthException() {
    }

    public NotNewMonthException(String msg) {
        super(msg);
    }
}
