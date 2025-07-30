<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.pahana.edu.model.enums.UserRole"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html>
<head>
<title>User Management</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/DisplayUsers.css"
>
<!-- Font Awesome for icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
>
<style>
.search-container {
	display: flex;
	align-items: center;
	margin-left: auto;
}

.search-container input[type="text"] {
	padding: 8px 15px;
	border: 1px solid #ddd;
	border-radius: 4px;
	margin-right: 10px;
	width: 300px;
}

.search-container button {
	padding: 8px 15px;
	background-color: #4CAF50;
	color: white;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

.search-container button:hover {
	background-color: #45a049;
}

.header-row {
	display: flex;
	align-items: center;
	width: 100%;
	margin: 15px 0;
}

.header-actions {
	display: flex;
	align-items: center;
	gap: 10px;
}

/* Modal styles */
.modal {
	display: none;
	position: fixed;
	z-index: 1;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
	background-color: #fefefe;
	margin: 15% auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%;
	max-width: 500px;
	border-radius: 5px;
}

.close-btn {
	color: #aaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
	cursor: pointer;
}

.close-btn:hover {
	color: black;
}

/* Form styles */
.form-group {
	margin-bottom: 15px;
}

.form-label {
	display: block;
	margin-bottom: 5px;
	font-weight: bold;
}

.form-control {
	width: 100%;
	padding: 8px;
	border: 1px solid #ddd;
	border-radius: 4px;
	box-sizing: border-box;
}

.btn {
	background-color: #4CAF50;
	color: white;
	padding: 10px 15px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 16px;
}

.btn:hover {
	background-color: #45a049;
}

.error-message {
	color: red;
	font-size: 14px;
	margin-top: 5px;
}
</style>
</head>
<body>
	<div class="users-container">
		<!-- Page title at the very top -->
		<h1 class="users-header">User Management</h1>

		<!-- Navigation and search row below title -->
		<div class="header-row">
			<div class="header-actions">
				<a href="${pageContext.request.contextPath}/dashboard"
					class="back-button"
				> <i class="fas fa-arrow-left"></i> Dashboard
				</a> <a href="#" class="add-user-icon" title="Add New User"
					onclick="openAddModal()"
				> <i class="fas fa-plus"></i>
				</a>
			</div>

			<!-- Client-side search -->
			<div class="search-container">
				<input type="text" id="searchInput" placeholder="Search users...">
				<button onclick="searchUsers()">
					<i class="fas fa-search"></i> Search
				</button>
			</div>
		</div>

		<table class="users-table" id="usersTable">
			<thead>
				<tr>
					<th>ID</th>
					<th>Username</th>
					<th>Status</th>
					<th>Role</th>
					<th>Last Login</th>
					<th>Created At</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty usersList}">
						<tr>
							<td colspan="7" class="empty-state"><i
								class="fas fa-users-slash"
							></i> No users found</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${usersList}" var="user">
							<tr class="user-row">
								<td class="user-id">${user.id}</td>
								<td class="username">${user.username}</td>
								<td
									class="${user.isActive ? 'status-active' : 'status-inactive'}"
								>${user.isActive ? 'Active' : 'Inactive'}</td>
								<td class="role">${user.role}</td>
								<td class="last-login"><c:choose>
										<c:when test="${not empty user.lastLogin}">
                                            ${user.lastLogin.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm'))}
                                        </c:when>
										<c:otherwise>
                                            Never logged in
                                        </c:otherwise>
									</c:choose></td>
								<td class="created-at">
									${user.createdAt.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm'))}
								</td>
								<td>
									<div class="action-buttons">
										<a href="#" class="action-btn edit-btn"
											onclick="openEditModal('${user.id}', '${user.username}', ${user.isActive}, '${user.role}')"
										> <i class="fas fa-edit"></i> Edit
										</a>
										<form class="delete-form"
											action="${pageContext.request.contextPath}/delete-User"
											method="POST"
										>
											<input type="hidden" name="userId" value="${user.id}">
											<button type="submit" class="delete-btn">
												<i class="fas fa-trash"></i>
											</button>
										</form>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>

	<!-- Update User Modal -->
	<div id="updateUserModal" class="modal">
		<div class="modal-content">
			<span class="close-btn" onclick="closeModal('updateUserModal')">&times;</span>
			<h2 class="modal-header">Update User</h2>

			<!-- Uneditable Username Display -->
			<div>
				<p>
					Username : <span id="displayUsername"></span>
				</p>
			</div>

			<form id="updateUserForm" class="registration-form"
				action="${pageContext.request.contextPath}/update-user"
				method="POST"
			>
				<input type="hidden" id="id" name="id" value="">

				<div class="form-group">
					<label class="form-label" for="isActive">Status</label> <select
						class="form-control" id="isActive" name="isActive" required
					>
						<option value="true">Active</option>
						<option value="false">Inactive</option>
					</select>
				</div>

				<div class="form-group">
					<label class="form-label" for="role">Privileges</label> <select
						class="form-control" id="role" name="role" required
					>
						<c:forEach items="<%=UserRole.values()%>" var="role">
							<option value="${role}">${role}</option>
						</c:forEach>
					</select>
				</div>

				<button type="submit" class="btn">Update User</button>
			</form>
		</div>
	</div>

	<!-- Add User Modal -->
	<div id="addUserModal" class="modal">
		<div class="modal-content">
			<span class="close-btn" onclick="closeModal('addUserModal')">&times;</span>
			<h2 class="modal-header">Add New User</h2>

			<form id="addUserForm" class="registration-form"
				action="${pageContext.request.contextPath}/create-user"
				method="POST" onsubmit="return validatePassword()"
			>

				<div class="form-group">
					<label class="form-label" for="newUsername">Username</label> <input
						type="text" class="form-control" id="newUsername" name="username"
						required
					>
				</div>

				<div class="form-group">
					<label class="form-label" for="newPassword">Password</label> <input
						type="password" class="form-control" id="newPassword"
						name="password" required
					>
				</div>

				<div class="form-group">
					<label class="form-label" for="confirmPassword">Confirm
						Password</label> <input type="password" class="form-control"
						id="confirmPassword" name="confirmPassword" required
					>
					<div id="passwordError" class="error-message"
						style="display: none;"
					>Passwords do not match!</div>
				</div>

				<div class="form-group">
					<label class="form-label" for="newRole">Privileges</label> <select
						class="form-control" id="newRole" name="role" required
					>
						<c:forEach items="<%=UserRole.values()%>" var="role">
							<option value="${role}">${role}</option>
						</c:forEach>
					</select>
				</div>

				<button type="submit" class="btn">Create User</button>
			</form>
		</div>
	</div>

	<script>
    // Search function
    function searchUsers() {
        const input = document.getElementById('searchInput');
        const filter = input.value.toUpperCase();
        const table = document.getElementById('usersTable');
        const rows = table.getElementsByClassName('user-row');
        
        for (let i = 0; i < rows.length; i++) {
            const id = rows[i].getElementsByClassName('user-id')[0].textContent;
            const username = rows[i].getElementsByClassName('username')[0].textContent;
            const role = rows[i].getElementsByClassName('role')[0].textContent;
            
            if (id.toUpperCase().includes(filter) || 
                username.toUpperCase().includes(filter) ||
                role.toUpperCase().includes(filter)) {
                rows[i].style.display = "";
            } else {
                rows[i].style.display = "none";
            }
        }
    }
    
    // Search on Enter key
    document.getElementById('searchInput').addEventListener('keyup', function(event) {
        if (event.key === 'Enter') {
            searchUsers();
        }
    });

    // Edit Modal functions
    function openEditModal(id, username, isActive, role) {
        // Set form values
        document.getElementById('id').value = id;
        document.getElementById('displayUsername').textContent = username;
        document.getElementById('isActive').value = isActive;
        
        // Select the current role
        const roleSelect = document.getElementById('role');
        Array.from(roleSelect.options).forEach(option => {
            if (option.text === role) {
                option.selected = true;
            }
        });
        
        // Show modal
        document.getElementById('updateUserModal').style.display = 'block';
        return false; // Prevent default anchor behavior
    }

    // Add Modal functions
    function openAddModal() {
        // Reset form
        document.getElementById('addUserForm').reset();
        document.getElementById('passwordError').style.display = 'none';
        
        // Show modal
        document.getElementById('addUserModal').style.display = 'block';
        return false; // Prevent default anchor behavior
    }

    // Close modal function
    function closeModal(modalId) {
        document.getElementById(modalId).style.display = 'none';
    }

    // Password validation
    function validatePassword() {
        const password = document.getElementById('newPassword').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const errorElement = document.getElementById('passwordError');
        
        if (password !== confirmPassword) {
            errorElement.style.display = 'block';
            return false;
        }
        errorElement.style.display = 'none';
        return true;
    }

    // Close modal when clicking outside
    window.addEventListener('click', function(e) {
        if (e.target.classList.contains('modal')) {
            e.target.style.display = 'none';
        }
    });
    </script>
</body>
</html>