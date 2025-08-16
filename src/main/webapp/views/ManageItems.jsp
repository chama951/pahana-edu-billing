<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>Item Management</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: #f5f5f5;
    }
    
    .items-container {
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
    
    .add-item-btn {
        display: inline-flex;
        align-items: center;
        padding: 8px 12px;
        background-color: #17a2b8;
        color: white;
        border-radius: 4px;
        text-decoration: none;
        gap: 5px;
        border: none;
        cursor: pointer;
        margin-left: 10px;
    }
    
    .add-item-btn:hover {
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
    
    .action-btn {
        padding: 6px 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        display: inline-flex;
        align-items: center;
        gap: 5px;
        margin-right: 5px;
        font-size: 14px;
    }
    
    .add-to-cart-btn {
        background-color: #4285F4;
        color: white;
    }
    
    .add-to-cart-btn:hover {
        background-color: #3367D6;
    }
    
    .edit-btn {
        background-color: #ffc107;
        color: #333;
    }
    
    .edit-btn:hover {
        background-color: #e0a800;
        color: #000;
    }
    
    .delete-btn {
        background-color: #dc3545;
        color: white;
    }
    
    .delete-btn:hover {
        background-color: #c82333;
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
        max-width: 600px;
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
    
    .form-group {
        margin-bottom: 15px;
    }
    
    .form-group label {
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
        font-size: 14px;
    }
    
    .submit-btn {
        background-color: #28a745;
        color: white;
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
        width: 100%;
    }
    
    .submit-btn:hover {
        background-color: #218838;
    }
    
    /* Quantity Modal - more compact */
    .quantity-modal .modal-content {
        max-width: 400px;
    }
    
    .quantity-modal h2 {
        margin-top: 0;
    }
</style>
</head>
<body>
    <div class="items-container">
        <h1>Item Management</h1>

        <div class="header-row">
            <div>
                <a href="${pageContext.request.contextPath}/views/Dashboard.jsp" class="back-button">
                    <i class="fas fa-arrow-left"></i> Dashboard
                </a>
                <button onclick="openAddModal()" class="add-item-btn">
                    <i class="fas fa-plus"></i> Add Item
                </button>
            </div>

            <div class="search-container">
                <input type="text" id="searchInput" placeholder="Search items...">
                <button onclick="searchItems()">
                    <i class="fas fa-search"></i> Search
                </button>
            </div>
        </div>

        <table id="itemsTable">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>ISBN</th>
                    <th>Price(LKR)</th>
                    <th>Discount %</th>
                    <th>Quantity</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty itemList}">
                        <tr>
                            <td colspan="7" class="empty-state"><i class="fas fa-box-open"></i> No items found</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${itemList}" var="item">
                            <tr class="item-row">
                                <td class="item-id">${item.id}</td>
                                <td class="title">${fn:escapeXml(item.title)}</td>
                                <td class="isbn">${fn:escapeXml(item.isbn)}</td>
                                <td class="price">${item.price}</td>
                                <td class="discount">${item.discountPercentage}</td>
                                <td class="quantity">${item.quantityInStock}</td>
                                <td>
                                    <button class="action-btn add-to-cart-btn" onclick="openQuantityModal('${item.id}')">
                                        <i class="fas fa-cart-plus"></i> Add to Cart
                                    </button>
                                    <button class="action-btn edit-btn" 
                                        onclick="openEditModal(
                                           '${item.id}',
                                           `${fn:escapeXml(item.title)}`,
                                           `${fn:escapeXml(item.isbn)}`,
                                           '${item.price}',
                                           '${item.discountPercentage}',
                                           '${item.quantityInStock}',
                                           `${fn:escapeXml(item.description)}`,
                                           `${fn:escapeXml(item.author)}`,
                                           '${item.publicationYear}',
                                           `${fn:escapeXml(item.publisher)}`
                                        )">
                                        <i class="fas fa-edit"></i> Edit
                                    </button>
                                    <form class="delete-form"
                                        action="${pageContext.request.contextPath}/item/delete-item"
                                        method="POST"
                                        style="display: inline;">
                                        <input type="hidden" name="id" value="${item.id}">
                                        <button type="submit" class="action-btn delete-btn">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

    <!-- Combined Add/Edit Item Modal -->
    <div id="itemModal" class="modal">
        <div class="modal-content">
            <span class="close-btn" onclick="closeModal('itemModal')">&times;</span>
            <h2 id="modalTitle">Add New Item</h2>

            <form id="itemForm" method="POST">
                <input type="hidden" id="id" name="id" value="">

                <div class="form-group">
                    <label for="title">Title</label> 
                    <input type="text" class="form-control" id="title" name="title" required>
                </div>

                <div class="form-group">
                    <label for="isbn">ISBN</label> 
                    <input type="text" class="form-control" id="isbn" name="isbn" required>
                </div>

                <div class="form-group">
                    <label for="price">Price</label> 
                    <input type="number" step="0.01" class="form-control" id="price" name="price" required>
                </div>

                <div class="form-group">
                    <label for="discountPercentage">Discount Percentage</label> 
                    <input type="number" step="0.01" class="form-control"
                        id="discountPercentage" name="discountPercentage" min="0"
                        max="100" value="0.0">
                </div>

                <div class="form-group">
                    <label for="quantityInStock">Quantity in Stock</label> 
                    <input type="number" class="form-control"
                        id="quantityInStock" name="quantityInStock" required>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                </div>

                <div class="form-group">
                    <label for="author">Author</label> 
                    <input type="text" class="form-control" id="author" name="author">
                </div>

                <div class="form-group">
                    <label for="publicationYear">Publication Year</label> 
                    <input type="number" class="form-control"
                        id="publicationYear" name="publicationYear" min="1900" max="2099">
                </div>

                <div class="form-group">
                    <label for="publisher">Publisher</label> 
                    <input type="text" class="form-control" id="publisher" name="publisher">
                </div>

                <button type="submit" class="submit-btn" id="submitButton">Add Item</button>
            </form>
        </div>
    </div>

    <!-- Quantity Modal -->
    <div id="quantityModal" class="modal quantity-modal">
        <div class="modal-content">
            <span class="close-btn" onclick="closeModal('quantityModal')">&times;</span>
            <h2>Enter Quantity</h2>
            
            <form id="quantityForm" method="POST" action="${pageContext.request.contextPath}/item/add-to-cart">
                <input type="hidden" id="cartItemId" name="itemId" value="">
                
                <div class="form-group">
                    <label for="quantity">Quantity:</label>
                    <input type="number" class="form-control" id="quantity" name="quantity" 
                           min="1" value="1" required>
                </div>
                
                <button type="submit" class="submit-btn">Add to Cart</button>
            </form>
        </div>
    </div>

    <script>
    // Search function
    function searchItems() {
        const input = document.getElementById('searchInput');
        const filter = input.value.toUpperCase();
        const table = document.getElementById('itemsTable');
        const rows = table.getElementsByClassName('item-row');

        for (let i = 0; i < rows.length; i++) {
            const id = rows[i].getElementsByClassName('item-id')[0].textContent;
            const title = rows[i].getElementsByClassName('title')[0].textContent;
            const isbn = rows[i].getElementsByClassName('isbn')[0].textContent;
            const price = rows[i].getElementsByClassName('price')[0].textContent;

            if (id.toUpperCase().includes(filter) ||
                title.toUpperCase().includes(filter) ||
                isbn.toUpperCase().includes(filter) ||
                price.toUpperCase().includes(filter)) {
                rows[i].style.display = "";
            } else {
                rows[i].style.display = "none";
            }
        }
    }
    
    // Search on Enter key
    document.getElementById('searchInput').addEventListener('keyup', function(event) {
        if (event.key === 'Enter') {
            searchItems();
        }
    });

    // Close modal function
    function closeModal(modalId) {
        document.getElementById(modalId).style.display = 'none';
    }

    // Open modal for adding new item
    function openAddModal() {
        // Reset form
        document.getElementById('itemForm').reset();
        // Explicitly set discount to 0.0 for new items
        document.getElementById('discountPercentage').value = "0.0";
        // Change form action for adding
        document.getElementById('itemForm').action = "${pageContext.request.contextPath}/item/create-item";
        // Update modal title
        document.getElementById('modalTitle').textContent = "Add New Item";
        // Update submit button text
        document.getElementById('submitButton').textContent = "Add Item";
        // Clear the ID field
        document.getElementById('id').value = "";
        // Show modal
        document.getElementById('itemModal').style.display = 'block';
    }

    // Open modal for editing existing item
    function openEditModal(id, title, isbn, price, discountPercentage,
        quantity, description, author, publicationYear, publisher) {
        // Set form values
        document.getElementById('id').value = id;
        document.getElementById('title').value = title;
        document.getElementById('isbn').value = isbn;
        document.getElementById('price').value = price;
        // Use the passed discountPercentage value, default to 0.0 if empty
        document.getElementById('discountPercentage').value = discountPercentage || "0.0";
        document.getElementById('quantityInStock').value = quantity;
        document.getElementById('description').value = description;
        document.getElementById('author').value = author;
        document.getElementById('publicationYear').value = publicationYear;
        document.getElementById('publisher').value = publisher;

        // Change form action for updating
        document.getElementById('itemForm').action = "${pageContext.request.contextPath}/item/update-item";
        document.getElementById('modalTitle').textContent = "Update Item";
        document.getElementById('submitButton').textContent = "Update Item";
        document.getElementById('itemModal').style.display = 'block';
        return false;
    }
    
    // Open modal for entering quantity
    function openQuantityModal(itemId) {
        document.getElementById('cartItemId').value = itemId;
        document.getElementById('quantity').value = 1;
        document.getElementById('quantityModal').style.display = 'block';
    }

    // Initialize when DOM is loaded
    document.addEventListener('DOMContentLoaded', function() {
        // Set up search functionality
        document.getElementById('searchInput').addEventListener('keyup', function(event) {
            if (event.key === 'Enter') {
                searchItems();
            }
        });
    });
    </script>
</body>
</html>