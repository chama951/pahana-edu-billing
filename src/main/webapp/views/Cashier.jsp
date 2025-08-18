<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.pahana.edu.model.enums.BillStatus" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f7fa;
            margin: 0;
            padding: 20px;
            color: #333;
        }
        
        .cart-container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
            padding: 25px;
            max-width: 1200px;
            margin: 0 auto;
        }
        
        .cart-header {
            color: #2c3e50;
            margin-bottom: 25px;
            font-size: 28px;
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .customer-info {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            display: flex;
            gap: 20px;
            align-items: center;
        }
        
        .customer-info h3 {
            margin: 0;
            color: #17a2b8;
        }
        
        .items-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        .items-table th, .items-table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
        
        .items-table th {
            background-color: #f8f9fa;
            color: #495057;
            font-weight: 600;
        }
        
        .discount-badge {
            background-color: #28a745;
            color: white;
            padding: 3px 8px;
            border-radius: 10px;
            font-size: 12px;
            font-weight: bold;
        }
        
        .original-price {
            text-decoration: line-through;
            color: #6c757d;
            font-size: 0.9em;
        }
        
        .final-price {
            color: #dc3545;
            font-weight: bold;
        }
        
        .quantity-display {
            padding: 5px;
            text-align: center;
            font-weight: bold;
        }
        
        .btn {
            padding: 8px 15px;
            border-radius: 4px;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.2s;
            display: inline-flex;
            align-items: center;
            gap: 5px;
            text-decoration: none;
            border: none;
            min-width: 120px;
            justify-content: center;
        }
        
        .btn-sm {
            padding: 6px 12px;
            font-size: 13px;
        }
        
        .back-btn {
            background-color: #6c757d;
            color: white;
        }
        
        .back-btn:hover {
            background-color: #5a6268;
        }
        
        .checkout-btn {
            background-color: #28a745;
            color: white;
        }
        
        .checkout-btn:hover {
            background-color: #218838;
        }
        
        .remove-btn {
            background-color: #dc3545;
            color: white;
        }
        
        .remove-btn:hover {
            background-color: #c82333;
        }
        
        .clear-btn {
            background-color: #ffc107;
            color: #212529;
        }
        
        .clear-btn:hover {
            background-color: #e0a800;
        }
        
        .cart-footer {
            display: flex;
            justify-content: flex-end;
            margin-top: 20px;
            padding-top: 20px;
            border-top: 1px solid #eee;
        }
        
        .total-section {
            text-align: right;
            width: 100%;
        }
        
        .total-amount {
            font-size: 1.2em;
            font-weight: bold;
            color: #2c3e50;
            margin-bottom: 15px;
        }
        
        .action-buttons {
            display: flex;
            justify-content: space-between;
            width: 100%;
        }
        
        .empty-cart {
            text-align: center;
            padding: 30px;
            color: #6c757d;
        }
        
        /* Status dropdown styles */
        .status-selection {
            margin-bottom: 15px;
        }
        
        .status-selection label {
            display: block;
            margin-bottom: 5px;
            font-weight: 600;
        }
        
        .status-selection select {
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            background-color: white;
            font-size: 14px;
            width: 200px;
            transition: border-color 0.2s;
        }
        
        .status-selection select:focus {
            outline: none;
            border-color: #17a2b8;
            box-shadow: 0 0 0 2px rgba(23, 162, 184, 0.2);
        }
    </style>
</head>
<body>
    <div class="cart-container">
        <div class="cart-header">
            <h2>Cashier</h2>
            <a href="${pageContext.request.contextPath}/views/Dashboard.jsp" class="btn back-btn">
                <i class="fas fa-arrow-left"></i> Back to Dashboard
            </a>
        </div>
        
        <c:if test="${not empty sessionScope.selectedCustomer}">
            <div class="customer-info">
                <h3>Customer: ${sessionScope.selectedCustomer.firstName} ${sessionScope.selectedCustomer.lastName}</h3>
                <span>Account #: ${sessionScope.selectedCustomer.accountNumber}</span>
            </div>
        </c:if>
        
        <c:choose>
            <c:when test="${not empty sessionScope.itemList and not empty sessionScope.itemList[0]}">
                <table class="items-table">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>ISBN</th>
                            <th>Price (LKR)</th>
                            <th>Quantity</th>
                            <th>Total (LKR)</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${sessionScope.itemList}" var="item">
                            <c:set var="discountedPrice" value="${item.price * (1 - item.discountPercentage/100)}" />
                            <tr>
                                <td><strong>${item.title}</strong></td>
                                <td>${item.isbn}</td>
                                <td>
                                    <c:if test="${item.discountPercentage > 0}">
                                        <span class="original-price">LKR ${String.format("%,.2f", item.price)}</span><br>
                                        <span class="final-price">LKR ${String.format("%,.2f", discountedPrice)}</span>
                                        <span class="discount-badge">${item.discountPercentage}% OFF</span>
                                    </c:if>
                                    <c:if test="${item.discountPercentage <= 0}">
                                        LKR ${String.format("%,.2f", item.price)}
                                    </c:if>
                                </td>
                                <td class="quantity-display">
                                    ${item.quantityInStock}
                                </td>
                                <td>LKR ${String.format("%,.2f", discountedPrice * item.quantityInStock)}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/cashier/cart-item-remove?id=${item.id}" class="btn btn-sm remove-btn">
                                        <i class="fas fa-trash"></i> Remove
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <div class="cart-footer">
                    <div class="total-section">
                        <p>Total Items: ${sessionScope.itemList.size()}</p>
                        <p class="total-amount">
                            Total Amount: LKR 
                            <c:set var="grandTotal" value="0" />
                            <c:forEach items="${sessionScope.itemList}" var="item">
                                <c:set var="itemTotal" value="${item.price * (1 - item.discountPercentage/100) * item.quantityInStock}" />
                                <c:set var="grandTotal" value="${grandTotal + itemTotal}" />
                            </c:forEach>
                            ${String.format("%,.2f", grandTotal)}
                        </p>
                        
                        <!-- Bill Status Dropdown -->
                        <div class="status-selection">
                            <label for="billStatus">Bill Status:</label>
                            <select name="billStatus" id="billStatus" class="form-control">
                                <c:forEach items="<%=BillStatus.values()%>" var="status">
                                    <option value="${status}" ${status == BillStatus.PAID ? 'selected' : ''}>
                                        ${status.displayName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="action-buttons">
                            <a href="${pageContext.request.contextPath}/cashier/clear-cart" class="btn clear-btn">
                                Clear Cart
                            </a>
                            <form id="checkoutForm" action="${pageContext.request.contextPath}/cashier/create-bill" method="POST">
                                <input type="hidden" name="billStatus" id="billStatusValue" value="PAID">
                                <button type="submit" class="btn checkout-btn">
                                    <i class="fas fa-credit-card"></i> Checkout
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                
                <script>
                    document.getElementById('billStatus').addEventListener('change', function() {
                        document.getElementById('billStatusValue').value = this.value;
                    });
                </script>
            </c:when>
            <c:otherwise>
                <div class="empty-cart">
                    <i class="fas fa-shopping-cart"></i>
                    <h2>Your cart is empty</h2>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>