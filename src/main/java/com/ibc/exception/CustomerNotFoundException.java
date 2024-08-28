package com.ibc.exception;

public class CustomerNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(Long customerId) {
        super("Customer not found with ID: " + customerId);
    }

    public CustomerNotFoundException(String customerName) {
        super("Customer not found with name: " + customerName);
    }

	public Long getCustomerId() {
		// TODO Auto-generated method stub
		return null;
	}

}