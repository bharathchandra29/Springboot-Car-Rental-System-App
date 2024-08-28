package com.ibc;

import com.ibc.datautil.PaymentTestDataUtil;
import com.ibc.entity.Lease;
import com.ibc.entity.Payment;
import com.ibc.exception.PaymentNotFoundException;
import com.ibc.repository.PaymentRepository;
import com.ibc.service.LeaseService;
import com.ibc.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private LeaseService leaseService;

    @InjectMocks
    private PaymentService paymentService;

    @Autowired
    public PaymentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRecordPayment_Success() {
        Long leaseId = 1L;
        BigDecimal amount = new BigDecimal("350.00");
        String paymentMethod = "Credit Card";
        
        Lease lease = new Lease();
        lease.setLeaseId(leaseId);

        when(leaseService.findLeaseById(leaseId)).thenReturn(lease);

        Payment payment = new Payment();
        payment.setLease(lease);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMethod(paymentMethod);

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.recordPayment(leaseId, amount, paymentMethod);

        assertNotNull(result);
        assertEquals(amount, result.getAmount());
        assertEquals(paymentMethod, result.getPaymentMethod());
        assertEquals(lease, result.getLease());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    public void testRecordPayment_InvalidDetails() {
        // Test case 1: leaseId is null
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.recordPayment(null, new BigDecimal("350.00"), "Credit Card");
        });

        // Test case 2: amount is negative
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.recordPayment(1L, new BigDecimal("-10.00"), "Credit Card");
        });

        // Test case 3: paymentMethod is empty
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.recordPayment(1L, new BigDecimal("350.00"), "");
        });
    }


    @Test
    public void testGetPaymentHistory() {
        final Long leaseId = 1L; // Declare leaseId as final

        List<Payment> payments = PaymentTestDataUtil.getSamplePayments().stream()
            .filter(payment -> payment.getLease().getLeaseId().equals(leaseId))
            .collect(Collectors.toList());

        when(paymentRepository.findByLease_LeaseId(leaseId)).thenReturn(payments);

        List<Payment> result = paymentService.getPaymentHistory(leaseId);

        assertNotNull(result);
        assertEquals(payments.size(), result.size());
        assertTrue(result.stream().allMatch(payment -> payment.getLease().getLeaseId().equals(leaseId)));
    }

    @Test
    public void testGetPaymentHistory_NoPayments() {
        final Long leaseId = 1L; // Declare leaseId as final

        when(paymentRepository.findByLease_LeaseId(leaseId)).thenReturn(Arrays.asList());

        assertThrows(PaymentNotFoundException.class, () -> {
            paymentService.getPaymentHistory(leaseId);
        });

        verify(paymentRepository, times(1)).findByLease_LeaseId(leaseId);
    }
}
