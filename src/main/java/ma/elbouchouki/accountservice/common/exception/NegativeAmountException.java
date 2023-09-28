package ma.elbouchouki.accountservice.common.exception;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException(String e) {
        super(e);
    }
}
