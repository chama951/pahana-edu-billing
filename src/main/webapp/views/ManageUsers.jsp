<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.pahana.edu.model.enums.UserRole"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html>
<head>
<title>User Management</title>
<link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: #f5f5f5;
    }
    
    .users-container {
        background-color: white;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        padding: 20px;
        max-width: 1200px;
        margin: 0 auto;
    }
    
    h1 {
        color: #333;
        margin-bottom: 20px;
        text-align: center;
    }
    
    .header-row {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
    }
    
    .header-actions {
        display: flex;
        align-items: center;
        gap: 10px;
    }
    
    .search-container {
        display: flex;
        align-items: center;
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
    
    .back-button {
        display: inline-flex;
        align-items: center;
        padding: 8px 12px;
        background-color: #6c757d;
        color: white;
        border-radius: 4px;
        text-decoration: none;
        gap: 5px;
    }
    
    .back-button:hover {
        background-color: #5a6268;
    }
    
    .add-user-btn {
        display: inline-flex;
        align-items: center;
        padding: 8px 12px;
        background-color: #17a2b8;
        color: white;
        border-radius: 4px;
        border: none;
        cursor: pointer;
        gap: 5px;
    }
    
    .add-user-btn:hover {
        background-color: #138496;
    }
    
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }
    
    th, td {
        padding: 12px 15px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }
    
    th {
        background-color: #f8f9fa;
        font-weight: bold;
    }
    
    tr:hover {
        background-color: #f5f5f5;
    }
    
    .empty-state {
        text-align: center;
        padding: 20px;
        color: #666;
    }
    
    .status-active {
        color: #28a745;
        font-weight: bold;
    }
    
    .status-inactive {
        color: #dc3545;
        font-weight: bold;
    }
    
    .action-buttons {
        display: flex;
        align-items: center;
        gap: 5px;
    }
    
    .edit-btn {
        padding: 6px 12px;
        background-color: #ffc107;
        color: #333;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        display: inline-flex;
        align-items: center;
        gap: 5px;
    }
    
    .edit-btn:hover {
        background-color: #e0a800;
    }
    
    .delete-btn {
        padding: 6px 12px;
        background-color: #dc3545;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        display: inline-flex;
        align-items: center;
        gap: 5px;
    }
    
    .delete-btn:hover {
        background-color: #c82333;
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
        margin: 10% auto;
        padding: 30px;
        border: 1px solid #888;
        width: 80%;
        max-width: 500px;
        border-radius: 8px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
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
    
    .modal-header {
        margin-top: 0;
        color: #333;
    }
    
    .form-group {
        margin-bottom: 15px;
    }
    
    .form-label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
        color: #555;
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
        <h1>User Management</h1>
        
        <div class="header-row">
            <div class="header-actions">
                <a href="${pageContext.request.contextPath}/views/Dashboard.jsp" class="back-button">
                    <i class="fas fa-arrow-left"></i> Dashboard
                </a>
                <button onclick="openAddModal()" class="add-user-btn">
                    <i class="fas fa-plus"></i> Add User
                </button>
            </div>
            
            <div class="search-container">
                <input type="text" id="searchInput" placeholder="Search users...">
                <button onclick="searchUsers()">
                    <i class="fas fa-search"></i> Search
                </button>
            </div>
        </div>
        
        <table id="usersTable">
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
                            <td colspan="7" class="empty-state">
                                <i class="fas fa-users-slash"></i> No users found
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${usersList}" var="user">
                            <tr class="user-row">
                                <td>${user.id}</td>
                                <td>${user.username}</td>
                                <td class="${user.isActive ? 'status-active' : 'status-inactive'}">
                                    ${user.isActive ? 'Active' : 'Inactive'}
                                </td>
                                <td>${user.role}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty user.lastLogin}">
                                            ${user.lastLogin.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm'))}
                                        </c:when>
                                        <c:otherwise>
                                            Never logged in
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    ${user.createdAt.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm'))}
                                </td>
                                <td>
                                    <div class="action-buttons">
                                        <button class="edit-btn" 
                                            onclick="openEditModal('${user.id}', '${user.username}', ${user.isActive}, '${user.role}')">
                                            <i class="fas fa-edit"></i> Edit
                                        </button>
                                        <form class="delete-form"
                                            action="${pageContext.request.contextPath}/user/delete-user"
                                            method="POST"
                                            style="display: inline;">
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
            <h2>Update User</h2>

            <div>
                <p>
                    <strong>Username:</strong> <span id="displayUsername"></span>
                </p>
            </div>

            <form id="updateUserForm" class="registration-form"
                action="${pageContext.request.contextPath}/user/update-user"
                method="POST">
                <input type="hidden" id="id" name="id" value="">

                <div class="form-group">
                    <label class="form-label" for="isActive">Status</label> 
                    <select class="form-control" id="isActive" name="isActive" required>
                        <option value="true">Active</option>
                        <option value="false">Inactive</option>
                    </select>
                </div>

                <div class="form-group">
                    <label class="form-label" for="role">User Role</label> 
                    <select class="form-control" id="role" name="role" required>
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
            <h2>Add New User</h2>

            <form id="addUserForm" class="registration-form"
                action="${pageContext.request.contextPath}/user/create-user"
                method="POST" onsubmit="return validatePassword()">

                <div class="form-group">
                    <label class="form-label" for="newUsername">Username</label> 
                    <input type="text" class="form-control" id="newUsername" name="username" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="newPassword">Password</label> 
                    <input type="password" class="form-control" id="newPassword" name="password" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="confirmPassword">Confirm Password</label> 
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                    <div id="passwordError" class="error-message" style="display: none;">
                        Passwords do not match!
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label" for="newRole">User Role</label> 
                    <select class="form-control" id="newRole" name="role" required>
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
            const id = rows[i].cells[0].textContent;
            const username = rows[i].cells[1].textContent;
            const role = rows[i].cells[3].textContent;
            
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
    </script>
</body>
</html>