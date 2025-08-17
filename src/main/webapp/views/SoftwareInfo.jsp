<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Software User Guide</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            color: #333;
            max-width: 1000px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f9f9f9;
        }
        h1 {
            color: #2c3e50;
            border-bottom: 2px solid #3498db;
            padding-bottom: 10px;
            text-align: center;
        }
        h2 {
            color: #2980b9;
            margin-top: 40px;
            background-color: #f0f7fc;
            padding: 12px;
            border-left: 5px solid #3498db;
            border-radius: 4px 0 0 4px;
        }
        h3 {
            color: #16a085;
            margin-top: 25px;
        }
        .section {
            margin-bottom: 35px;
            background-color: #fff;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.08);
        }
        .requirements {
            background-color: #f8f9fa;
            padding: 16px;
            border-left: 4px solid #e74c3c;
            margin: 18px 0;
            border-radius: 4px;
        }
        .note {
            background-color: #fffde7;
            padding: 14px;
            border-left: 4px solid #ffc107;
            margin: 16px 0;
            border-radius: 4px;
        }
        .logout-note {
            background-color: #fff3e0;
            padding: 14px;
            border-left: 4px solid #ff9800;
            margin: 16px 0;
            border-radius: 4px;
            font-style: italic;
        }
        .restriction {
            background-color: #ffebee;
            padding: 14px;
            border-left: 4px solid #f44336;
            margin: 16px 0;
            border-radius: 4px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 18px 0;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 14px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #3498db;
            color: white;
            font-weight: 500;
        }
        tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .steps {
            margin-left: 22px;
            padding-left: 10px;
        }
        .steps li {
            margin-bottom: 10px;
            padding-left: 5px;
        }
        .menu-options {
            list-style-type: none;
            padding-left: 20px;
            margin: 10px 0;
        }
        .menu-options li {
            margin-bottom: 8px;
            position: relative;
            padding-left: 25px;
        }
        .menu-options li:before {
            content: "•";
            color: #3498db;
            font-weight: bold;
            position: absolute;
            left: 0;
        }
        .toc {
            background-color: #f0f7fc;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 30px;
        }
        .toc ol {
            padding-left: 25px;
        }
        .toc li {
            margin-bottom: 8px;
        }
        a {
            color: #2980b9;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h1>Software User Guide</h1>
    
    <div class="toc">
        <h2>Table of Contents</h2>
        <ol>
            <li><a href="#user-management">User Account Management</a></li>
            <li><a href="#customer-management">Customer Management</a></li>
            <li><a href="#item-management">Item Management</a></li>
            <li><a href="#cashier-operations">Cashier Operations</a></li>
            <li><a href="#invoice-management">Invoice Management</a></li>
            <li><a href="#quick-reference">Quick Reference</a></li>
        </ol>
    </div>

    <div id="user-management" class="section">
        <h2>1. User Account Management</h2>
        
        <h3>Accessing Your Account Options</h3>
        <ol class="steps">
            <li>Locate the dropdown menu at the top-right corner of the screen (click your username)</li>
            <li>You will see these options:
                <ul class="menu-options">
                    <li><strong>Change Password</strong> - Update your current password</li>
                    <li><strong>Change Username</strong> - Modify your login username</li>
                    <li><strong>Logout</strong> - Always appears at the bottom of the menu to sign out securely</li>
                </ul>
            </li>
            <li>Select your desired action and follow the on-screen instructions</li>
        </ol>
        
        <div class="logout-note">
            <strong>Important:</strong> The logout button is permanently positioned at the bottom of the user menu for quick access. Always log out when you're finished, especially on shared computers.
        </div>
        
        <h3>Password Requirements</h3>
        <div class="requirements">
            <strong>When changing your password:</strong>
            <ul class="steps">
                <li>Must be at least 8 characters long</li>
                <li>Must contain at least one uppercase letter (A-Z)</li>
                <li>Must contain at least one lowercase letter (a-z)</li>
                <li>Must contain at least one number (0-9)</li>
                <li>Must contain at least one special character (!@#$%^&*)</li>
                <li>Cannot contain any spaces</li>
            </ul>
        </div>
        
        <h3>Adding New Users (Admin Only)</h3>
        <ol class="steps">
            <li>Navigate to <strong>User Management > Add User</strong></li>
            <li>Complete all required fields:
                <ul class="steps">
                    <li><strong>Username:</strong> 4-20 characters, letters only (no numbers or symbols)</li>
                    <li><strong>Password:</strong> Must meet all requirements listed above</li>
                    <li><strong>User Role:</strong> Select from Admin, Cashier, or Inventory Manager</li>
                </ul>
            </li>
            <li>Click "Save" to create the new user account</li>
        </ol>
        
        <div class="note">
            <strong>Security Note:</strong> Administrators cannot change their own privileges or delete their own account from within the system. Contact your system administrator for these actions.
        </div>
    </div>

    <div id="customer-management" class="section">
        <h2>2. Customer Management</h2>
        
        <h3>Adding a New Customer</h3>
        <ol class="steps">
            <li>Go to <strong>Customer Management > Add Customer</strong></li>
            <li>Fill in customer details:
                <ul class="steps">
                    <li><strong>First Name:</strong> Letters only, max 50 characters</li>
                    <li><strong>Last Name:</strong> Letters only, max 50 characters</li>
                    <li><strong>Address:</strong> Required, max 200 characters</li>
                    <li><strong>Phone Number:</strong> Required, valid international format (+1234567890)</li>
                    <li><strong>Email:</strong> Optional, must be valid format (name@domain.com)</li>
                    <li><strong>Account Number:</strong> Numbers only, positive value</li>
                </ul>
            </li>
            <li>Click "Save" to store customer information</li>
        </ol>
        
        <h3>Searching Customers</h3>
        <ul class="steps">
            <li>Use the search bar to find customers by name, phone, or account number</li>
            <li>Click on a customer to view/edit details or create a bill</li>
        </ul>
    </div>

    <div id="item-management" class="section">
        <h2>3. Item Management</h2>
        
        <h3>Adding New Items</h3>
        <ol class="steps">
            <li>Go to <strong>Item Management > Add Item</strong></li>
            <li>Fill in item details:
                <ul class="steps">
                    <li><strong>Title:</strong> Required, max 255 characters</li>
                    <li><strong>ISBN:</strong> Optional, must be valid ISBN format if provided</li>
                    <li><strong>Price:</strong> Required, must be positive</li>
                    <li><strong>Quantity:</strong> Optional, cannot be negative</li>
                    <li><strong>Author:</strong> Required, max 100 characters</li>
                    <li><strong>Publisher:</strong> Optional, max 100 characters</li>
                    <li><strong>Description:</strong> Optional, max 2000 characters</li>
                </ul>
            </li>
            <li>Click "Save" to add the item to inventory</li>
        </ol>
        
        <h3>Managing Inventory</h3>
        <ul class="steps">
            <li>Search for items using the search bar</li>
            <li>Edit existing items by clicking on them</li>
            <li>Add items to cart for checkout</li>
        </ul>
    </div>

    <div id="cashier-operations" class="section">
        <h2>4. Cashier Operations</h2>
        
        <div class="restriction">
            <strong>Cashier Permissions:</strong> Cashiers can only create/view bills and change bill status. They cannot modify inventory or user accounts.
        </div>
        
        <h3>Creating a Sale</h3>
        <ol class="steps">
            <li>Search and select a customer</li>
            <li>Add items to cart:
                <ul class="steps">
                    <li>Search for items</li>
                    <li>Click "Add to Cart"</li>
                </ul>
            </li>
            <li>In the cart view:
                <ul class="steps">
                    <li>Remove items if needed</li>
                    <li>Clear the cart to start over</li>
                </ul>
            </li>
            <li>Click "Checkout" to:
                <ul class="steps">
                    <li>Generate PDF invoice</li>
                    <li>Set invoice status (Paid/Unpaid)</li>
                </ul>
            </li>
        </ol>
        
        <h3>Viewing Bills</h3>
        <ul class="steps">
            <li>Go to <strong>Invoice Management</strong></li>
            <li>View all bills or search for specific ones</li>
            <li>Click on a bill to view details</li>
        </ul>
        
        <h3>Changing Bill Status</h3>
        <ol class="steps">
            <li>Open the bill you want to modify</li>
            <li>Click "Edit Status" button</li>
            <li>Select new status (Paid/Unpaid)</li>
            <li>Click "Save" to update</li>
        </ol>
    </div>

    <div id="invoice-management" class="section">
        <h2>5. Invoice Management</h2>
        
        <h3>Viewing Invoices</h3>
        <ol class="steps">
            <li>Go to <strong>Invoice Management</strong></li>
            <li>View all invoices or search for specific ones</li>
            <li>Click on an invoice to view details</li>
        </ol>
        
        <h3>Invoice Status</h3>
        <ul class="steps">
            <li>Mark invoices as Paid/Unpaid</li>
            <li>Print or email invoices to customers</li>
        </ul>
    </div>

    <div id="quick-reference" class="section">
        <h2>6. Quick Reference</h2>
        
        <h3>Role Permissions</h3>
        <table>
            <tr>
                <th>Role</th>
                <th>Permissions</th>
            </tr>
            <tr>
                <td>Admin</td>
                <td>Full access to all features</td>
            </tr>
            <tr>
                <td>Cashier</td>
                <td>Create/view bills and change bill status only</td>
            </tr>
            <tr>
                <td>Inventory Manager</td>
                <td>Add/edit items and manage inventory</td>
            </tr>
        </table>
        
        <h3>Common Error Messages</h3>
        <ul class="steps">
            <li><strong>Username issues:</strong> Must be 4-20 letters only</li>
            <li><strong>Password issues:</strong> Doesn't meet complexity requirements</li>
            <li><strong>Customer data issues:</strong> Missing required fields or invalid formats</li>
            <li><strong>Item issues:</strong> Invalid price or missing title/author</li>
            <li><strong>Permission errors:</strong> Action not allowed for your role</li>
        </ul>
        
        <div class="note">
            <strong>For assistance:</strong> Contact your system administrator if you encounter any issues or need additional permissions.
        </div>
    </div>
</body>
</html>