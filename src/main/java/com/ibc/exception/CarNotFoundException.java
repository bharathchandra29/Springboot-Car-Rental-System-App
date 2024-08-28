package com.ibc.exception;

public class CarNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CarNotFoundException(Long carId) {
        super("Car not found with ID: " + carId);
    }

    public CarNotFoundException(String carNumber) {
        super("Car not found with number: " + carNumber);
    }
}
