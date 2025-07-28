<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<html>
<head>
<title>Error Occurred</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/ErrorMessege.css"
>
<!-- Font Awesome for icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
>
</head>
<body>
	<div class="container">
		<div class="error-icon">
			<i class="fas fa-exclamation-circle"></i>
		</div>
		<h2>Error Occurred</h2>

		<!-- Dynamic message from servlet -->
		<%
		if (request.getAttribute("errorMessage") != null) {
		%>
		<p class="error-message"><%=request.getAttribute("errorMessage")%></p>
		<%
		}
		%>

		<a href="${pageContext.request.contextPath}${buttonPath}" class="btn">${buttonValue}
			${buttonAction} </a>
	</div>
</body>
</html>