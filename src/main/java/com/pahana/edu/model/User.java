package com.pahana.edu.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.pahana.edu.model.enums.Privilege;
import com.pahana.edu.model.enums.UserRole;
import com.pahana.edu.utill.PasswordUtil;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;

	private String hashedPassword; // Consider using @Transient for plain password if needed

	@Enumerated(EnumType.STRING)
	private UserRole role;

	private Boolean isActive = true;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	private LocalDateTime lastLogin;

	public User(String username, String plainPassword, UserRole role, Boolean isActive, LocalDateTime createdAt,
			LocalDateTime updatedAt, LocalDateTime lastLogin) {
		super();
		this.username = username;
		this.hashedPassword = plainPassword;
		this.role = role;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.lastLogin = lastLogin;
	}

	public User(String username, String plainPassword, UserRole role, Boolean isActive, LocalDateTime createdAt) {
		super();
		this.username = username;
		this.hashedPassword = plainPassword;
		this.role = role;
		this.isActive = isActive;
		this.createdAt = createdAt;
	}

	public User() {
	}

	public User(UserRole userRole, boolean isActive) {
		this.role = userRole;
		this.isActive = isActive;
	}

	public User(Long id, UserRole role, Boolean isActive) {
		super();
		this.id = id;
		this.role = role;
		this.isActive = isActive;
	}

	public User(Long id, String newUsername) {
		this.id = id;
		this.username = newUsername;
	}

	public boolean verifyPassword(String plainPassword) {
		return PasswordUtil.checkPassword(plainPassword, this.hashedPassword);
	}

	public boolean hasPrivilege(Privilege privilege) {
		return role.hasPrivilege(privilege);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}
}