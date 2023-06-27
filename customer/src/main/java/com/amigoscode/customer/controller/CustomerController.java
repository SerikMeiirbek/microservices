package com.amigoscode.customer.controller;

import com.amigoscode.customer.model.Customer;
import com.amigoscode.customer.request.CustomerRegistrationRequest;
import com.amigoscode.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
@AllArgsConstructor
public class CustomerController{
    private final CustomerService customerService;


//    @CircuitBreaker(name="customer", fallbackMethod = "customerServiceFallBack")
//    @Retry(name="customer", fallbackMethod = "customerServiceRetryBack")
    @PostMapping()
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest){
        log.info("new customer registration {}", customerRegistrationRequest);
        customerService.registerCustomer(customerRegistrationRequest);
    }

    @GetMapping()
    public ResponseEntity<List<Customer>> getCustomer(){
        log.info("Get all customers log");
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

//    public void customerServiceFallBack(String userName, Exception e){
//        log.error("exception{}", e.getMessage());
//    }
//
//    public void  customerServiceRetryBack(String userName, Exception e){
//        log.error("Retried 3 times. User-service is not healthy{}", e.getMessage());
//    }


}
