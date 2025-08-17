<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="com.pahana.edu.model.enums.BillStatus"%>
<!DOCTYPE html>
<html>
<head>
<title>Invoice Management</title>
<link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: #f5f5f5;
    }
    
    .invoices-container {
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
    
    .view-btn, .edit-btn {
        padding: 6px 12px;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        display: inline-flex;
        align-items: center;
        gap: 5px;
        margin-right: 5px;
    }
    
    .view-btn {
        background-color: #17a2b8;
    }
    
    .view-btn:hover {
        background-color: #138496;
    }
    
    .edit-btn {
        background-color: #ffc107;
    }
    
    .edit-btn:hover {
        background-color: #e0a800;
    }
    
    .status-paid {
        color: #28a745;
        font-weight: bold;
    }
    
    .status-pending {
        color: #ffc107;
        font-weight: bold;
    }
    
    .status-cancelled {
        color: #dc3545;
        font-weight: bold;
    }
    
    .empty-state {
        text-align: center;
        padding: 20px;
        color: #666;
    }
    
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
        max-width: 700px;
        border-radius: 8px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
    }
    
    #editStatusModal .modal-content {
        max-width: 400px;
        padding: 20px;
    }
    
    #editStatusModal h2 {
        margin-top: 0;
        font-size: 1.5em;
        margin-bottom: 20px;
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
    
    .invoice-id-display {
        font-weight: bold;
        margin-bottom: 15px;
        padding: 10px;
        background-color: #f8f9fa;
        border-radius: 4px;
        border: 1px solid #eee;
    }
    
    .edit-status-form {
        margin-top: 15px;
    }
    
    .form-group {
        margin-bottom: 20px;
    }
    
    .form-group label {
        display: block;
        margin-bottom: 8px;
        font-weight: bold;
        color: #555;
    }
    
    .form-group select {
        width: 100%;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        font-size: 14px;
    }
    
    .form-actions {
        display: flex;
        justify-content: flex-start;
        margin-top: 25px;
    }
    
    .save-btn {
        padding: 10px 20px;
        background-color: #28a745;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
        display: flex;
        align-items: center;
        gap: 8px;
    }
    
    .save-btn:hover {
        background-color: #218838;
    }
    
    .detail-section {
        margin-bottom: 20px;
    }
    
    .detail-section h3 {
        border-bottom: 1px solid #eee;
        padding-bottom: 8px;
        margin-bottom: 12px;
        color: #333;
    }
    
    .detail-grid {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 12px;
    }
    
    .detail-item {
        margin-bottom: 8px;
    }
    
    .detail-label {
        font-weight: bold;
        color: #555;
        display: inline-block;
        min-width: 150px;
    }
    
    .detail-value {
        color: #333;
    }
