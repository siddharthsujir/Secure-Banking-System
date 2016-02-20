<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<body>
  <h1> ${title}</h1>
  <form action="authorizerequest"  method='GET'>
  
<h1> <center> Provide your Authorization</center> </h1>

<sec:authentication var="principal" property="principal" />
Welcome, ${principal.username}

<p> The following bank employees have requested your authorization to view your transactions.

<p>
 Select one employee: <select name="employee">
 			<c:forEach var="emp" items="${empList}">
  					<option value="${emp.employeename}">${emp.employeename}</option>
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