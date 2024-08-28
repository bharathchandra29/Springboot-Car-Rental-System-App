package com.ibc.controller;

import com.ibc.entity.Lease;
import com.ibc.exception.LeaseNotFoundException;
import com.ibc.service.LeaseService;
import com.ibc.util.ValidationUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leases")
@SecurityRequirement(name = "bharathapi")
public class LeaseController {

    @Autowired
    private LeaseService leaseService;

    // Create a new lease (active or inactive)
    @PostMapping("/addleases")
    public ResponseEntity<?> createLease(@RequestBody Lease lease) {
        // Validate customer ID and car ID
        if (lease.getCustomer() == null || !ValidationUtil.isValidCustomerId(lease.getCustomer().getCustomerId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid customer ID.");
        }

        if (lease.getCar() == null || !ValidationUtil.isValidCarId(lease.getCar().getCarId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid car ID.");
        }

        Lease createdLease = leaseService.createLease(lease);
        return new ResponseEntity<>(createdLease, HttpStatus.CREATED);
    }

    // Return a car and complete the lease
    @PutMapping("/{leaseId}/return")
    public ResponseEntity<?> returnCar(@PathVariable Long leaseId) {
        // Validate lease ID
        if (!ValidationUtil.isValidLeaseId(leaseId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid lease ID.");
        }

        try {
            Lease updatedLease = leaseService.returnCar(leaseId);
            return ResponseEntity.ok(updatedLease);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (LeaseNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // List all active leases
    @GetMapping("/active")
    public ResponseEntity<List<Lease>> listActiveLeases() {
        List<Lease> activeLeases = leaseService.listActiveLeases();
        return ResponseEntity.ok(activeLeases);
    }

    // List all inactive leases
    @GetMapping("/inactive")
    public ResponseEntity<List<Lease>> listInactiveLeases() {
        List<Lease> inactiveLeases = leaseService.listInactiveLeases();
        return ResponseEntity.ok(inactiveLeases);
    }

    // List all lease history
    @GetMapping("/history")
    public ResponseEntity<List<Lease>> listLeaseHistory() {
        List<Lease> leaseHistory = leaseService.listLeaseHistory();
        return ResponseEntity.ok(leaseHistory);
    }

    // List all leases by customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> listLeasesByCustomer(@PathVariable Long customerId) {
        // Validate customer ID
        if (!ValidationUtil.isValidCustomerId(customerId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid customer ID.");
        }

        List<Lease> leases = leaseService.listLeasesByCustomer(customerId);
        return ResponseEntity.ok(leases);
    }

    // Delete a lease by ID
    @DeleteMapping("/{leaseId}")
    public ResponseEntity<?> deleteLease(@PathVariable Long leaseId) {
        // Validate lease ID
        if (!ValidationUtil.isValidLeaseId(leaseId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid lease ID.");
        }

        try {
            leaseService.deleteLease(leaseId);
            return ResponseEntity.noContent().build();
        } catch (LeaseNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
