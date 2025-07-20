<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ page import="com.pahana.edu.model.enums.UserRole"%>

<!DOCTYPE html>
<html>
<head>
<title>User Registration</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="registration-container">
		<h1 class="registration-header">Register New User</h1>

		<form class="registration-form" action="user" method="post">
			<div class="form-group">
				<label class="form-label" for="username">Username:</label> <input
					class="form-control" type="text" id="username" name="username"
					required>
			</div>

			<div class="form-group">
				<label class="form-label" for="password">Password:</label> <input
					class="form-control" type="password" id="password" name="password"
					required>
			</div>

			<div class="form-group">
				<label class="form-label" for="confirmPassword">Confirm
					Password:</label> <input class="form-control" type="password"
					id="confirmPassword" required>
				<p id="passwordError" style="color: red; display: none;">Passwords
					don't match!</p>
			</div>

			<div class="form-group">
				<label class="form-label" for="role">Role:</label> <select
					class="form-control" id="role" name="role" required>
					<c:forEach items="<%=UserRole.values()%>" var="role">
						<option value="${role}">${role}</option>
					</c:forEach>
				</select>
			</div>

			<button type="submit" class="btn" onclick="return checkPasswords()">Register</button>

			<c:if test="${not empty error}">
				<p class="error-message">${error}</p>
			</c:if>
		</form>
	</div>

	<script>
		function checkPasswords() {
			var password = document.getElementById("password").value;
			var confirmPassword = document.getElementById("confirmPassword").value;
			var errorElement = document.getElementById("passwordError");

			if (password !== confirmPassword) {
				errorElement.style.display = "block";
				return false; // prevent form submission
			}
			return true; // allow form submission
		}
	</script>
</body>
</html>