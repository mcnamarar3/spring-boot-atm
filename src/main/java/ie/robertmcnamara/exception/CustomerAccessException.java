package ie.robertmcnamara.exception;

/**
 * Exception used when the customer cannot access their account.
 * Typically due to invalid account number or pin
 */
public class CustomerAccessException extends RuntimeException {
    
    private final int accountNumber;
    private final int errorCode;
    
    public CustomerAccessException(int errorCode, int accountNumber, String message) {
        super(message);
        this.errorCode = errorCode;
        this.accountNumber = accountNumber;
    }
    
    public CustomerAccessException(int errorCode, int accountNumber, String message, Throwable cause) {
        super(message, cause);
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
