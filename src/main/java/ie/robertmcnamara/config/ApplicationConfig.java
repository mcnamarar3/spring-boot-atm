package ie.robertmcnamara.config;

import ie.robertmcnamara.data.SampleCustomerAccountData;
import ie.robertmcnamara.rest.CustomerAccountRestController;
import ie.robertmcnamara.rest.CustomerAccountRestControllerAdvice;
import ie.robertmcnamara.service.AtmService;
import ie.robertmcnamara.service.OperationsService;
import ie.robertmcnamara.service.impl.AtmServiceImpl;
import ie.robertmcnamara.service.impl.OperationsServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The JavaConfig class for this service, creates and configures all necessary beans.
 * Within this project, all beans are managed here, this removed the automagically Spring autowiring from the project.
 * Instead composition is used to provide objects required to classes. 
 */
@Configuration
public class ApplicationConfig {
    
    @Bean
    public AtmService atmService() {
        return new AtmServiceImpl();
    }
    
    @Bean
    public SampleCustomerAccountData sampleCustomerAccountData() {
        return new SampleCustomerAccountData();
    }
    
    @Bean
    public OperationsService operationsService() {
        return new OperationsServiceImpl(sampleCustomerAccountData(), atmService());
    }
    
    @Bean
    public CustomerAccountRestControllerAdvice customerAccountRestControllerAdvice() {
        return new CustomerAccountRestControllerAdvice();
    }
    
    @Bean
    public CustomerAccountRestController customerAccountRestController() {
        return new CustomerAccountRestController(operationsService());
    }
}
