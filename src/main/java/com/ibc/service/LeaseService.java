package com.ibc.service;

import com.ibc.entity.Lease;
import com.ibc.entity.Car;
import com.ibc.exception.LeaseNotFoundException;
import com.ibc.repository.LeaseRepository;
import com.ibc.repository.CarRepository;
import com.ibc.util.DateUtil;
import com.ibc.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private CarRepository carRepository;

    public Lease createLease(Lease lease) {
        // Validate lease details
        if (!ValidationUtil.isValidLease(lease)) {
            throw new IllegalArgumentException("Invalid lease details.");
        }

        // If the lease is active, set the start date if not provided
        if (lease.isActive()) {
            if (lease.getLeaseStartDate() == null) {
                lease.setLeaseStartDate(DateUtil.getCurrentDate());
            }

            // Calculate total cost for the lease
            Car car = lease.getCar();
            int days = lease.getDays();
            double totalCost = car.getPricePerDay() * days;
            lease.setTotalCost(totalCost);
        }

        return leaseRepository.save(lease);
    }

    public Lease returnCar(Long leaseId) {
        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() -> new LeaseNotFoundException(leaseId));

        if (lease.isActive()) {
            lease.setLeaseEndDate(DateUtil.getCurrentDate());
            lease.setActive(false);
            return leaseRepository.save(lease);
        } else {
            throw new IllegalStateException("Lease is already completed.");
        }
    }

    public List<Lease> listActiveLeases() {
        return leaseRepository.findByIsActive(true);
    }

    public List<Lease> listInactiveLeases() {
        return leaseRepository.findByIsActive(false);
    }

    public List<Lease> listLeaseHistory() {
        return leaseRepository.findAll();
    }

    public List<Lease> listLeasesByCustomer(Long customerId) {
        if (!ValidationUtil.isValidCustomerId(customerId)) {
            throw new IllegalArgumentException("Invalid customer ID.");
        }
        return leaseRepository.findByCustomer_CustomerId(customerId);
    }

    public Lease findLeaseById(Long leaseId) {
        return leaseRepository.findById(leaseId)
                .orElseThrow(() -> new LeaseNotFoundException(leaseId));
    }

    public void deleteLease(Long leaseId) {
        // Check if the lease exists
        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() -> new LeaseNotFoundException(leaseId));

        // Delete the lease
        leaseRepository.delete(lease);
    }

    public CarRepository getCarRepository() {
        return carRepository;
    }

    public void setCarRepository(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
}
