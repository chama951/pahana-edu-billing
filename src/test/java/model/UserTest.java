package model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.Privilege;
import com.pahana.edu.model.enums.UserRole;
import com.pahana.edu.utill.PasswordUtil;

public class UserTest {

	@Test
	public void testUserCreationWithAllFields() {
		LocalDateTime now = LocalDateTime.now();
		User user = new User("testuser", "password123", UserRole.ADMIN, true, now, now, now);

		assertEquals("testuser", user.getUsername());
		assertEquals("password123", user.getHashedPassword());
		assertEquals(UserRole.ADMIN, user.getRole());
		assertTrue(user.getIsActive());
		assertEquals(now, user.getCreatedAt());
	}

	@Test
	public void testDefaultConstructor() {
		User user = new User();
		assertNull(user.getUsername());
		assertNull(user.getHashedPassword());
		assertNull(user.getRole());
		assertNull(user.getIsActive());
	}

	@Test
	public void testVerifyPassword() {

		User user = new User();
		user.setHashedPassword(PasswordUtil.hashPassword("hashed_password_123"));
		assertNotNull(user.verifyPassword("test"));
	}

	@Test
	public void testHasPrivilege() {
		User user = new User();
		user.setRole(UserRole.ADMIN);

		assertTrue(user.hasPrivilege(Privilege.MANAGE_USERS));
	}

	@Test
	public void testSettersAndGetters() {
		User user = new User();
		user.setId(1L);
		user.setUsername("john");
		user.setRole(UserRole.CASHIER);
		user.setIsActive(false);

		assertEquals(1L, user.getId().longValue());
		assertEquals("john", user.getUsername());
		assertEquals(UserRole.CASHIER, user.getRole());
		assertFalse(user.getIsActive());
	}
}