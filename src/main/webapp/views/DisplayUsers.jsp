<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.pahana.edu.model.enums.UserRole"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html>
<head>
<title>User Management</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/DisplayUsers.css">
<!-- Font Awesome for icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<style>
</style>
</head>
<body>
	<div class="users-container">
		<div class="page-header">
			<div style="display: flex; align-items: center;">
				<a href="${pageContext.request.contextPath}/dashboard"
					class="back-button"> <i class="fas fa-arrow-left"></i>
					Dashboard
				</a><a href="${pageContext.request.contextPath}/create-user"
					class="add-user-icon" title="Add New User"> <i
					class="fas fa-plus"></i>
				</a>
			</div>
			<h1 class="users-header">User Management</h1>
		</div>

		<table class="users-table">
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
								class="fas fa-users-slash"></i> No users found</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${usersList}" var="user">
							<tr>
								<td>${user.id}</td>
								<td>${user.username}</td>
								<td
									class="${user.isActive ? 'status-active' : 'status-inactive'}">
									${user.isActive ? 'Active' : 'Inactive'}</td>
								<td>${user.role}</td>
								<td><c:choose>
										<c:when test="${not empty user.lastLogin}">
                                            ${user.lastLogin.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm'))}
                                        </c:when>
										<c:otherwise>
                                            Never logged in
                                        </c:otherwise>
									</c:choose></td>
								<td>
									${user.createdAt.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm'))}
								</td>
								<td>
									<div class="action-buttons">
										<a href="editUser?id=${user.id}" class="action-btn edit-btn">
											<i class="fas fa-edit"></i> Edit
										</a>
										<form class="delete-form"
											action="${pageContext.request.contextPath}/delete-User-Servlet"
											method="POST">
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
			<button class="close-btn">&times;</button>
			<h2 class="modal-header">Update User</h2>

			<!-- Uneditable Username Display -->
			<div>
				<h3>
					Username : <span id="displayUsername"></span>
				</h3>
			</div>

			<form id="updateUserForm" class="registration-form"
				action="${pageContext.request.contextPath}/update-user"
				method="POST">
				<input type="hidden" id="id" name="id" value="">

				<div class="form-group">
					<label class="form-label" for="isActive">Status</label> <select
						class="form-control" id="isActive" name="isActive" required>
						<option value="true">Active</option>
						<option value="false">Inactive</option>
					</select>
				</div>

				<div class="form-group">
					<label class="form-label" for="role">Role</label> <select
						class="form-control" id="role" name="role" required>
						<c:forEach items="<%=UserRole.values()%>" var="role">
							<option value="${role}">${role}</option>
						</c:forEach>
					</select>
				</div>

				<button type="submit" class="btn">Update User</button>
			</form>
		</div>
	</div>

	<script>
    document.addEventListener('DOMContentLoaded', function() {
        document.querySelectorAll('.edit-btn').forEach(btn => {
            btn.addEventListener('click', function(e) {
                e.preventDefault();
                const row = this.closest('tr');
                const id = row.cells[0].textContent;
                const username = row.cells[1].textContent;
                const isActive = row.cells[2].classList.contains('status-active');
                const currentRole = row.cells[3].textContent.trim();
                
                // Set form values
                document.getElementById('id').value = id;
                document.getElementById('displayUsername').textContent = username;
                document.getElementById('isActive').value = isActive;
                
                // Select the current role
                const roleSelect = document.getElementById('role');
                Array.from(roleSelect.options).forEach(option => {
                    if (option.text === currentRole) {
                        option.selected = true;
                    }
                });
                
                // Show modal
                document.getElementById('updateUserModal').style.display = 'block';
            });
        });

        // Close modal handlers
        document.querySelector('.close-btn').addEventListener('click', function() {
            document.getElementById('updateUserModal').style.display = 'none';
        });

        window.addEventListener('click', function(e) {
            if (e.target === document.getElementById('updateUserModal')) {
                document.getElementById('updateUserModal').style.display = 'none';
            }
        });
    });
    </script>
</body>
</html>