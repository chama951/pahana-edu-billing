<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>Item Management</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/DisplayUsers.css"
>
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
	margin: 10% auto;
	padding: 30px 40px;
	border: 1px solid #888;
	width: 80%;
	max-width: 600px;
	border-radius: 8px;
	box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

/* Form group spacing */
.form-group {
	margin-bottom: 20px;
}

/* Input field padding */
.form-control {
	padding: 10px 12px;
}

/* Button spacing */
.btn {
	margin-top: 10px;
	padding: 12px 20px;
}

/* Close button position adjustment */
.close-btn {
	position: absolute;
	top: 20px;
	right: 25px;
	font-size: 30px;
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
		<h1 class="users-header">Item Management</h1>

		<div class="header-row">
			<div class="header-actions">
				<a href="${pageContext.request.contextPath}/views/Dashboard.jsp"
					class="back-button"
				> <i class="fas fa-arrow-left"></i> Dashboard
				</a>
				<button onclick="openAddModal()" class="add-user-icon"
					title="Add New Item"
				>
					<i class="fas fa-plus"></i>
				</button>
			</div>

			<div class="search-container">
				<input type="text" id="searchInput" placeholder="Search items...">
				<button onclick="searchItems()">
					<i class="fas fa-search"></i> Search
				</button>
			</div>
		</div>

		<table class="users-table" id="itemsTable">
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
							<td colspan="7" class="empty-state"><i
								class="fas fa-box-open"
							></i> No items found</td>
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
									<div class="action-buttons">
										<a href="#" class="action-btn edit-btn"
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
                                           )"
										> <i class="fas fa-edit"></i> Edit
										</a>
										<form class="delete-form"
											action="${pageContext.request.contextPath}/item/delete-item"
											method="POST"
										>
											<input type="hidden" name="id" value="${item.id}">
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

	<!-- Combined Add/Edit Item Modal -->
	<div id="itemModal" class="modal">
		<div class="modal-content">
			<button class="close-btn">&times;</button>
			<h2 class="modal-header" id="modalTitle">Add New Item</h2>

			<form id="itemForm" class="registration-form" method="POST">
				<input type="hidden" id="id" name="id" value="">

				<div class="form-group">
					<label class="form-label" for="title">Title</label> <input
						type="text" class="form-control" id="title" name="title" required
					>
				</div>

				<div class="form-group">
					<label class="form-label" for="isbn">ISBN</label> <input
						type="text" class="form-control" id="isbn" name="isbn" required
					>
				</div>

				<div class="form-group">
					<label class="form-label" for="price">Price</label> <input
						type="number" step="0.01" class="form-control" id="price"
						name="price" required
					>
				</div>

				<div class="form-group">
					<label class="form-label" for="discountPercentage">Discount
						Percentage</label> <input type="number" step="0.01" class="form-control"
						id="discountPercentage" name="discountPercentage" min="0"
						max="100" value="0.0"
					>
				</div>

				<div class="form-group">
					<label class="form-label" for="quantityInStock">Quantity in
						Stock</label> <input type="number" class="form-control"
						id="quantityInStock" name="quantityInStock" required
					>
				</div>

				<div class="form-group">
					<label class="form-label" for="description">Description</label>
					<textarea class="form-control" id="description" name="description"
						rows="3"
					></textarea>
				</div>

				<div class="form-group">
					<label class="form-label" for="author">Author</label> <input
						type="text" class="form-control" id="author" name="author"
					>
				</div>

				<div class="form-group">
					<label class="form-label" for="publicationYear">Publication
						Year</label> <input type="number" class="form-control"
						id="publicationYear" name="publicationYear" min="1900" max="2099"
					>
				</div>

				<div class="form-group">
					<label class="form-label" for="publisher">Publisher</label> <input
						type="text" class="form-control" id="publisher" name="publisher"
					>
				</div>

				<button type="submit" class="btn" id="submitButton">Add
					Item</button>
			</form>
		</div>
	</div>

	<script>
	// Initialize when DOM is loaded
	document
		.addEventListener(
			'DOMContentLoaded',
			function() {
			    // Set up search functionality
			    document.getElementById('searchInput')
				    .addEventListener('keyup', function(event) {
					if (event.key === 'Enter') {
					    searchItems();
					}
				    });

			    // Close modal handlers
			    document
				    .querySelector('.close-btn')
				    .addEventListener(
					    'click',
					    function() {
						document
							.getElementById('itemModal').style.display = 'none';
					    });

			    window
				    .addEventListener(
					    'click',
					    function(e) {
						if (e.target === document
							.getElementById('itemModal')) {
						    document
							    .getElementById('itemModal').style.display = 'none';
						}
					    });
			});

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

		if (id.toUpperCase().includes(filter)
			|| title.toUpperCase().includes(filter)
			|| isbn.toUpperCase().includes(filter)
			|| price.toUpperCase().includes(filter)) {
		    rows[i].style.display = "";
		} else {
		    rows[i].style.display = "none";
		}
	    }
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
	    document.getElementById('discountPercentage').value = discountPercentage
		    || "0.0";
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
    </script>
</body>
</html>