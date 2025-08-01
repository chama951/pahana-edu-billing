<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Error Occurred</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: rgba(0,0,0,0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            font-family: Arial, sans-serif;
        }
        .popup-container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
            width: 90%;
            max-width: 400px;
            padding: 30px;
            text-align: center;
            position: relative;
        }
        .close-btn {
            position: absolute;
            top: 15px;
            right: 15px;
            font-size: 20px;
            color: #888;
            cursor: pointer;
            background: none;
            border: none;
            padding: 5px;
        }
        .error-icon {
            color: #e74c3c;
            font-size: 50px;
            margin: 20px 0;
        }
        .error-message {
            color: #555;
            margin: 20px 0;
            line-height: 1.5;
        }
    </style>
</head>
<body>
    <div class="popup-container">
        <button class="close-btn" onclick="handleClose()">
            <i class="fas fa-times"></i>
        </button>
        <div class="error-icon">
            <i class="fas fa-exclamation-circle"></i>
        </div>
        <h2>Error Occurred</h2>
        <p class="error-message">
            <%= request.getAttribute("errorMessage") != null ? 
                request.getAttribute("errorMessage") : "" %>
        </p>
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