package com.ibc.util;

import com.ibc.entity.Lease;

public class ValidationUtil {

    // Example validation for Lease
    public static boolean isValidLease(Lease lease) {
        // Add actual validation logic for the Lease object
        return lease != null && lease.getCar() != null && lease.getCustomer() != null;
    }
    
    // Validate if a car number is valid (assuming a pattern for car numbers)
    public static boolean isValidCarNumber(String carNumber) {
        // Implement validation logic here
        return carNumber != null && carNumber.matches("[A-Z0-9]{1,7}"); // Example regex
    }
    
    // Validate if a customer ID is valid (example implementation)
    public static boolean isValidCustomerId(Long customerId) {
        return customerId != null && customerId > 0;
    }
    
    // Validate if a lease ID is valid (example implementation)
    public static boolean isValidLeaseId(Long leaseId) {
        return leaseId != null && leaseId > 0;
    }
    
    // Validate the car ID
    public static boolean isValidCarId(Long carId) {
        return carId != null && carId > 0;
    }

    // Validate if an email is in a valid format
    public static boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+$";
        return email != null && email.matches(emailPattern);
    }

    // Validate if a string is not empty
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    // Example validation for other fields (if needed)
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String phonePattern = "^[0-9]{10}$"; // Example pattern for phone numbers
        return phoneNumber != null && phoneNumber.matches(phonePattern);
    }
}
