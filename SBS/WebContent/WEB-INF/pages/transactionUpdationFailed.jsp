<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>Transaction Updating Failed</h1>

<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		  <h2>Reason: ${reason} <br/></h2>
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