package com.ibc.exception;

public class LeaseNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LeaseNotFoundException(Long leaseId) {
        super("Lease not found with ID: " + leaseId);
    }

    public LeaseNotFoundException(String leaseCode) {
        super("Lease not found with code: " + leaseCode);
    }
}