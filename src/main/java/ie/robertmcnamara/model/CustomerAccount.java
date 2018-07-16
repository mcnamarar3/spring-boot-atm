package ie.robertmcnamara.model;

/**
 * This class models a Cutomer bank account.
 */
public class CustomerAccount {
    
    private int accountNumber;
    private int pin;
    private int balance;
    private int overdraftLimit;
    
    public CustomerAccount(int accountNumber, int pin, int balance, int overdraftLimit) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.overdraftLimit = overdraftLimit;
    }
    
    public int getAccountNumber() {
        return accountNumber;
    }
    
    public int getPin() {
        return pin;
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
}
