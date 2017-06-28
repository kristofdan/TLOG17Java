package timelogger.exceptions;

public class EmptyTimeFieldException extends RuntimeException {

    public EmptyTimeFieldException() {
    }

    public EmptyTimeFieldException(String msg) {
        super(msg);
    }
}
