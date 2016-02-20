<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>

</head>
<body>
  <br>
  <h1>Bank of Tempe Arizona</h1>
  <br>
  <br>
  <form action="NewRegUser"  method='POST'>
  <fieldset>
  <legend>Personal information:</legend>
  First name:   <input type="text" id="fname" name="fname"  ><br>
  Last name:    <input type="text" id="lname" name="lname" ><br>
  Designation:  <select name="desname">
  <option value="Reg">regular employee</option>
  <option value="sysmgr">System Manager</option>
  <option value="admin">Admin</option></select>
  <br>
  Address:      <input type="text" name="address"><br>
  Phone Number: <input type="text" name="Phno" ><br>
  Email Id:     <input type="text" name="email"><br>
  Username: <input type="text" name="username"><br>
  <input type="submit" value="Submit">
  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
</fieldset>
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