<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<body>
	<h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>

	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			Welcome : ${pageContext.request.userPrincipal.name} | <a
				href="javascript:formSubmit()"> Logout</a>
		</h2>
	</c:if>
	<form action="CreateRegUser" method="POST">
	 <input type="submit" value="Create Bank Employee">
  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<form action="UpdateRegUser" method="POST">
	 <input type="submit" value="Update Bank Employee">
  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form> 
	<form action="DeleteRegUser" method="POST">
	 <input type="submit" value="Delete Bank Employee">
  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	
	<form action="NewTimeOutEmployee" method="POST">	
    <input type="submit" value="Request PII Authorization">
  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
    </form>	
	
	
</body>
</html>