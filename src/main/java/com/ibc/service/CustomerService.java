package com.ibc.service;

import com.ibc.entity.Customer;
import com.ibc.exception.CustomerNotFoundException;
import com.ibc.repository.CustomerRepository;
import com.ibc.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

	@Autowired
    private CustomerRepository customerRepository;

    public List<Customer> addCustomers(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            throw new IllegalArgumentException("Customer list cannot be null or empty.");
        }

        List<Customer> addedCustomers = new ArrayList<>();
        List<String> invalidEmails = new ArrayList<>();

        for (Customer customer : customers) {
            if (customer == null || !ValidationUtil.isValidEmail(customer.getEmail())) {
                invalidEmails.add(customer != null ? customer.getEmail() : "null");
                continue;
            }
            try {
                addedCustomers.add(customerRepository.save(customer));
            } catch (DataIntegrityViolationException ex) {
                throw new IllegalArgumentException("Data integrity violation for customer with email: " 
                                                   + customer.getEmail() + ". Error: " + ex.getMessage());
            }
        }

        if (!invalidEmails.isEmpty()) {
            throw new IllegalArgumentException("Invalid email address format for: " + String.join(", ", invalidEmails));
        }

        return addedCustomers;
    }

    public void removeCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        try {
            customerRepository.deleteById(customerId);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Cannot delete customer due to data integrity issues: " + ex.getMessage());
        }
    }
    
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    public Customer findCustomerByEmail(String email) {
        // Validate email format
        if (!ValidationUtil.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address format.");
        }

        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotFoundException(email);
        }
        return customer;
    }

    public Customer updateCustomer(Long customerId, Customer customer) {
        // Check if customer with the provided ID exists
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }

        // Ensure the ID in the request body matches the ID in the URL
        if (!customerId.equals(customer.getCustomerId())) {
            throw new IllegalArgumentException("Customer ID in the request body does not match the URL ID.");
        }

        try {
            // Save the updated customer details
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Data integrity violation: " + ex.getMessage());
        }
    }
}
