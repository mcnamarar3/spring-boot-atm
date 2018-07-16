package ie.robertmcnamara.rest;

import ie.robertmcnamara.exception.WithdrawalException;
import ie.robertmcnamara.exception.CustomerAccessException;
import ie.robertmcnamara.rest.response.ErrorResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class handles exceptions thrown by the application, 
 * to provide the API user with JSON error responses
 */
@ControllerAdvice(annotations = RestController.class)
public class CustomerAccountRestControllerAdvice extends ResponseEntityExceptionHandler{

    @ExceptionHandler(WithdrawalException.class)
    public ResponseEntity<ErrorResponse> withdrawalException(final WithdrawalException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(e.getErrorCode());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setAccountNumber(e.getAccountNumber());
        return ResponseEntity.status(400).body(errorResponse);
    }
    
    @ExceptionHandler(CustomerAccessException.class)
    public ResponseEntity<ErrorResponse> customerAccessException(final CustomerAccessException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(e.getErrorCode());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setAccountNumber(e.getAccountNumber());
        return ResponseEntity.status(401).body(errorResponse);
    }
    

}
