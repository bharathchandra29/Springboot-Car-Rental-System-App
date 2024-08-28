package com.ibc.datautil;

import com.ibc.entity.Lease;
import com.ibc.entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PaymentTestDataUtil {

    public static List<Payment> getSamplePayments() {
        // Create sample leases
        Lease lease1 = new Lease();
        lease1.setLeaseId(1L);

        Lease lease2 = new Lease();
        lease2.setLeaseId(2L);

        // Create sample payments
        Payment payment1 = new Payment();
        payment1.setPaymentId(1L);
        payment1.setLease(lease1);
        payment1.setAmount(new BigDecimal("350.00"));
        payment1.setPaymentDate(LocalDate.of(2024, 8, 1));
        payment1.setPaymentMethod("Credit Card");

        Payment payment2 = new Payment();
        payment2.setPaymentId(2L);
        payment2.setLease(lease1);
        payment2.setAmount(new BigDecimal("200.00"));
        payment2.setPaymentDate(LocalDate.of(2024, 8, 10));
        payment2.setPaymentMethod("Cash");

        Payment payment3 = new Payment();
        payment3.setPaymentId(3L);
        payment3.setLease(lease2);
        payment3.setAmount(new BigDecimal("490.00"));
        payment3.setPaymentDate(LocalDate.of(2024, 8, 5));
        payment3.setPaymentMethod("Credit Card");

        Payment payment4 = new Payment();
        payment4.setPaymentId(4L);
        payment4.setLease(lease2);
        payment4.setAmount(new BigDecimal("225.00"));
        payment4.setPaymentDate(LocalDate.of(2024, 8, 15));
        payment4.setPaymentMethod("Debit Card");

        return Arrays.asList(payment1, payment2, payment3, payment4);
    }
}
