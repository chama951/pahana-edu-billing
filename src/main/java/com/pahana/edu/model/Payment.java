package com.pahana.edu.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.pahana.edu.model.enums.PaymentMethod;

@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "billId")
	private Bill bill;

	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;

	private double amount;

	private LocalDateTime paymentDate;

	private String notes;

	private double discountAmount;

	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	@CreationTimestamp
	private LocalDateTime createdAt = LocalDateTime.now();

	@UpdateTimestamp
	private LocalDateTime updatedAt = LocalDateTime.now();

//	private Payment(Long id, Bill bill, double amount, LocalDateTime paymentDate, String notes,
//			PaymentMethod paymentMethod, LocalDateTime createdAt, LocalDateTime updatedAt) {
//		super();
//		this.id = id;
//		this.bill = bill;
//		this.amount = amount;
//		this.paymentDate = paymentDate;
//		this.notes = notes;
//		this.paymentMethod = paymentMethod;
//		this.createdAt = createdAt;
//		this.updatedAt = updatedAt;
//	}

	public Payment() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

}
