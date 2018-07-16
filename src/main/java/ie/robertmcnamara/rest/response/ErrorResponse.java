package ie.robertmcnamara.rest.response;

/**
 * This class models a error response for a customer request with cannot be handled.
 */
public class ErrorResponse {
    private int code;
    private String message;
    private int accountNumber;
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public int getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
}
