package timelogger.exceptions;

public class NotExpectedTimeOrder extends RuntimeException {

    public NotExpectedTimeOrder() {
    }
    
    public NotExpectedTimeOrder(String msg) {
        super(msg);
    }
}
