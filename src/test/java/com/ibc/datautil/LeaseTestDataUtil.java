package com.ibc.datautil;

import com.ibc.entity.Car;
import com.ibc.entity.Customer;
import com.ibc.entity.Lease;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class LeaseTestDataUtil {

    // Method to return a list of Lease objects using provided Car and Customer data
    public static List<Lease> getDynamicLeaseData(List<Car> cars, List<Customer> customers) {
        return Arrays.asList(
            new Lease(cars.get(0), customers.get(0), LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 7)),
            new Lease(cars.get(1), customers.get(1), LocalDate.of(2024, 8, 10), LocalDate.of(2024, 8, 15)),
            new Lease(cars.get(2), customers.get(2), LocalDate.of(2024, 8, 5), LocalDate.of(2024, 8, 12)),
            new Lease(cars.get(3), customers.get(3), LocalDate.of(2024, 8, 15), LocalDate.of(2024, 8, 20))
        );
    }

    // Static method to return hardcoded Lease data
    public static List<Lease> getLeaseTestData() {
        // Create Car objects
        Car car1 = new Car();
        car1.setCarId(1L);
        car1.setPricePerDay(100.0);

        Car car2 = new Car();
        car2.setCarId(2L);
        car2.setPricePerDay(150.0);

        Car car3 = new Car();
        car3.setCarId(3L);
        car3.setPricePerDay(200.0);

        Car car4 = new Car();
        car4.setCarId(4L);
        car4.setPricePerDay(250.0);

        // Create Customer objects
        Customer customer1 = new Customer();
        customer1.setCustomerId(1L);

        Customer customer2 = new Customer();
        customer2.setCustomerId(2L);

        Customer customer3 = new Customer();
        customer3.setCustomerId(3L);

        Customer customer4 = new Customer();
        customer4.setCustomerId(4L);

        // Create Lease objects
        Lease lease1 = new Lease();
        lease1.setCar(car1);
        lease1.setCustomer(customer1);
        lease1.setLeaseStartDate(LocalDate.of(2024, 8, 1));
        lease1.setLeaseEndDate(LocalDate.of(2024, 8, 7));
        lease1.setActive(true);

        Lease lease2 = new Lease();
        lease2.setCar(car2);
        lease2.setCustomer(customer2);
        lease2.setLeaseStartDate(LocalDate.of(2024, 8, 10));
        lease2.setLeaseEndDate(LocalDate.of(2024, 8, 15));
        lease2.setActive(true);

        Lease lease3 = new Lease();
        lease3.setCar(car3);
        lease3.setCustomer(customer3);
        lease3.setLeaseStartDate(LocalDate.of(2024, 8, 5));
        lease3.setLeaseEndDate(LocalDate.of(2024, 8, 12));
        lease3.setActive(false);

        Lease lease4 = new Lease();
        lease4.setCar(car4);
        lease4.setCustomer(customer4);
        lease4.setLeaseStartDate(LocalDate.of(2024, 8, 15));
        lease4.setLeaseEndDate(LocalDate.of(2024, 8, 20));
        lease4.setActive(true);

        return Arrays.asList(lease1, lease2, lease3, lease4);
    }
}
