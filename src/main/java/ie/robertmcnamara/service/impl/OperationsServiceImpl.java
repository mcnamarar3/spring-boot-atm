package ie.robertmcnamara.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import ie.robertmcnamara.data.SampleCustomerAccountData;
import ie.robertmcnamara.exception.CustomerAccessException;
import ie.robertmcnamara.exception.WithdrawalException;
import ie.robertmcnamara.model.CustomerAccount;
import ie.robertmcnamara.service.AtmService;
import ie.robertmcnamara.service.OperationsService;

public class OperationsServiceImpl implements OperationsService {

    private SampleCustomerAccountData sampleCustomerAccountData;
    private AtmService atmService;
    
    public OperationsServiceImpl(SampleCustomerAccountData sampleCustomerAccountData, AtmService atmService) {
        this.sampleCustomerAccountData = sampleCustomerAccountData;
        this.atmService = atmService;
    }

    @Override
    public CustomerAccount getAuthorizedAccount(int accountNumber, int pin) {
        CustomerAccount authorizedCustomerAccount = null;
        
        boolean accountExists = sampleCustomerAccountData.checkCustomerAccountByAccountNumber(accountNumber);
        
        if(accountExists) {
            CustomerAccount customerAccount = sampleCustomerAccountData.getCustomerAccountByAccountNumber(accountNumber);
            if(customerAccount.getPin() == pin) {
                authorizedCustomerAccount = customerAccount;
            } else {
                throw new CustomerAccessException(4001, accountNumber, "Invalid pin provided");
            }
        } else {
            throw new CustomerAccessException(4002, accountNumber, "Customer account not found");
        }

        return authorizedCustomerAccount;
    }

    @Override
    public Map<Integer, Integer> withdraw(CustomerAccount customerAccount, int requestedAmount) {
        verifyCustomerFunds(customerAccount, requestedAmount);
        return attemptToDispenseMoney(customerAccount, requestedAmount);
    }

    @Override
    public int getAtmBalance() {
        return atmService.getCurrentBalance();
    }
    
    // check if user has sufficent funds
    private void verifyCustomerFunds(CustomerAccount customerAccount, int requestedAmount) {
        int maxWithdrawalLimit = customerAccount.getBalance() + customerAccount.getOverdraftLimit();
        
        if(maxWithdrawalLimit < requestedAmount) {
            throw new WithdrawalException(4003, customerAccount.getAccountNumber(), "Customer has insufficent funds to complete transaction");
        }
    }
    
    private Map<Integer, Integer> attemptToDispenseMoney(CustomerAccount customerAccount, int requestedAmount) {
        int amountToDispence = requestedAmount;
        Map<Integer, Integer> notesDispensed = new LinkedHashMap<>();
        
        for (Map.Entry<Integer, Integer> entry : atmService.getNotesAvailable().entrySet()) {
            if(amountToDispence >= entry.getKey() && entry.getValue() > 0) {
                int notesAvailable = entry.getValue();
                int notesRequired = amountToDispence / entry.getKey();
                int notesDelta =  notesAvailable - notesRequired;
            
                if(notesDelta < 0) {
                    amountToDispence -= entry.getKey() * notesAvailable;
                    notesDispensed.put(entry.getKey(), notesAvailable); 
                } else {
                    amountToDispence -= entry.getKey() * notesRequired;
                    notesDispensed.put(entry.getKey(), notesRequired); 
                }
            }
        }
    
        if(amountToDispence == 0) {
            customerAccount.setBalance(customerAccount.getBalance() - requestedAmount);
            sampleCustomerAccountData.updateCustomerAccount(customerAccount);
            notesDispensed.forEach((note,quantity)->{
            	atmService.updateNoteAvailability(note, quantity);
            });
        } 
        else {
            // error atm has insufficent note combinations to complete dispensing
            throw new WithdrawalException(4004, customerAccount.getAccountNumber(), "ATM does not have sufficent notes to complete the transaction");
        }
        
        return notesDispensed;
    }
}
