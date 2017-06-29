package timelogger.exceptions;

public class NotTheSameMonthException extends RuntimeException {

    public NotTheSameMonthException() {
    }

    public NotTheSameMonthException(String msg) {
        super(msg);
    }
}
