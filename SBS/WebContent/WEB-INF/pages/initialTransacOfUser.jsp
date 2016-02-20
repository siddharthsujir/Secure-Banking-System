<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<p>Welcome to ${person} Transactions Section!!!!</p> 
  <form action="viewTransactionsofUser" method="POST">
  <input type="submit" value="View" name="tran">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  </form><br>
  <form action="ModifyTransactionsofUser" method="POST" >
   <input type="submit" value="Modify" name="tran">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  </form><br>
  <form action="DeleteTransactionsofUser" method="POST">
   <input type="submit" value="Delete" name="tran">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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