package com.ibc.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;
    private String carNumber;
    private String make;
    private String model;
    private int year;
    private double pricePerDay;
    private boolean available; // true if available, false if rented
    
    // Getters and Setters
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
	public Car(Long carId, String carNumber, String make, String model, int year, double pricePerDay,
			boolean available) {
		super();
		this.carId = carId;
		this.carNumber = carNumber;
		this.make = make;
		this.model = model;
		this.year = year;
		this.pricePerDay = pricePerDay;
		this.available = available;
	}
	
	public Car() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
    public String toString() {
        return "Car [carId=" + carId + ", carNumber=" + carNumber + ", make=" + make + ", model=" + model + ", year="
                + year + ", pricePerDay=" + pricePerDay + ", available=" + available + "]";
    }
}
