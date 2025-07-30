<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Admin Dashboard</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/Dashboard.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
</head>
<body>
	<div class="dashboard-container">
		<!-- Main Content Area -->
		<div class="main-content">
			<!-- Top Navigation Bar -->
			<div class="topnav">
				<div class="greeting">
					Hi, <strong>${loggedInUsername}</strong> <span
						class="user-role-badge">${userRole}</span>
				</div>

				<div class="user-menu-container">
					<button class="user-menu-btn" id="userMenuBtn">
						<div class="user-avatar">
							<i class="fas fa-user-circle"></i>
						</div>
						<span>${loggedInUsername}</span> <i
							class="fas fa-chevron-down dropdown-icon"></i>
					</button>

					<div id="userDropdown" class="user-dropdown-menu">
						<div class="user-info">
							<div class="user-avatar-large">
								<i class="fas fa-user-circle"></i>
							</div>
							<div class="user-details">
								<div class="user-name">${loggedInUsername}</div>
								<div class="user-role">${userRole}</div>
							</div>
						</div>

						<div class="dropdown-divider"></div>

						<!-- Change Username Button -->
						<button class="dropdown-item"
							onclick="window.location.href='${pageContext.request.contextPath}/change-username'">
							<i class="fas fa-user-edit dropdown-icon"></i> <span>Change
								Username</span>
						</button>

						<!-- Change Password Button -->
						<button
							class="dropdown-item"
							onclick="window.location.href='${pageContext.request.contextPath}/change-password'"
						>
							<i class="fas fa-key dropdown-icon"></i> <span>Change
								Password</span>
						</button>
						<div class="dropdown-divider"></div>

						<!-- Logout Button -->
						<a href="${pageContext.request.contextPath}/logout-user"
							class="dropdown-item logout-item"> <i
							class="fas fa-sign-out-alt dropdown-icon"></i> <span>Logout</span>
						</a>
					</div>
				</div>
			</div>

			<!-- Dynamic Content Area with Grid Buttons -->
			<div class="content-area">
				<div class="content-header">
					<h2>Admin Dashboard</h2>
				</div>

				<div class="dashboard-grid">
					<!-- User Management Card -->
					<a href="${pageContext.request.contextPath}/manage-users"
						class="dashboard-card">
						<div class="card-icon">
							<i class="fa-solid fa-user-check"></i>
						</div>
						<div class="card-title">Users</div>
					</a>

					<!-- Customer Management Card -->
					<a href="${pageContext.request.contextPath}/manage-customers"
						class="dashboard-card">
						<div class="card-icon">
							<i class="fa-solid fa-handshake"></i>
						</div>
						<div class="card-title">Customers</div>
					</a>

					<!-- Items Management Card -->
					<a href="${pageContext.request.contextPath}/manage-items"
						class="dashboard-card">
						<div class="card-icon">
							<i class="fa-solid fa-book-open"></i>
						</div>
						<div class="card-title">Items</div>
					</a>

					<!-- Cashier Card -->
					<a href="${pageContext.request.contextPath}/bill-print"
						class="dashboard-card">
						<div class="card-icon">
							<i class="fa-solid fa-cash-register"></i>
						</div>
						<div class="card-title">Cashier</div>
					</a>

					<!-- Invoices Card -->
					<a href="${pageContext.request.contextPath}/invoice-history"
						class="dashboard-card">
						<div class="card-icon">
							<i class="fa-solid fa-receipt"></i>
						</div>
						<div class="card-title">Invoice</div>
					</a>

					<!-- Software Info -->
					<a href="${pageContext.request.contextPath}/SoftwareInfo.jsp"
						class="dashboard-card">
						<div class="card-icon">
							<i class="fa-solid fa-circle-info"></i>
						</div>
						<div class="card-title">Info</div>
					</a>
				</div>
			</div>
		</div>
	</div>

	<script>
		document.addEventListener('DOMContentLoaded', function() {
			// User menu toggle
			document.getElementById('userMenuBtn').addEventListener(
					'click',
					function(e) {
						e.stopPropagation();
						document.getElementById('userDropdown').classList
								.toggle('show');
					});

			// Close dropdown when clicking outside
			document.addEventListener('click', function(e) {
				if (!e.target.closest('.user-menu-container')) {
					document.getElementById('userDropdown').classList
							.remove('show');
				}
			});
		});
	</script>
</body>
</html>