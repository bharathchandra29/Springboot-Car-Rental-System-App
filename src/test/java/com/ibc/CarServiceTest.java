package com.ibc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.ibc.datautil.CarTestDataUtil;
import com.ibc.entity.Car;
import com.ibc.exception.CarNotFoundException;
import com.ibc.repository.CarRepository;
import com.ibc.repository.LeaseRepository;
import com.ibc.service.CarService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private LeaseRepository leaseRepository;

    @InjectMocks
    private CarService carService;

    @Test
    public void testAddCars() throws CarNotFoundException {
        List<Car> cars = CarTestDataUtil.getDynamicCarData();

        when(carRepository.saveAll(cars)).thenReturn(cars);

        List<Car> result = carService.addCars(cars);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(10, result.size());
        for (Car car : cars) {
            Assertions.assertTrue(result.stream().anyMatch(c -> car.getCarNumber().equals(c.getCarNumber())));
        }
    }

    @Test
    public void testUpdateCar() throws CarNotFoundException {
        Car car = CarTestDataUtil.getDynamicCarData().get(0);

        when(carRepository.existsById(car.getCarId())).thenReturn(true);
        when(carRepository.save(car)).thenReturn(car);

        Car result = carService.updateCar(car);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(car.getCarNumber(), result.getCarNumber());
        Assertions.assertEquals(car.getMake(), result.getMake());
    }

    @Test
    public void testRemoveCar() throws CarNotFoundException {
        Long carId = 1L;

        when(carRepository.existsById(carId)).thenReturn(true);
        when(leaseRepository.existsById(carId)).thenReturn(false);

        carService.removeCar(carId);

        verify(carRepository).deleteById(carId);
    }

    @Test
    public void testRemoveCarThrowsExceptionIfCarNotFound() {
        when(carRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(CarNotFoundException.class, () -> carService.removeCar(1L));
    }

    @Test
    public void testRemoveCarThrowsExceptionIfCarHasLeases() {
        when(carRepository.existsById(1L)).thenReturn(true);
        when(leaseRepository.existsById(1L)).thenReturn(true);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> carService.removeCar(1L));
    }

    @Test
    public void testListCars() {
        List<Car> cars = CarTestDataUtil.getDynamicCarData().subList(0, 2);

        when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.listCars();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Toyota", result.get(0).getMake());
        Assertions.assertEquals("Civic", result.get(1).getModel());
    }

    @Test
    public void testFindCarById() throws CarNotFoundException {
        Car car = CarTestDataUtil.getDynamicCarData().get(0);

        when(carRepository.findById(car.getCarId())).thenReturn(Optional.of(car));

        Car result = carService.findCarById(car.getCarId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(car.getMake(), result.getMake());
        Assertions.assertEquals(car.getYear(), result.getYear());
    }

    @Test
    public void testFindCarByIdThrowsExceptionIfCarNotFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(CarNotFoundException.class, () -> carService.findCarById(1L));
    }

    @Test
    public void testFindCarByNumber() throws CarNotFoundException {
        Car car = CarTestDataUtil.getDynamicCarData().get(0);

        when(carRepository.findByCarNumber(car.getCarNumber())).thenReturn(Optional.of(car));

        Car result = carService.findCarByNumber(car.getCarNumber());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(car.getModel(), result.getModel());
        Assertions.assertTrue(result.isAvailable());
    }

    @Test
    public void testFindCarByNumberThrowsExceptionIfCarNotFound() {
        when(carRepository.findByCarNumber("AB123CD")).thenReturn(Optional.empty());

        Assertions.assertThrows(CarNotFoundException.class, () -> carService.findCarByNumber("AB123CD"));
    }

    @Test
    public void testFindCarsByAvailability() {
        List<Car> availableCars = CarTestDataUtil.getDynamicCarData().stream()
            .filter(Car::isAvailable)
            .toList();

        when(carRepository.findByAvailable(true)).thenReturn(availableCars);

        List<Car> result = carService.findCarsByAvailability(true);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(availableCars.size(), result.size());
        Assertions.assertTrue(result.stream().allMatch(Car::isAvailable));
    }
}
