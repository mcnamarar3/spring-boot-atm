package ie.robertmcnamara.service;

import java.util.Map;

import ie.robertmcnamara.model.CustomerAccount;

/**
 * Simple service that represents operations that can happen internal in the application
 * as a result of the API usage
 */
public interface OperationsService {
    CustomerAccount getAuthorizedAccount(int accountNumber, int pin);
    Map<Integer, Integer> withdraw(CustomerAccount customerAccount, int requestedAmount);
    int getAtmBalance();
}

