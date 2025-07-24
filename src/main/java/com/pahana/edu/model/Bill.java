package com.pahana.edu.model;

import java.math.BigDecimal;

import java.sql.Date;

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

	private Date billDate;

	private BigDecimal totalAmount;

	private Date dueDate;

	private BigDecimal taxAmount;

	private BigDecimal discountAmount;

	@CreationTimestamp
	private Date createdAt;

	@UpdateTimestamp
	private Date updatedAt;

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	@OneToMany(mappedBy = "bill", cascade = ALL, orphanRemoval = true)
	private List<BillItem> billItems;

	@OneToOne(mappedBy = "bill", cascade = ALL, orphanRemoval = true)
	private Payment payment;

	private Bill(Long id, Date billDate, BigDecimal totalAmount, Date dueDate, BigDecimal taxAmount,
			BigDecimal discountAmount, Date createdAt, Date updatedAt, Customer customer, List<BillItem> billItems,
			Payment payment) {
		super();
		this.id = id;
		this.billDate = billDate;
		this.totalAmount = totalAmount;
		this.dueDate = dueDate;
		this.taxAmount = taxAmount;
		this.discountAmount = discountAmount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.customer = customer;
		this.billItems = billItems;
		this.payment = payment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
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
