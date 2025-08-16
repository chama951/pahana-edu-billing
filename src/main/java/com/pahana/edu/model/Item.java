package com.pahana.edu.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String title;

	@Column(unique = true)
	private String isbn;

	private double price;

	private double discountPercentage;

	private double discountAmount;

	private Integer quantityInStock;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@CreationTimestamp
	private LocalDateTime createdAt = LocalDateTime.now();

	@UpdateTimestamp
	private LocalDateTime updatedAt = LocalDateTime.now();

	private String description;

	private String author;

	private Integer publicationYear;

	private String publisher;

	public Item(String title, String isbn, double price, Integer quantityInStock, User user, String description,
			String author, Integer publicationYear, String publisher, double discountPercentage) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.price = price;
		this.quantityInStock = quantityInStock;
		this.user = user;
		this.description = description;
		this.author = author;
		this.publicationYear = publicationYear;
		this.publisher = publisher;
		this.discountPercentage = discountPercentage;
	}

	public Item(Long id, String title, String isbn, double price, Integer quantityInStock, User user,
			String description,
			String author, Integer publicationYear, String publisher, double discountPercentage) {
		super();
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.price = price;
		this.quantityInStock = quantityInStock;
		this.user = user;
		this.description = description;
		this.author = author;
		this.publicationYear = publicationYear;
		this.publisher = publisher;
		this.discountPercentage = discountPercentage;
	}

	public Item() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(Integer quantityInStock) {
		this.quantityInStock = quantityInStock;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

}
