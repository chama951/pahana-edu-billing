package com.pahana.edu.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import static jakarta.persistence.CascadeType.ALL;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double totalAmount;

	private Double discountAmount;

	private Double netAmount;

	@CreationTimestamp
	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@OneToMany(mappedBy = "bill", cascade = ALL, orphanRemoval = true)
	private List<BillItem> billItems;

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

	public Double getTotalAmount() {
		return totalAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
