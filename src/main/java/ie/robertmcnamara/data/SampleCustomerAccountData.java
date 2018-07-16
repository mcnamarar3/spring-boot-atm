package ie.robertmcnamara.data;

import ie.robertmcnamara.model.CustomerAccount;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provided sample hard coded customer account data for this test application.
 * Ultimately, this should be replaced by real customer data e.g. a repository that communicates with another service
 * or a database 
 */
public class SampleCustomerAccountData {
    
    private Map<Integer, CustomerAccount> sampleCustomerAccounts;
    
    public SampleCustomerAccountData() {
        sampleCustomerAccounts = new HashMap<Integer, CustomerAccount>();
        sampleCustomerAccounts.put(123456789, createCustomerAccount(123456789, 1234, 800, 200));
        sampleCustomerAccounts.put(987654321, createCustomerAccount(987654321, 4321, 1230, 150));
    }
    
    /**
     * Checks if a account exists based on the account number
     * @param accountNumber the account number to verify a account exists
     * @return true if account exists, false otherwise 
     */
    public boolean checkCustomerAccountByAccountNumber(int accountNumber) {
        return sampleCustomerAccounts.containsKey(accountNumber);
    }
    
    /**
     * Retrieves the CustomerAccount by account number
     * @param accountNumber the account number for the account to retrieve
     * @return the customerAccount
     */
    public CustomerAccount getCustomerAccountByAccountNumber(int accountNumber) {
        return sampleCustomerAccounts.get(accountNumber);
    }
    
    /**
     * Updates the customer account provided
     * @param customerAccount the customer account to update
     */
    public void updateCustomerAccount(CustomerAccount customerAccount) {
        sampleCustomerAccounts.put(customerAccount.getAccountNumber(), customerAccount);
    }
    
    /**
     * Helper method to create a customer account
     * @param accountNumber
     * @param pin
     * @param balance
     * @param overdraftLimit
     * @return customerAccount
     */
    private CustomerAccount createCustomerAccount(int accountNumber, int pin, int balance, int overdraftLimit) {
        CustomerAccount customerAccount = new CustomerAccount(accountNumber, pin, balance, overdraftLimit);
        return customerAccount;
    }
}
