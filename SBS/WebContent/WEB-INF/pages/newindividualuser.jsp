<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<body>
  <h1> ${title}</h1>
  <form action="newindividualuser"  method='POST'>
  First name:   <input type="text" name="firstName"><br>
  Last name:    <input type="text" name="lastName"><br>
  Address:      <input type="text" name="address"><br>
  Role:			<select name="role">
  					<option value="ROLE_USER">Individual User</option>
  					<option value="ROLE_MERCHANT">Merchant</option>
				</select>
  Phone Number: <input type="text" name="phoneNumber"><br>
  Email Id: <input type="text" name="emailId"><br>
  
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