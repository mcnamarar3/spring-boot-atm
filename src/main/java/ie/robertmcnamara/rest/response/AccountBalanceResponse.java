package ie.robertmcnamara.rest.response;

/**
 * This class models a response to the check balance request.
 */
public class AccountBalanceResponse {

    private int accountNumber;
    private int balance;
    private int overdraftLimit;
    private int maxWithdrawalLimit;
    
    public int getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public int getBalance() {
        return balance;
    }
    
    public void setBalance(int balance) {
        this.balance = balance;
    }
    
    public int getOverdraftLimit() {
        return overdraftLimit;
    }
    
    public void setOverdraftLimit(int overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
    
    public int getMaxWithdrawalLimit() {
        return maxWithdrawalLimit;
    }
    
    public void setMaxWithdrawalLimit() {
        maxWithdrawalLimit = balance + overdraftLimit;
    }
}


