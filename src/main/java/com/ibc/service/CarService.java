package com.ibc.service;

import com.ibc.entity.Car;
import com.ibc.exception.CarNotFoundException;
import com.ibc.repository.CarRepository;
import com.ibc.repository.LeaseRepository; // Assuming you have this repository for leases

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private LeaseRepository leaseRepository; // Inject the lease repository

    public List<Car> addCars(List<Car> cars) throws CarNotFoundException {
        // Check if the list is null
        if (cars == null) {
            throw new CarNotFoundException("Car list is required.");
        }

        // Check for each car in the list
        for (Car car : cars) {
            // Check if carNumber is null or empty
            if (car.getCarNumber() == null || car.getCarNumber().isEmpty()) {
                throw new CarNotFoundException("Car number is required for one of the cars.");
            }

            // Check if a car with the same number already exists
            if (carRepository.findByCarNumber(car.getCarNumber()).isPresent()) {
                throw new CarNotFoundException("Car with number " + car.getCarNumber() + " already exists.");
            }
        }

        // Save all cars to the repository
        return carRepository.saveAll(cars);
    }

    public Car updateCar(Car car) throws CarNotFoundException {
        if (!carRepository.existsById(car.getCarId())) {
            throw new CarNotFoundException("Car with ID " + car.getCarId() + " not found.");
        }
        return carRepository.save(car);
    }

    @Transactional
    public void removeCar(Long carId) throws CarNotFoundException {
        if (!carRepository.existsById(carId)) {
            throw new CarNotFoundException("Car with ID " + carId + " not found.");
        }

        // Check for existing leases
        if (leaseRepository.existsById(carId)) {
            throw new DataIntegrityViolationException("Cannot delete car with existing leases.");
        }

        carRepository.deleteById(carId);
    }

    public List<Car> listCars() {
        return carRepository.findAll();
    }

    public Car findCarById(Long carId) throws CarNotFoundException {
        return carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found."));
    }

    public Car findCarByNumber(String carNumber) throws CarNotFoundException {
        return carRepository.findByCarNumber(carNumber)
                .orElseThrow(() -> new CarNotFoundException("Car with number " + carNumber + " not found."));
    }

    public List<Car> findCarsByAvailability(boolean isAvailable) {
        return carRepository.findByAvailable(isAvailable);
    }
}
