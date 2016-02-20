<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
  <head>
  <script type="text/javascript">

   </script>
  </head>
  <body>
<br>
  <h1>Bank of Tempe</h1>
  <br>
  <br>

<h1> Customer Detail <h1>
<p>
 <table border="1">
 <tr>
 		<td> Customer ID </td>
 		<td> User Name </td>
 		<td> First Name </td>
 		<td> Last Name </td>
 		<td> Address </td>
 		<td> Phone Number </td>
 		<td> Email Id </td>
 		<td> Role </td>
 </tr>
<c:forEach items="${customerlist}" var="customerlist">
    <tr>
        <td>${customerlist.customerId}</td>
        <td>${customerlist.username}</td>
        <td>${customerlist.firstName}</td>
        <td>${customerlist.lastName}</td>
        <td>${customerlist.address}</td>
        <td>${customerlist.phoneNumber}</td>
        <td>${customerlist.emailId}</td>
        <td>${customerlist.role}</td>
    </tr>
</c:forEach>
</table>
<br/>
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