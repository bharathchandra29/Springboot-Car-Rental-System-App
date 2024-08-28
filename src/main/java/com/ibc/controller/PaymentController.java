package com.ibc.controller;

import com.ibc.entity.Payment;
import com.ibc.exception.PaymentNotFoundException;
import com.ibc.service.PaymentService;
import com.ibc.util.ValidationUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/payments")
@SecurityRequirement(name = "bharathapi")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    //http://localhost:8080/payments/record
    @PostMapping("/record")
    public ResponseEntity<?> recordPayment(@RequestParam Long leaseId,
                                            @RequestParam BigDecimal amount,
                                            @RequestParam String paymentMethod) {
        try {
            // Validate input parameters
            if (!ValidationUtil.isValidLeaseId(leaseId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid lease ID.");
            }

            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid payment amount.");
            }

            if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Payment method cannot be empty.");
            }

            // Record payment
            Payment payment = paymentService.recordPayment(leaseId, amount, paymentMethod);
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    //http://localhost:8080/payments/history/{leaseId}
    @GetMapping("/history/{leaseId}")
    public ResponseEntity<?> getPaymentHistory(@PathVariable Long leaseId) {
        try {
            // Validate lease ID
            if (!ValidationUtil.isValidLeaseId(leaseId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid lease ID.");
            }

            List<Payment> payments = paymentService.getPaymentHistory(leaseId);
            return ResponseEntity.ok(payments);
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
