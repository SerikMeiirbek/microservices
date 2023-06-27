package com.amigoscode.customer.service;

import com.amigoscode.clients.fraud.FraudCheckResponse;
import com.amigoscode.clients.fraud.FraudClient;
import com.amigoscode.clients.notification.NotificationClient;
import com.amigoscode.clients.notification.NotificationRequest;
import com.amigoscode.customer.model.Customer;
import com.amigoscode.customer.repository.CustomerRepository;
import com.amigoscode.customer.request.CustomerRegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        //todo: check if email valid
        //todo: check if email not taken

        customerRepository.saveAndFlush(customer);

        //todo: check if fraudster
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://localhost:8081/api/v1/fraud-check/{customerId}",
//                "http://FRAUD/api/v1/fraud-check/{customerId}", use this for eureka service
//                FraudCheckResponse.class,
//                customer.getId()
//        );

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());



        if (fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }

        //todo: send notification
        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to Amigoscode...", customer.getFirstName())
                ));

        //todo: make it async. i.e add to queue

    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }


}
