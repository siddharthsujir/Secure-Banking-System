<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<body>
  <h1> ${title}</h1>
  <form action="authorizemodification"  method='POST'>
  
<h1> <center> Provide your Authorization for the user to modify</center> </h1>

<sec:authentication var="principal" property="principal" />
Welcome, ${principal.username}

<p> The following customers have requested your authorization to modify their details.

<p>
 Select one Customer: <select name="customer">
 			<c:forEach var="customer" items="${requestlist}">
  					<option value="${customer.username}">${customer.username}</option>
 			</c:forEach>
 </select>  
  <p>
  Do they have your approval?
  <input type="submit" name="decision" value="Yes">
  <input type="submit" name="decision" value="No">
  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
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