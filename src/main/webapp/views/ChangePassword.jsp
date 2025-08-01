<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
<title>Change Password</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/ChangeUsername.css">
</head>
<body>
	<div class="container">
		<button class="close-btn"
			onclick="window.location.href='${pageContext.request.contextPath}/views/Dashboard.jsp'">×</button>
		<h2>Change Password</h2>

		<%-- Error message positioned below title --%>
		<%
		if (request.getAttribute("error") != null) {
		%>
		<div class="error-message" style="margin-bottom: 20px;">
			<%=request.getAttribute("error")%>
		</div>
		<%
		}
		%>

		<form id="passwordForm"
			action="${pageContext.request.contextPath}/user/change-password"
			method="post">

			<div class="form-group" style="margin-top: 0;">
				<label class="form-label" for="currentPassword">Current
					Password:</label> <input class="form-control" type="password"
					id="currentPassword" name="currentPassword" required>
			</div>

			<div class="form-group">
				<label class="form-label" for="newPassword">New Password:</label> <input
					class="form-control" type="password" id="newPassword"
					name="newPassword" required>
			</div>

			<div class="form-group">
				<label class="form-label" for="confirmPassword">Confirm New
					Password:</label> <input class="form-control" type="password"
					id="confirmPassword" name="confirmPassword" required>
				<p id="passwordError"
					style="color: red; display: none; margin-top: 5px;">Passwords
					don't match!</p>
			</div>

			<button type="submit" class="btn" onclick="return checkPasswords()">Update
				Password</button>
		</form>
	</div>

	<script>
		function checkPasswords() {
			var newPassword = document.getElementById("newPassword").value;
			var confirmPassword = document.getElementById("confirmPassword").value;
			var errorElement = document.getElementById("passwordError");

			if (newPassword !== confirmPassword) {
				errorElement.style.display = "block";
				return false;
			}
			errorElement.style.display = "none";
			return true;
		}

		// Real-time validation
		document.getElementById("newPassword").addEventListener("input",
				checkPasswords);
		document.getElementById("confirmPassword").addEventListener("input",
				checkPasswords);
	</script>
</body>
</html>