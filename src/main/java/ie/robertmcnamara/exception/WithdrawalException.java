package ie.robertmcnamara.exception;

/**
 * Exception used when a transaction (withdrawal) fails
 * Typically due to insufficent funds in ATM or customer account
 */
public class WithdrawalException extends RuntimeException {
    
    private final int errorCode;
    private final int accountNumber;
    
    public WithdrawalException(int errorCode, int accountNumber, String message) {
        super(message);
        this.errorCode = errorCode;
        this.accountNumber = accountNumber;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    
    public int getAccountNumber() {
        return accountNumber;
    }
}
