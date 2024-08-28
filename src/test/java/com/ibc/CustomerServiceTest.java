package com.ibc;

import com.ibc.datautil.CustomerTestDataUtil;
import com.ibc.entity.Customer;
import com.ibc.exception.CustomerNotFoundException;
import com.ibc.repository.CustomerRepository;
import com.ibc.service.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCustomersSuccess() {
        List<Customer> customers = CustomerTestDataUtil.getDynamicCustomerData();
        // Mock the repository save method to return the input customer
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<Customer> addedCustomers = customerService.addCustomers(customers);

        // Assert that the number of added customers is the same as the number of input customers
        assertEquals(customers.size(), addedCustomers.size());

        // Verify that save was called for each customer
        for (Customer customer : customers) {
            verify(customerRepository, times(1)).save(customer);
        }
    }


    @Test
    void testAddCustomers_InvalidEmail() {
        Customer customer = new Customer(null, "Bharath", "Chandra", "invalid-email", "9160348766", "224 Lelle Vari St, Chirala");

        List<Customer> customers = List.of(customer);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            customerService.addCustomers(customers)
        );

        assertEquals("Invalid email address format for: invalid-email", exception.getMessage());
        verify(customerRepository, never()).saveAll(anyList());
    }

    @Test
    void testRemoveCustomer_CustomerExists() {
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(true);

        customerService.removeCustomer(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void testRemoveCustomer_CustomerNotFound() {
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(false);

        assertThrows(CustomerNotFoundException.class, () -> customerService.removeCustomer(customerId));

        verify(customerRepository, never()).deleteById(customerId);
    }

    @Test
    void testListCustomers() {
        List<Customer> customers = CustomerTestDataUtil.getDynamicCustomerData();

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.listCustomers();

        assertNotNull(result);
        assertEquals(customers.size(), result.size());
        assertEquals("Bharath", result.get(0).getFirstName());
        assertEquals("Sunil", result.get(1).getFirstName());
    }

    @Test
    void testFindCustomerById_CustomerExists() {
        Long customerId = 1L;
        Customer customer = CustomerTestDataUtil.getDynamicCustomerData().get(0);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerService.findCustomerById(customerId);

        assertEquals(customer, result);
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testFindCustomerById_CustomerNotFound() {
        Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.findCustomerById(customerId));

        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testFindCustomerByEmail_ValidEmail() {
        String email = "bharathchandra.ila@gmail.com";
        Customer customer = CustomerTestDataUtil.getDynamicCustomerData().get(0);

        when(customerRepository.findByEmail(email)).thenReturn(customer);

        Customer result = customerService.findCustomerByEmail(email);

        assertEquals(customer, result);
        verify(customerRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindCustomerByEmail_InvalidEmailFormat() {
        String invalidEmail = "invalid-email";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            customerService.findCustomerByEmail(invalidEmail)
        );

        assertEquals("Invalid email address format.", exception.getMessage());
        verify(customerRepository, never()).findByEmail(anyString());
    }

    @Test
    void testUpdateCustomer_CustomerExists() {
        Long customerId = 1L;
        Customer customer = CustomerTestDataUtil.getDynamicCustomerData().get(0);

        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.updateCustomer(customerId, customer);

        assertEquals(customer, result);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testUpdateCustomer_CustomerNotFound() {
        Long customerId = 1L;
        Customer customer = CustomerTestDataUtil.getDynamicCustomerData().get(0);

        when(customerRepository.existsById(customerId)).thenReturn(false);

        assertThrows(CustomerNotFoundException.class, () ->
            customerService.updateCustomer(customerId, customer)
        );

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer_IdMismatch() {
        Long customerId = 1L;
        Customer customer = new Customer(2L, "Bharath", "Chandra", "bharathchandra.ila@gmail.com", "9160348766", "224 Lelle Vari St, Chirala");

        when(customerRepository.existsById(customerId)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            customerService.updateCustomer(customerId, customer)
        );

        assertEquals("Customer ID in the request body does not match the URL ID.", exception.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));
    }
}
