package com.pahana.edu.model;

import javax.persistence.*;

import com.pahana.edu.model.enums.Privilege;
import com.pahana.edu.model.enums.UserRole;
import com.pahana.edu.utill.PasswordUtil;

@Entity
@Table(name = "users")
public class User {

	@Id
    @Column(columnDefinition = "INT AUTO_INCREMENT")
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String hashedPassword;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;

	// Constructors
	public User(int id, String username, String hashedPassword, UserRole userRole) {
	}

	public User(String username, String plainPassword, UserRole role) {
		this.username = username;
		this.hashedPassword = PasswordUtil.hashPassword(plainPassword);
		this.role = role;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	// Verify password
    public boolean verifyPassword(String plainPassword) {
        return PasswordUtil.checkPassword(plainPassword, this.hashedPassword);
    }

	// Privilege check
	public boolean hasPrivilege(Privilege privilege) {
		return role.hasPrivilege(privilege);
	}

	// Getters and setters
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
}