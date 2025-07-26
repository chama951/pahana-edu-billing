<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Change Username</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/ChangeUsername.css">
</head>
<body>
	<div class="container">
		<button class="close-btn"
			onclick="window.location.href='${pageContext.request.contextPath}/dashboard'">×</button>
		<h2>Change Username</h2>

		<%-- Display error message --%>
		<div class="error-message">${errorMessage}</div>

		<form id="usernameForm"
			action="${pageContext.request.contextPath}/change-username"
			method="POST">
			<div class="form-group">
				<label for="currentUsername">Current Username</label> <input
					type="text" id="currentUsername" name="currentUsername"
					value="${currentUsername}" readonly>
			</div>

			<div class="form-group">
				<label for="newUsername">New Username</label> <input type="text"
					id="newUsername" name="newUsername" required>
				<p id="usernameError" style="color: red; display: none;">New
					username cannot be the same as current username</p>
			</div>

			<div class="form-group">
				<button type="submit" class="btn">Update Username</button>
			</div>
		</form>
	</div>

	<script>
		document
				.getElementById('usernameForm')
				.addEventListener(
						'submit',
						function(e) {
							const currentUsername = document
									.getElementById('currentUsername').value
									.trim();
							const newUsername = document
									.getElementById('newUsername').value.trim();
							const errorElement = document
									.getElementById('usernameError');

							// Reset error display
							errorElement.style.display = 'none';

							if (!newUsername) {
								e.preventDefault();
								alert('Please enter a new username');
								return false;
							}

							if (newUsername === currentUsername) {
								e.preventDefault();
								errorElement.style.display = 'block';
								return false;
							}

							return true;
						});

		// Real-time validation as user types
		document.getElementById('newUsername').addEventListener(
				'input',
				function() {
					const currentUsername = document
							.getElementById('currentUsername').value.trim();
					const newUsername = this.value.trim();
					const errorElement = document
							.getElementById('usernameError');

					if (newUsername === currentUsername) {
						errorElement.style.display = 'block';
					} else {
						errorElement.style.display = 'none';
					}
				});
	</script>
</body>
</html>