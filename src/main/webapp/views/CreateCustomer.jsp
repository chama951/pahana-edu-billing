<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
<title>Create Customer</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/CreateUser.css"
>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
>

</head>
<body>
	<div class="registration-container">
		<!-- Close button conditionally rendered -->
		<button class="close-btn"
			onclick="window.location.href='${pageContext.request.contextPath}/get-customers'"
		>
			<i class="fas fa-times"></i>
		</button>

		<h1 class="registration-header">New Customer</h1>

		<!-- Create Customer form -->
		<form class="registration-form"
			action="${pageContext.request.contextPath}/create-customer"
			method="post"
		>
			<%
			if (request.getAttribute("error") != null) {
			%>
			<div class="error-message">
				<%=request.getAttribute("error")%>
			</div>
			<%
			}
			%>

			<div class="form-group">
				<label class="form-label" for="accountNumber">Account No:</label> <input
					class="form-control" type="text" id=accountNumber
					name="accountNumber" required
				>
			</div>

			<div class="form-group">
				<label class="form-label" for="firstName">First Name:</label> <input
					class="form-control" type="text" id="firstName" name="firstName"
					required
				>
			</div>

			<div class="form-group">
				<label class="form-label" for="lastName">Last Name:</label> <input
					class="form-control" type="text" id="lastName" name="lastName"
				>
			</div>

			<div class="form-group">
				<label class="form-label" for="address">Address:</label> <input
					class="form-control" type="text" id="address" name="address"
				>
			</div>

			<div class="form-group">
				<label class="form-label" for="phoneNumber">Phone No:</label> <input
					class="form-control" type="text" id="phoneNumber"
					name="phoneNumber"
				>
			</div>

			<div class="form-group">
				<label class="form-label" for="email">Email:</label> <input
					class="form-control" type="text" id="email" name="email"
				>
			</div>

			<div class="form-group">
				<label class="form-label" for="unitsConsumed">Units
					Consumed:</label> <input class="form-control" type="text"
					id="unitsConsumed" name="unitsConsumed"
				>
			</div>

			<button type="submit" class="btn">Add Customer</button>
		</form>
	</div>

</body>
</html>