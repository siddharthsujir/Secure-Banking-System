<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
<sec:authentication var="principal" property="principal" />
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
		Welcome, ${principal.username}| <a
				href="javascript:formSubmit()"> Logout</a>
				
				<input type="hidden" name="currentuser"
			value="${pageContext.request.userPrincipal.name}" />
		</h2>
	</c:if>
 <a href="createindividualuser">Create New Customer</a><p>
 <a href="deleteexternaluser">Delete Customer</a><p>
 <a href="updatecustomer">Update Customer</a><p>
 <a href="requestauthorization">Request Authorization to view transactions</a><p>
 <a href="viewauthorizedtransactions">View Authorized Transactions</a><p>
 <a href="authorizecriticaltransactions">Authorize Critical Transactions</a><p>
 <a href="authorizemodifyrequest">Authorize Modify Request</a><p>
 
</body>
</html>