package com.ibc.exception;

public class PaymentNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PaymentNotFoundException(Long paymentId) {
        super("Payment not found with ID: " + paymentId);
    }

    public PaymentNotFoundException(String paymentReference) {
        super("Payment not found with reference: " + paymentReference);
    }
}