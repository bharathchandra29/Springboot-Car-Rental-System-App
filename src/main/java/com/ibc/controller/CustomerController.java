package com.ibc.controller;

import com.ibc.entity.Customer;
import com.ibc.exception.CustomerNotFoundException; // Assume this is a custom exception
import com.ibc.service.CustomerService;
import com.ibc.util.ValidationUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bharathapi")
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //http://localhost:8080/customers/addcustomers
    @PostMapping("/addcustomers")
    public ResponseEntity<?> addCustomers(@RequestBody List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer list cannot be null or empty.");
        }

        try {
            List<Customer> addedCustomers = customerService.addCustomers(customers);
            return new ResponseEntity<>(addedCustomers, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data integrity violation: " + ex.getMessage());
        }
    }
    
    //http://localhost:8080/customers/delete/{customerId}
    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> removeCustomer(@PathVariable Long customerId) {
        try {
            customerService.removeCustomer(customerId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Customer with ID " + customerId + " has been successfully deleted.");
        } catch (CustomerNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer with ID " + customerId + " not found.");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }
    }
    
    //http://localhost:8080/customers/update/{customerId}
    @PutMapping("/update/{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer) {
        // Validate email format
        if (!ValidationUtil.isValidEmail(customer.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid email format.");
        }

        try {
            // Ensure the ID in the request body matches the ID in the URL
            if (!customerId.equals(customer.getCustomerId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Customer ID in the request body does not match the URL ID.");
            }

            Customer updatedCustomer = customerService.updateCustomer(customerId, customer);
            return ResponseEntity.ok("Customer updated successfully: " + updatedCustomer);

        } catch (CustomerNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer with ID " + customerId + " not found.");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Data integrity violation: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }
    
    //http://localhost:8080/customers/allcustomers
    @GetMapping("/allcustomers")
    public ResponseEntity<List<Customer>> listCustomers() {
        List<Customer> customers = customerService.listCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    //http://localhost:8080/customers/{customerId}
    @GetMapping("/{customerId}")
    public ResponseEntity<?> findCustomerById(@PathVariable Long customerId) {
        try {
            Customer customer = customerService.findCustomerById(customerId);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (CustomerNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer with ID " + customerId + " not found.");
        }
    }

    //http://localhost:8080/customers/email/{email}
    @GetMapping("/email/{email}")
    public ResponseEntity<?> findCustomerByEmail(@PathVariable String email) {
        // Validate email format before querying the service
        if (!ValidationUtil.isValidEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid email format.");
        }

        try {
            Customer customer = customerService.findCustomerByEmail(email);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (CustomerNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer with email " + email + " not found.");
        }
    }
}
