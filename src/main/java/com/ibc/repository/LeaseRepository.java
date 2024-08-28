package com.ibc.repository;


import com.ibc.entity.Lease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findByIsActive(boolean isActive);
    List<Lease> findByCustomer_CustomerId(Long customerId);
}
