package ma.elbouchouki.accountservice.common.exception;

public class NegativeStartingBalanceException extends RuntimeException {
    public NegativeStartingBalanceException(String e) {
        super(e);
    }
}
