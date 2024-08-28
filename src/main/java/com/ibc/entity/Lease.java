package com.ibc.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "Lease")
public class Lease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaseId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private LocalDate leaseStartDate;

    private LocalDate leaseEndDate;

    @Column(nullable = false)
    private boolean isActive;

    // Default Constructor
    public Lease() {
    }

    // Constructor to set initial values
    public Lease(Car car, Customer customer, LocalDate leaseStartDate, LocalDate leaseEndDate) {
        this.car = car;
        this.customer = customer;
        this.leaseStartDate = leaseStartDate;
        this.setLeaseEndDate(leaseEndDate);
        this.isActive = true;
    }

    // Getters and Setters

    public Long getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(Long leaseId) {
        this.leaseId = leaseId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(LocalDate leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public LocalDate getLeaseEndDate() {
        return leaseEndDate;
    }

    public void setLeaseEndDate(LocalDate leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    // Calculate the number of days between the lease start and end dates
    public int getDays() {
        if (leaseStartDate != null && leaseEndDate != null) {
            return (int) ChronoUnit.DAYS.between(leaseStartDate, leaseEndDate);
        }
        return 0;
    }

    // Calculate the total cost based on the number of days and the car's price per day
    public double getTotalCost() {
        return getDays() * car.getPricePerDay();
    }

    @Override
    public String toString() {
        return "Lease [leaseId=" + leaseId + ", car=" + car + ", customer=" + customer + ", leaseStartDate="
                + leaseStartDate + ", leaseEndDate=" + leaseEndDate + ", isActive=" + isActive + ", days=" + getDays()
                + ", totalCost=" + getTotalCost() + "]";
    }

	public void setTotalCost(double totalCost) {
		// TODO Auto-generated method stub
		
	}
}
