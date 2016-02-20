<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
</head>
<body>
	<c:if test="${not empty error}">
			<div class="error">${error}</div>
	</c:if>
	
	<form action="resetpassword" method="post">
	Username:<br>
	<input type="text" name="username" id="username"><br>
	OTP:<br>
	<input type="text" name="otp" id="otp"><br>
	Password:<br>
	<input type="password" name="password" id="password"><br>
	Confirm Password:<br>
	<input type="password" name="confirmpassword" id="confirmpassword"><br>
	<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	<input type="submit" value="Submit">
	</form>
<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form name="login" action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>

<a href="javascript:formSubmit()"> Logout</a>
</body>
</html>