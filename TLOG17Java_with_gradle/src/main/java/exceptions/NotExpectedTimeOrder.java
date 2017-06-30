package timelogger.exceptions;

public class NotExpectedTimeOrder extends Exception {

    public NotExpectedTimeOrder() {
    }
    
    public NotExpectedTimeOrder(String msg) {
        super(msg);
    }
}
