<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/LoginUser.css"
>
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate"
>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<script>
	if (window.history.replaceState) {
		window.history.replaceState(null, null, window.location.href);
	}
</script>
<!-- Other head elements -->
</head>
<body>
	<div class="login-container">
		<div class="login-header">
			<h2>Login</h2>
		</div>
		<%-- Display error message if authentication fails --%>
		<%
		if (request.getParameter("error") != null) {
		%>
		<div class="error-message">Invalid username or password. Please
			try again.</div>
		<%
		}
		%>
		<form action="${pageContext.request.contextPath}/login" method="POST">
			<%
			if (request.getAttribute("error") != null) {
			%>
			<div class="error-message">
				<%=request.getAttribute("error")%>
			</div>
			<%
			}
			%>
			<div class="form-group">
				<label for="username">Username</label> <input type="text"
					id="username" name="username"
				>
			</div>
			<div class="form-group">
				<label for="password">Password</label> <input type="password"
					id="password" name="password"
				>
			</div>
			<button type="submit" class="login-btn">Login</button>
		</form>
		<div class="login-footer">
			<p>
				<a href="${pageContext.request.contextPath}/views/CreateUser.jsp">Register
					here</a>
			</p>
		</div>
	</div>
</body>
</html>