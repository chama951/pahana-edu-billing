<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Process Completed</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/ErrorMessege.css">
</head>
<!-- Font Awesome for icons (optional) -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
	<div class="container">
		<div class="success-icon">
			<i class="fas fa-check-circle"></i>
		</div>
		<h2>Process Completed</h2>

		<!-- Dynamic message from servlet -->
		<%
		if (request.getAttribute("successMessage") != null) {
		%>
		<p class="additional-info"><%=request.getAttribute("successMessage")%></p>
		<%
		}
		%>

		<a href="${pageContext.request.contextPath}${buttonPath}" class="btn">
			Continue </a>
</body>
</html>