</style>
</head>
<body>
    <div class="invoices-container">
        <h1>Invoice Management</h1>
        
        <div class="header-row">
            <a href="${pageContext.request.contextPath}/views/Dashboard.jsp" class="back-button">
                <i class="fas fa-arrow-left"></i> Dashboard
            </a>
            
            <div class="search-container">
                <input type="text" id="searchInput" placeholder="Search invoices...">
                <button onclick="searchInvoices()">
                    <i class="fas fa-search"></i> Search
                </button>
            </div>
        </div>
        
        <table id="invoicesTable">
            <thead>
                <tr>
                    <th>Invoice ID</th>
                    <th>Customer</th>
                    <th>Total Amount</th>
                    <th>Net Amount</th>
                    <th>Discount</th>
                    <th>Status</th>
                    <th>Created At</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty billList}">
                        <tr>
                            <td colspan="8" class="empty-state">
                                <i class="fas fa-file-invoice"></i> No invoices found
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${billList}" var="bill">
                            <tr class="invoice-row">
                                <td>${bill.id}</td>
                                <td>${bill.customer.firstName} ${bill.customer.lastName}</td>
                                <td>LKR ${bill.totalAmount}</td>
                                <td>LKR ${bill.netAmount}</td>
                                <td>LKR ${bill.discountAmount}</td>
                                <td class="status-${bill.billStatus.name().toLowerCase()}">
                                    ${bill.billStatus}
                                </td>
                                <td>
                                    ${bill.createdAt.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm'))}
                                </td>
                                <td>
                                    <button class="view-btn" onclick="openViewModal(
                                        '${bill.id}',
                                        '${bill.totalAmount}',
                                        '${bill.netAmount}',
                                        '${bill.discountAmount}',
                                        '${bill.billStatus}',
                                        '${bill.createdAt.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm'))}',
                                        '${bill.customer.id}',
                                        '${bill.customer.accountNumber}',
                                        '${bill.customer.firstName}',
                                        '${bill.customer.lastName}',
                                        '${bill.customer.address}',
                                        '${bill.customer.phoneNumber}',
                                        '${bill.customer.email}',
                                        '${bill.customer.unitsConsumed}',
                                        '${bill.customer.createdAt.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm'))}',
                                        '${bill.customer.updatedAt.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm'))}',
                                        '${bill.user.id}',
                                        '${bill.user.username}',
                                        '${bill.user.role}',
                                        ${bill.user.isActive},
                                        '${bill.user.createdAt.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm'))}',
                                        '${bill.user.updatedAt.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm'))}',
                                        '${bill.user.lastLogin != null ? bill.user.lastLogin.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm')) : ''}'
                                    )">
                                        <i class="fas fa-eye"></i> View
                                    </button>
                                    <button class="edit-btn" onclick="openEditStatusModal('${bill.id}', '${bill.billStatus}')">
                                        <i class="fas fa-edit"></i> Edit
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
    
    <!-- View Invoice Modal -->
    <div id="viewInvoiceModal" class="modal">
        <div class="modal-content">
            <span class="close-btn" onclick="closeModal('viewInvoiceModal')">&times;</span>
            <h2>Invoice Details</h2>
            
            <div class="detail-section">
                <h3>Invoice Information</h3>
                <div class="detail-grid">
                    <div class="detail-item">
                        <span class="detail-label">Invoice ID:</span>
                        <span class="detail-value" id="modalInvoiceId"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Status:</span>
                        <span class="detail-value" id="modalStatus"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Total Amount:</span>
                        <span class="detail-value" id="modalTotalAmount"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Net Amount:</span>
                        <span class="detail-value" id="modalNetAmount"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Discount Amount:</span>
                        <span class="detail-value" id="modalDiscountAmount"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Created At:</span>
                        <span class="detail-value" id="modalCreatedAt"></span>
                    </div>
                </div>
            </div>
            
            <div class="detail-section">
                <h3>Customer Information</h3>
                <div class="detail-grid">
                    <div class="detail-item">
                        <span class="detail-label">Customer ID:</span>
                        <span class="detail-value" id="modalCustomerId"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Account Number:</span>
                        <span class="detail-value" id="modalAccountNumber"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Name:</span>
                        <span class="detail-value" id="modalCustomerName"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Address:</span>
                        <span class="detail-value" id="modalCustomerAddress"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Phone:</span>
                        <span class="detail-value" id="modalCustomerPhone"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Email:</span>
                        <span class="detail-value" id="modalCustomerEmail"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Units Consumed:</span>
                        <span class="detail-value" id="modalUnitsConsumed"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Customer Since:</span>
                        <span class="detail-value" id="modalCustomerCreatedAt"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Last Updated:</span>
                        <span class="detail-value" id="modalCustomerUpdatedAt"></span>
                    </div>
                </div>
            </div>
            
            <div class="detail-section">
                <h3>Created By</h3>
                <div class="detail-grid">
                    <div class="detail-item">
                        <span class="detail-label">User ID:</span>
                        <span class="detail-value" id="modalUserId"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Username:</span>
                        <span class="detail-value" id="modalUsername"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Role:</span>
                        <span class="detail-value" id="modalUserRole"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Status:</span>
                        <span class="detail-value" id="modalUserStatus"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Account Created:</span>
                        <span class="detail-value" id="modalUserCreatedAt"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Last Updated:</span>
                        <span class="detail-value" id="modalUserUpdatedAt"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Last Login:</span>
                        <span class="detail-value" id="modalUserLastLogin"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Edit Status Modal -->
    <div id="editStatusModal" class="modal">
        <div class="modal-content">
            <span class="close-btn" onclick="closeModal('editStatusModal')">&times;</span>
            <h2>Update Invoice Status</h2>
            
            <div class="invoice-id-display">
                <strong>Invoice ID:</strong> <span id="displayInvoiceId"></span>
            </div>
            
            <form id="editStatusForm" class="edit-status-form" 
                  action="${pageContext.request.contextPath}/invoice/update-invoice" 
                  method="post">
                <input type="hidden" id="editInvoiceId" name="invoiceId">
                
                <div class="form-group">
                    <label for="editStatus">Select New Status:</label>
                    <select id="editStatus" name="status" required>
                        <% for (BillStatus status : BillStatus.values()) { %>
                            <option value="<%= status.name() %>"><%= status %></option>
                        <% } %>
                    </select>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="save-btn">
                        <i class="fas fa-save"></i> Update Status
                    </button>
                </div>
            </form>
        </div>
    </div>
    
    <script>
    function searchInvoices() {
        const input = document.getElementById('searchInput');
        const filter = input.value.toUpperCase();
        const table = document.getElementById('invoicesTable');
        const rows = table.getElementsByClassName('invoice-row');
        
        for (let i = 0; i < rows.length; i++) {
            const id = rows[i].cells[0].textContent;
            const customer = rows[i].cells[1].textContent;
            const status = rows[i].cells[5].textContent;
            
            if (id.toUpperCase().includes(filter) || 
                customer.toUpperCase().includes(filter) ||
                status.toUpperCase().includes(filter)) {
                rows[i].style.display = "";
            } else {
                rows[i].style.display = "none";
            }
        }
    }
    
    document.getElementById('searchInput').addEventListener('keyup', function(event) {
        if (event.key === 'Enter') {
            searchInvoices();
        }
    });
    
    function openViewModal(
        id, totalAmount, netAmount, discountAmount, status, createdAt,
        customerId, accountNumber, firstName, lastName, address, phoneNumber, email, 
        unitsConsumed, customerCreatedAt, customerUpdatedAt,
        userId, username, role, isActive, userCreatedAt, userUpdatedAt, lastLogin
    ) {
        // Invoice Information
        document.getElementById('modalInvoiceId').textContent = id;
        document.getElementById('modalTotalAmount').textContent = 'LKR ' + totalAmount;
        document.getElementById('modalNetAmount').textContent = 'LKR ' + netAmount;
        document.getElementById('modalDiscountAmount').textContent = 'LKR ' + discountAmount;
        document.getElementById('modalStatus').textContent = status;
        document.getElementById('modalCreatedAt').textContent = createdAt;
        
        // Customer Information
        document.getElementById('modalCustomerId').textContent = customerId;
        document.getElementById('modalAccountNumber').textContent = accountNumber;
        document.getElementById('modalCustomerName').textContent = firstName + ' ' + lastName;
        document.getElementById('modalCustomerAddress').textContent = address;
        document.getElementById('modalCustomerPhone').textContent = phoneNumber;
        document.getElementById('modalCustomerEmail').textContent = email;
        document.getElementById('modalUnitsConsumed').textContent = unitsConsumed;
        document.getElementById('modalCustomerCreatedAt').textContent = customerCreatedAt;
        document.getElementById('modalCustomerUpdatedAt').textContent = customerUpdatedAt;
        
        // User Information
        document.getElementById('modalUserId').textContent = userId;
        document.getElementById('modalUsername').textContent = username;
        document.getElementById('modalUserRole').textContent = role;
        document.getElementById('modalUserStatus').textContent = isActive ? 'Active' : 'Inactive';
        document.getElementById('modalUserCreatedAt').textContent = userCreatedAt;
        document.getElementById('modalUserUpdatedAt').textContent = userUpdatedAt;
        document.getElementById('modalUserLastLogin').textContent = lastLogin || 'Never logged in';
        
        document.getElementById('viewInvoiceModal').style.display = 'block';
    }
    
    function openEditStatusModal(invoiceId, currentStatus) {
        document.getElementById('editInvoiceId').value = invoiceId;
        document.getElementById('displayInvoiceId').textContent = invoiceId;
        document.getElementById('editStatus').value = currentStatus;
        document.getElementById('editStatusModal').style.display = 'block';
    }
    
    function closeModal(modalId) {
        document.getElementById(modalId).style.display = 'none';
    }
    
    window.onclick = function(event) {
        if (event.target.className === 'modal') {
            event.target.style.display = 'none';
        }
    }
    </script>
</body>
</html>