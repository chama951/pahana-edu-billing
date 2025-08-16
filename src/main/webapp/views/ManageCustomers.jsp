<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html>
<head>
<title>Customer Management</title>
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
    
    .add-customer-icon {
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
    
    .add-customer-icon:hover {
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
        padding: 25px;
        border: 1px solid #888;
        width: 60%;
        max-width: 500px;
        border-radius: 8px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
    }
    
    .close-btn {
        color: #aaa;
        float: right;
        font-size: 24px;
        font-weight: bold;
        cursor: pointer;
        margin-top: -10px;
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
        background-color: #28a745;
        color: white;
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
    }
    
    .btn:hover {
        background-color: #218838;
    }
    
    .error-message {
        color: red;
        font-size: 14px;
        margin-top: 5px;
    }
    
    /* Button styles */
    .select-btn, .edit-btn, .delete-btn {
        padding: 6px 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        display: inline-flex;
        align-items: center;
        gap: 5px;
        margin-right: 5px;
    }
    
    .select-btn {
        background-color: #4285F4;
        color: white;
    }
    
    .select-btn:hover {
        background-color: #3367D6;
    }
    
    .edit-btn {
        background-color: #FFC107;
        color: #333;
    }
    
    .edit-btn:hover {
        background-color: #E0A800;
        color: #000;
    }
    
    .delete-btn {
        background-color: #dc3545;
        color: white;
    }
    
    .delete-btn:hover {
        background-color: #c82333;
    }
    
    .action-buttons {
        display: flex;
        align-items: center;
        gap: 5px;
    }
</style>
</head>
<body>
    <div class="users-container">
        <h1>Customer Management</h1>

        <div class="header-row">
            <div class="header-actions">
                <a href="${pageContext.request.contextPath}/views/Dashboard.jsp"
                    class="back-button">
                    <i class="fas fa-arrow-left"></i> Dashboard
                </a>
                <button onclick="openAddModal()" class="add-customer-icon">
                    <i class="fas fa-plus"></i> Add Customer
                </button>
            </div>

            <div class="search-container">
                <input type="text" id="searchInput" placeholder="Search customers...">
                <button onclick="searchCustomers()">
                    <i class="fas fa-search"></i> Search
                </button>
            </div>
        </div>

        <table class="users-table" id="customersTable">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Account Number</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Units Consumed</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty customerList}">
                        <tr>
                            <td colspan="8" class="empty-state"><i
                                class="fas fa-users-slash"
                            ></i> No customers found</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${customerList}" var="customer">
                            <tr class="customer-row">
                                <td class="customer-id">${customer.id}</td>
                                <td class="account-number">${customer.accountNumber}</td>
                                <td class="first-name">${customer.firstName}</td>
                                <td class="last-name">${customer.lastName}</td>
                                <td class="email">${customer.email}</td>
                                <td class="phone">${customer.phoneNumber}</td>
                                <td class="units-consumed">${customer.unitsConsumed}</td>
                                <td>
                                    <div class="action-buttons">
                                        <form method="post" action="${pageContext.request.contextPath}/customer/select-customer" style="display: inline;">
                                            <input type="hidden" name="customerId" value="${customer.id}">
                                            <button type="submit" class="select-btn">
                                                <i class="fas fa-check"></i> Select
                                            </button>
                                        </form>
                                        <button class="edit-btn" 
                                            onclick="openEditModal(
                                                '${customer.id}',
                                                '${customer.accountNumber}',
                                                '${customer.firstName}',
                                                '${customer.lastName}',
                                                '${customer.address}',
                                                '${customer.phoneNumber}',
                                                '${customer.email}',
                                                ${customer.unitsConsumed}
                                            )">
                                            <i class="fas fa-edit"></i> Edit
                                        </button>
                                        <form class="delete-form"
                                            action="${pageContext.request.contextPath}/customer/delete-customer"
                                            method="POST"
                                            style="display: inline;">
                                            <input type="hidden" name="id" value="${customer.id}">
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

    <!-- Combined Add/Edit Customer Modal -->
    <div id="customerModal" class="modal">
        <div class="modal-content">
            <button class="close-btn" onclick="closeModal()">&times;</button>
            <h2 class="modal-header" id="modalTitle">Add New Customer</h2>

            <form id="customerForm" class="registration-form"
                action="${pageContext.request.contextPath}/customer/create-customer"
                method="POST"
            >
                <input type="hidden" id="id" name="id" value="">

                <div class="form-group">
                    <label class="form-label" for="accountNumber">Account
                        Number</label> <input type="text" class="form-control" id="accountNumber"
                        name="accountNumber" required
                    >
                </div>

                <div class="form-group">
                    <label class="form-label" for="firstName">First Name</label> <input
                        type="text" class="form-control" id="firstName" name="firstName"
                        required
                    >
                </div>

                <div class="form-group">
                    <label class="form-label" for="lastName">Last Name</label> <input
                        type="text" class="form-control" id="lastName" name="lastName"
                        required
                    >
                </div>

                <div class="form-group">
                    <label class="form-label" for="address">Address</label> <input
                        type="text" class="form-control" id="address" name="address"
                        required
                    >
                </div>

                <div class="form-group">
                    <label class="form-label" for="phoneNumber">Phone Number</label> <input
                        type="tel" class="form-control" id="phoneNumber"
                        name="phoneNumber" required
                    >
                </div>

                <div class="form-group">
                    <label class="form-label" for="email">Email</label> <input
                        type="email" class="form-control" id="email" name="email" required
                    >
                </div>

                <div class="form-group">
                    <label class="form-label" for="unitsConsumed">Units
                        Consumed</label> <input type="number" class="form-control"
                        id="unitsConsumed" name="unitsConsumed" required
                    >
                </div>

                <button type="submit" class="btn" id="submitButton">Add
                    Customer</button>
            </form>
        </div>
    </div>

    <script>
    // Search function
    function searchCustomers() {
        const input = document.getElementById('searchInput');
        const filter = input.value.toUpperCase();
        const table = document.getElementById('customersTable');
        const rows = table.getElementsByClassName('customer-row');
        
        for (let i = 0; i < rows.length; i++) {
            const id = rows[i].getElementsByClassName('customer-id')[0].textContent;
            const accountNumber = rows[i].getElementsByClassName('account-number')[0].textContent;
            const firstName = rows[i].getElementsByClassName('first-name')[0].textContent;
            const lastName = rows[i].getElementsByClassName('last-name')[0].textContent;
            const email = rows[i].getElementsByClassName('email')[0].textContent;
            
            if (id.toUpperCase().includes(filter) || 
                accountNumber.toUpperCase().includes(filter) || 
                firstName.toUpperCase().includes(filter) ||
                lastName.toUpperCase().includes(filter) ||
                email.toUpperCase().includes(filter)) {
                rows[i].style.display = "";
            } else {
                rows[i].style.display = "none";
            }
        }
    }

    // Open modal for adding new customer
    function openAddModal() {
        // Reset form
        document.getElementById('customerForm').reset();
        // Change form action for adding
        document.getElementById('customerForm').action = "${pageContext.request.contextPath}/customer/create-customer";
        // Update modal title
        document.getElementById('modalTitle').textContent = "Add New Customer";
        // Update submit button text
        document.getElementById('submitButton').textContent = "Add Customer";
        // Clear the ID field
        document.getElementById('id').value = "";
        // Show modal
        document.getElementById('customerModal').style.display = 'block';
    }

    // Open modal for editing existing customer
    function openEditModal(id, accountNumber, firstName, lastName, address, phoneNumber, email, unitsConsumed) {
        // Set form values
        document.getElementById('id').value = id;
        document.getElementById('accountNumber').value = accountNumber;
        document.getElementById('firstName').value = firstName;
        document.getElementById('lastName').value = lastName;
        document.getElementById('address').value = address;
        document.getElementById('phoneNumber').value = phoneNumber;
        document.getElementById('email').value = email;
        document.getElementById('unitsConsumed').value = unitsConsumed;
        // Change form action for updating
        document.getElementById('customerForm').action = "${pageContext.request.contextPath}/customer/update-customer";
        // Update modal title
        document.getElementById('modalTitle').textContent = "Update Customer";
        // Update submit button text
        document.getElementById('submitButton').textContent = "Update Customer";
        // Show modal
        document.getElementById('customerModal').style.display = 'block';
        return false; // Prevent default anchor behavior
    }

    // Close modal function
    function closeModal() {
        document.getElementById('customerModal').style.display = 'none';
    }

    // Close modal with ESC key
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape' && document.getElementById('customerModal').style.display === 'block') {
            closeModal();
        }
    });

    // Search on Enter key
    document.getElementById('searchInput').addEventListener('keyup', function(event) {
        if (event.key === 'Enter') {
            searchCustomers();
        }
    });
    </script>
</body>
</html>