package com.pahana.edu.model;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import static jakarta.persistence.CascadeType.ALL;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime billDate;

	private BigDecimal totalAmount;

	private BigDecimal discountAmount;

	@CreationTimestamp
	private LocalDateTime createdAt = LocalDateTime.now();

	@UpdateTimestamp
	private LocalDateTime updatedAt = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;

	@OneToMany(mappedBy = "bill", cascade = ALL, orphanRemoval = true)
	private List<BillItem> billItems;

	@OneToOne(mappedBy = "bill", cascade = ALL, orphanRemoval = true)
	private Payment payment;

//	private Bill(Long id, LocalDateTime billDate, BigDecimal totalAmount,
//			BigDecimal discountAmount, LocalDateTime createdAt, LocalDateTime updatedAt, Customer customer,
//			List<BillItem> billItems,
//			Payment payment) {
//		super();
//		this.id = id;
//		this.billDate = billDate;
//		this.totalAmount = totalAmount;
//		this.discountAmount = discountAmount;
//		this.createdAt = createdAt;
//		this.updatedAt = updatedAt;
//		this.customer = customer;
//		this.billItems = billItems;
//		this.payment = payment;
//	}

	public Bill() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getBillDate() {
		return billDate;
	}

	public void setBillDate(LocalDateTime billDate) {
		this.billDate = billDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<BillItem> getBillItems() {
		return billItems;
	}

	public void setBillItems(List<BillItem> billItems) {
		this.billItems = billItems;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
