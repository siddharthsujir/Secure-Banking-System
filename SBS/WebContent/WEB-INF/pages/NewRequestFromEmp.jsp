<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<c:forEach var="per" items="${person}">
    <option value="${per.extuser}">${per.extuser}</option> !!!  you have request from  Bank employee <option value="${per.intuser}">${per.intuser}</option>.<br><br> 
    The bank employee will get access to your transactions if you accept.<br><br>So please choose the option 
  <form action="customerAccept">
    <button type="submit" value="${pageContext.request.userPrincipal.name}" name="auth">Accept</button>
  </form>
  <form action="customerReject">
    <button type="submit" value="submit">Reject</button> 
  </form>
</c:forEach>
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