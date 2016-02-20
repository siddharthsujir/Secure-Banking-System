<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<body>
  <h1> Customer Update</h1>
  <form action="modifycustomer"  method='POST'>
  Select the user name of the customer to be updated:	
				<select name="customer">
  					<c:forEach var="user" items="${userlist}">
    					<option value="${user.username}">${user.username}</option>
   					</c:forEach>
 			   </select> <br/>
  <input type="submit" value="Submit">
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