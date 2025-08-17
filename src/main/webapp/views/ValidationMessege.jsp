<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Validation Error</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: rgba(0,0,0,0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .error-container {
            background: white;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            width: 90%;
            max-width: 500px;
            padding: 1.5rem;
            text-align: center;
        }
        .error-icon {
            color: #dc3545;
            font-size: 3rem;
            margin: 1rem 0;
        }
        h2 {
            color: #dc3545;
            margin: 1rem 0;
            font-size: 1.5rem;
        }
        .error-message {
            background-color: #fff5f5;
            color: #d32f2f;
            padding: 1rem;
            border-radius: 8px;
            margin: 1rem 0;
            border: 1px solid #ffcdd2;
            text-align: left;
        }
        .btn {
            padding: 0.75rem 1.5rem;
            background-color: #dc3545;
            color: white;
            border: none;
            border-radius: 8px;
            margin-top: 1rem;
            cursor: pointer;
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" media="print" onload="this.media='all'">
</head>
<body>
    <div class="error-container">
        <div class="error-icon">
            <i class="fas fa-exclamation-triangle"></i>
        </div>
        
        <div class="error-message">
            <p>The following errors occurred:</p>
            <ul>
                <% 
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
                    String[] errors = errorMessage.split("\n");
                    for (String error : errors) {
                %>
                    <li><%= error %></li>
                <% 
                    }
                } else {
                %>
                    <li>No specific error details available</li>
                <% } %>
            </ul>
        </div>

        <button class="btn" onclick="handleClose()">
            <i class="fas fa-arrow-left"></i> Return to Form
        </button>
    </div>

    <script>
        function handleClose() {
            const buttonPath = "<%= request.getAttribute("buttonPath") != null ? 
                              request.getAttribute("buttonPath") : "" %>";
            
            if (buttonPath) {
                window.location.href = "<%= request.getContextPath() %>" + buttonPath;
            } else {
                window.history.back();
            }
        }
    </script>
</body>
</html>