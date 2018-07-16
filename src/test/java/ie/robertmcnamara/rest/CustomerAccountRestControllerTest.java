package ie.robertmcnamara.rest;

import java.util.LinkedHashMap;
import java.util.Map;

import ie.robertmcnamara.exception.CustomerAccessException;
import ie.robertmcnamara.exception.WithdrawalException;
import ie.robertmcnamara.model.CustomerAccount;
import ie.robertmcnamara.rest.response.AccountBalanceResponse;
import ie.robertmcnamara.rest.response.WithdrawalResponse;
import ie.robertmcnamara.service.OperationsService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerAccountRestControllerTest {
    
    @InjectMocks
    CustomerAccountRestController customerAccountRestController;
    
    @Mock
    OperationsService mockOperationsService;
    
    private int validAccountNumber = 123456789;
    private int invalidAccountNumber = 5555;
    private int validPin = 1234;
    private int invalidPin = 5555;
    private int balance = 800;
    private int overdraft = 150;
    
    @Test
    public void getBalance_withValidAccountNumberAndPin() {
        CustomerAccount customerAccount = new CustomerAccount(validAccountNumber, validPin, balance, overdraft);
        
        when(mockOperationsService.getAuthorizedAccount(validAccountNumber, validPin)).thenReturn(customerAccount);
        
        ResponseEntity<AccountBalanceResponse> response = customerAccountRestController.getBalance(validAccountNumber, validPin);
        
        assertEquals(validAccountNumber, response.getBody().getAccountNumber()); 
        assertEquals(balance, response.getBody().getBalance()); 
        assertEquals(overdraft, response.getBody().getOverdraftLimit()); 
        assertEquals(balance + overdraft, response.getBody().getMaxWithdrawalLimit()); 
    }

    @Test(expected = CustomerAccessException.class)
    public void getBalance_withInvalidAccountNumber() {
        when(mockOperationsService.getAuthorizedAccount(invalidAccountNumber, validPin))
            .thenThrow(new CustomerAccessException(4001, invalidAccountNumber, "invalid account number"));
            
        customerAccountRestController.getBalance(invalidAccountNumber, validPin);
    }
    
    @Test(expected = CustomerAccessException.class)
    public void getBalance_withInvalidPin() {
        when(mockOperationsService.getAuthorizedAccount(validAccountNumber, invalidPin))
            .thenThrow(new CustomerAccessException(4001, validAccountNumber, "invalid pin provided"));
        
        customerAccountRestController.getBalance(validAccountNumber, invalidPin);
    }
    
    @Test(expected = CustomerAccessException.class)
    public void withdrawal_withInvalidAccountNumber() {
        when(mockOperationsService.getAuthorizedAccount(invalidAccountNumber, validPin))
            .thenThrow(new CustomerAccessException(4001, invalidAccountNumber, "invalid account number"));
            
        customerAccountRestController.withdraw(invalidAccountNumber, validPin, 5);
    }
    
    @Test(expected = CustomerAccessException.class)
    public void withdrawal_withInvalidPin() {
        when(mockOperationsService.getAuthorizedAccount(validAccountNumber, invalidPin))
            .thenThrow(new CustomerAccessException(4001, invalidAccountNumber, "invalid pin provided"));
            
        customerAccountRestController.withdraw(validAccountNumber, invalidPin, 5);
    }
    
    @Test(expected = WithdrawalException.class)
    public void withdrawal_withNegativeAmount() {
        CustomerAccount customerAccount = new CustomerAccount(validAccountNumber, validPin, balance, overdraft);
        
        when(mockOperationsService.getAuthorizedAccount(validAccountNumber, validPin)).thenReturn(customerAccount);
            
        customerAccountRestController.withdraw(validAccountNumber, validPin, -5);
    }
    
    @Test(expected = WithdrawalException.class)
    public void withdrawal_withInvalidAmount() {
        CustomerAccount customerAccount = new CustomerAccount(validAccountNumber, validPin, balance, overdraft);
        
        when(mockOperationsService.getAuthorizedAccount(validAccountNumber, validPin)).thenReturn(customerAccount);
            
        customerAccountRestController.withdraw(validAccountNumber, validPin, 3);
    }
    
    @Test
    public void withdrawal_withValidInput() {
        CustomerAccount customerAccount = new CustomerAccount(validAccountNumber, validPin, balance, overdraft);
        Map<Integer,Integer> notesDispensed = new LinkedHashMap<>();
        notesDispensed.put(10, 1);
        
        when(mockOperationsService.getAuthorizedAccount(validAccountNumber, validPin)).thenReturn(customerAccount);
        when(mockOperationsService.getAtmBalance()).thenReturn(20);
        when(mockOperationsService.withdraw(customerAccount, 10)).thenReturn(notesDispensed);
            
        ResponseEntity<WithdrawalResponse> response = customerAccountRestController.withdraw(validAccountNumber, validPin, 10);
        
        assertEquals(validAccountNumber, response.getBody().getAccountNumber());
        assertEquals(10, response.getBody().getAmountWithdrawn());
        assertEquals(notesDispensed, response.getBody().getNotesDispensed());
    }
    
    @Test(expected = WithdrawalException.class)
    public void withdrawal_withInsufficentAtmFunds() {
        CustomerAccount customerAccount = new CustomerAccount(validAccountNumber, validPin, balance, overdraft);
        
        when(mockOperationsService.getAuthorizedAccount(validAccountNumber, validPin)).thenReturn(customerAccount);
        when(mockOperationsService.getAtmBalance()).thenReturn(500);
        
        customerAccountRestController.withdraw(validAccountNumber, validPin, 980);
    }
    
    @Test
    public void withdrawal_withInsufficentCustomerBalanceAndSufficentOverdraft() {
        CustomerAccount customerAccount = new CustomerAccount(validAccountNumber, validPin, 0, 20);
        Map<Integer,Integer> notesDispensed = new LinkedHashMap<>();
        notesDispensed.put(10, 1);
        
        when(mockOperationsService.getAuthorizedAccount(validAccountNumber, validPin)).thenReturn(customerAccount);
        when(mockOperationsService.getAtmBalance()).thenReturn(20);
        when(mockOperationsService.withdraw(customerAccount, 10)).thenReturn(notesDispensed);
            
        ResponseEntity<WithdrawalResponse> response = customerAccountRestController.withdraw(validAccountNumber, validPin, 10);
        
        assertEquals(validAccountNumber, response.getBody().getAccountNumber());
        assertEquals(10, response.getBody().getAmountWithdrawn());
        assertEquals(notesDispensed, response.getBody().getNotesDispensed());
        
    }

    @Test(expected = WithdrawalException.class)
    public void withdrawal_withInsufficentCustomerBalanceAndInsufficentOverdraft() {
        CustomerAccount customerAccount = new CustomerAccount(validAccountNumber, validPin, balance, overdraft);
        
        when(mockOperationsService.getAuthorizedAccount(validAccountNumber, validPin)).thenReturn(customerAccount);
        when(mockOperationsService.getAtmBalance()).thenReturn(1000);
        when(mockOperationsService.withdraw(customerAccount, 980)).thenThrow(new WithdrawalException(4007, validAccountNumber, "customer has insufficent funds"));
        
        customerAccountRestController.withdraw(validAccountNumber, validPin, 980);
    }    
    
}
