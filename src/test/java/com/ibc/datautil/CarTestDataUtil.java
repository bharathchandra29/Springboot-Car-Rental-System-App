package com.ibc.datautil;

import com.ibc.entity.Car;
import java.util.Arrays;
import java.util.List;

public class CarTestDataUtil {

    public static List<Car> getDynamicCarData() {
        return Arrays.asList(
            new Car(1L, "AB123CD", "Toyota", "Camry", 2020, 50.00, true),
            new Car(2L, "XY456ZT", "Honda", "Civic", 2019, 40.00, true),
            new Car(3L, "LM789OP", "Ford", "Mustang", 2021, 70.00, true),
            new Car(4L, "QR012ST", "Chevrolet", "Impala", 2018, 45.00, false),
            new Car(5L, "UV345WX", "Nissan", "Altima", 2022, 55.00, true),
            new Car(6L, "GH678IJ", "BMW", "3 Series", 2020, 65.00, true),
            new Car(7L, "KL910MN", "Audi", "A4", 2021, 60.00, true),
            new Car(8L, "OP112QR", "Mercedes-Benz", "C-Class", 2019, 75.00, false),
            new Car(9L, "ST314UV", "Tesla", "Model 3", 2021, 80.00, true),
            new Car(10L, "WX516YZ", "Hyundai", "Elantra", 2020, 50.00, true)
        );
    }
}
