package ie.robertmcnamara.rest;

import java.util.Map;

import ie.robertmcnamara.exception.WithdrawalException;
import ie.robertmcnamara.model.CustomerAccount;
import ie.robertmcnamara.rest.response.AccountBalanceResponse;
import ie.robertmcnamara.rest.response.WithdrawalResponse;
import ie.robertmcnamara.service.OperationsService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class CustomerAccountRestController {
    
    private OperationsService operationsService;
    
    public CustomerAccountRestController(OperationsService operationsService) {
        this.operationsService = operationsService;
    }
    
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountBalanceResponse> getBalance(@PathVariable int accountNumber, @RequestParam int pin) {
        CustomerAccount customerAccount = operationsService.getAuthorizedAccount(accountNumber, pin);

        int accountBalance = customerAccount.getBalance();
        int overdraftLimit = customerAccount.getOverdraftLimit();
            
        AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse();
        accountBalanceResponse.setAccountNumber(accountNumber);
        accountBalanceResponse.setBalance(accountBalance);
        accountBalanceResponse.setOverdraftLimit(overdraftLimit);
        accountBalanceResponse.setMaxWithdrawalLimit();
        return ResponseEntity.ok().body(accountBalanceResponse);
    }
    
    // using post rather than put as this is not a impodent operation
    @PostMapping("/{accountNumber}")
    public ResponseEntity<WithdrawalResponse> withdraw(@PathVariable int accountNumber, @RequestParam int pin,  @RequestParam int amount) {
        CustomerAccount customerAccount = operationsService.getAuthorizedAccount(accountNumber, pin);
        
        validateRequestAmount(accountNumber, amount);
             
        Map<Integer,Integer> notesDispensed = operationsService.withdraw(customerAccount, amount);
        WithdrawalResponse withdrawalResponse = new WithdrawalResponse();
        withdrawalResponse.setAccountNumber(customerAccount.getAccountNumber());
        withdrawalResponse.setAmountWithdrawn(amount);
        withdrawalResponse.setNotesDispensed(notesDispensed);
        withdrawalResponse.setBalance(customerAccount.getBalance());
        withdrawalResponse.setBalanceWithOverdraft(customerAccount.getBalance() + customerAccount.getOverdraftLimit());
        return ResponseEntity.accepted().body(withdrawalResponse); 
    }
    
    private void validateRequestAmount(int accountNumber, int requestAmount) {
        if(requestAmount < 0) {
           throw new WithdrawalException(4005, accountNumber, "Invalid amount requested, please request an positive amount that is a multiple of 5 euro");
        }
        
        if(requestAmount % 5 != 0) {
            throw new WithdrawalException(4005, accountNumber, "Invalid amount requested, please request an amount that is a multiple of 5 euro");
        }
        
        if(operationsService.getAtmBalance() < requestAmount) {
            throw new WithdrawalException(4007, accountNumber, "ATM has insufficent funds to complete transaction");
        }
    }
}
