package com.ibc.service;

import com.ibc.entity.Lease;
import com.ibc.entity.Payment;
import com.ibc.exception.PaymentNotFoundException;
import com.ibc.repository.PaymentRepository;
import com.ibc.util.DateUtil;
import com.ibc.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private LeaseService leaseService;

    public Payment recordPayment(Long leaseId, BigDecimal amount, String paymentMethod) {
        // Validate input values
        if (leaseId == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 || !ValidationUtil.isNotEmpty(paymentMethod)) {
            throw new IllegalArgumentException("Invalid payment details.");
        }

        // Fetch the Lease from LeaseService
        Lease lease = leaseService.findLeaseById(leaseId);

        Payment payment = new Payment();
        payment.setLease(lease);
        payment.setAmount(amount);
        payment.setPaymentDate(DateUtil.getCurrentDate()); // Use DateUtil for current date
        payment.setPaymentMethod(paymentMethod);
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentHistory(Long leaseId) {
        if (leaseId == null) {
            throw new IllegalArgumentException("Lease ID cannot be null.");
        }

        List<Payment> payments = paymentRepository.findByLease_LeaseId(leaseId);
        if (payments == null || payments.isEmpty()) {
            throw new PaymentNotFoundException("No payments found for lease ID: " + leaseId);
        }
        return payments;
    }
}
