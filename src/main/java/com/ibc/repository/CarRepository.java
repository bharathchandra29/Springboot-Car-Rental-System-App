package com.ibc.repository;

import com.ibc.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByAvailable(boolean available);
    java.util.Optional<Car> findByCarNumber(String carNumber);
}
