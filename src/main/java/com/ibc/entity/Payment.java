package com.ibc.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "lease_id", nullable = false)
    private Lease lease;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false)
    private String paymentMethod; // e.g., Credit Card, Cash, etc.

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Lease getLease() {
		return lease;
	}

	public void setLease(Lease lease) {
		this.lease = lease;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Payment(Long paymentId, Lease lease, BigDecimal amount, LocalDate paymentDate, String paymentMethod) {
		super();
		this.paymentId = paymentId;
		this.lease = lease;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.paymentMethod = paymentMethod;
	}

	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", lease=" + lease + ", amount=" + amount + ", paymentDate="
				+ paymentDate + ", paymentMethod=" + paymentMethod + "]";
	}
}
