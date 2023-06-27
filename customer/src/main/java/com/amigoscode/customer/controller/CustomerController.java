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

}
