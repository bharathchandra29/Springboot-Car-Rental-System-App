package com.ibc.controller;

import com.ibc.entity.Car;
import com.ibc.exception.CarNotFoundException;
import com.ibc.service.CarService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cars")
@SecurityRequirement(name = "bharathapi")
public class CarController {

    @Autowired
    private CarService carService;

    //http://localhost:8080/cars/addcar
    @PostMapping("/addcar")
    public ResponseEntity<?> addCars(@RequestBody List<Car> cars) {
        try {
            List<Car> addedCars = carService.addCars(cars);
            return new ResponseEntity<>(addedCars, HttpStatus.CREATED);
        } catch (CarNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }
    
    //http://localhost:8080/cars/updatecar/1
    @PutMapping("/updatecar/{carId}")
    public ResponseEntity<Car> updateCar(@PathVariable Long carId, @RequestBody Car car) {
        if (!carId.equals(car.getCarId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        try {
            Car updatedCar = carService.updateCar(car);
            return new ResponseEntity<>(updatedCar, HttpStatus.OK);
        } catch (CarNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    //http://localhost:8080/cars/delete/5
    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<String> removeCar(@PathVariable Long carId) {
        try {
            carService.removeCar(carId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Car with ID " + carId + " has been successfully deleted.");
        } catch (CarNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Car with ID " + carId + " not found.");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }
    }

    //http://localhost:8080/cars/allcars
    @GetMapping("/allcars")
    public ResponseEntity<List<Car>> listCars() {
        List<Car> cars = carService.listCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    //http://localhost:8080/cars/carid/1
    @GetMapping("/carid/{carId}")
    public ResponseEntity<?> findCarById(@PathVariable Long carId) {
        try {
            Car car = carService.findCarById(carId);
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (CarNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Car with ID " + carId + " not found.");
        }
    }

    //http://localhost:8080/cars/carnumber/1
    @GetMapping("/carnumber/{carNumber}")
    public ResponseEntity<?> findCarByNumber(@PathVariable String carNumber) {
        try {
            Car car = carService.findCarByNumber(carNumber);
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (CarNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Car with number " + carNumber + " not found.");
        }
    }

    //http://localhost:8080/cars/number/available
    @GetMapping("/available")
    public ResponseEntity<List<Car>> listCarsByAvailability() {
        List<Car> cars = carService.findCarsByAvailability(true);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    //http://localhost:8080/cars/number/rented
    @GetMapping("/rented")
    public ResponseEntity<List<Car>> listRentedCars() {
        List<Car> rentedCars = carService.findCarsByAvailability(false);
        return new ResponseEntity<>(rentedCars, HttpStatus.OK);
    }
}
