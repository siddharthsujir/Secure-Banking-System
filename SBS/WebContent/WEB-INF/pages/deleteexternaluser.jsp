<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<body>

  
<h1> <center> Remove Customers</center> </h1>

<p> Bank of Tempe 
  <form action="removeexternaluser"  method='POST'>
<p>
 External User:<select name="customer">
  					<c:forEach var="user" items="${userList}">
    					<option value="${user.username}">${user.username}</option>
   					</c:forEach>
 			   </select> 
  <p>
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