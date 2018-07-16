package ie.robertmcnamara.rest.response;

import java.util.Map;

/**
 * This class models a response to the withdrawal request.
 */
public class WithdrawalResponse {
     private int accountNumber;
     private int amountWithdrawn;
     private Map<Integer, Integer> notesDispensed;
     private int balance;
     private int balanceWithOverdraft;
     
     public int getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public int getAmountWithdrawn() {
        return amountWithdrawn;
    }
    
    public void setAmountWithdrawn(int amountWithdrawn) {
        this.amountWithdrawn = amountWithdrawn;
    }
    
    public Map<Integer, Integer> getNotesDispensed() {
        return notesDispensed;
    }
    
    public void setNotesDispensed(Map<Integer, Integer> notesDispensed) {
        this.notesDispensed = notesDispensed;
    }
    
    public int getBalance() {
        return balance;
    }
    
    public void setBalance(int balance) {
        this.balance = balance;
    }
    
    public int getBalanceWithOverdraft() {
        return balanceWithOverdraft;
    }
    
    public void setBalanceWithOverdraft(int balanceWithOverdraft) {
        this.balanceWithOverdraft = balanceWithOverdraft;
    }
}

