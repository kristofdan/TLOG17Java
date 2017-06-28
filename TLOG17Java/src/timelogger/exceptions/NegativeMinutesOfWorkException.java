package timelogger.exceptions;

public class NegativeMinutesOfWorkException extends RuntimeException {

    public NegativeMinutesOfWorkException() {
    }

    public NegativeMinutesOfWorkException(String msg) {
        super(msg);
    }
}
