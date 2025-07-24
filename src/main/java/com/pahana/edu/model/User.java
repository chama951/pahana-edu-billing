package com.pahana.edu.model;

import java.sql.Date;

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

	private String username;

	private String hashedPassword; // Consider using @Transient for plain password if needed

	@Enumerated(EnumType.STRING)
	private UserRole role;

	private Boolean isActive = true;

	@CreationTimestamp
	private Date createdAt;

	@UpdateTimestamp
	private Date updatedAt;

	private Date lastLogin;

	public User(String username, String plainPassword, UserRole role, Boolean isActive, Date currentDate,
			Date currentDate2, Date currentDate3) {
		super();
		this.username = username;
		this.hashedPassword = PasswordUtil.hashPassword(plainPassword);
		this.role = role;
		this.isActive = isActive;
		this.createdAt = currentDate;
		this.updatedAt = currentDate2;
		this.lastLogin = currentDate3;
	}

	public User() {
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

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

}