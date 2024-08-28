package com.ibc.datautil;

import com.ibc.entity.Customer;
import java.util.Arrays;
import java.util.List;

public class CustomerTestDataUtil {

    public static List<Customer> getDynamicCustomerData() {
        return Arrays.asList(
            new Customer(1L, "Bharath", "Chandra", "bharathchandra.ila@gmail.com", "9160348766", "224 Lelle Vari St, Chirala"),
            new Customer(2L, "Sunil", "Kumar", "sunilkumar@gmail.com", "9836567563", "456 Ramnager St, Ongole"),
            new Customer(3L, "Murali", "Ram", "muraliram@gmail.com", "8965536956", "789 Houseing Board St, Chirala"),
            new Customer(4L, "Siva", "Shanker", "sivashanker@gmail.com", "9765854266", "101 Sivalayam St, Chirala")
        );
    }
}
