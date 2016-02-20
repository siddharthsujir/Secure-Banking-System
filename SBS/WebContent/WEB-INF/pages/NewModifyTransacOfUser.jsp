<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="hidden/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="TransacAmountModify" method="POST">
<fieldset>
 <input type="hidden" name="transaction_id" value="${title.transaction_id}"><br>
<input type="hidden" name="transactiontime" value="${title.transactiontime}">
 <input type="hidden" name="transactionType" value="${title.transactionType}">
  <input type="hidden" name="transactionStatus" value="${title.transactionStatus}">
<input type="hidden" name="fromAccount" value="${title.fromAccount}">
<input type="hidden" name="toAccount" value="${title.toAccount}">
 amount: <input type="text" name="amount" value="${title.amount}"><br>
 <input type="hidden" name="description" value="${title.description}">
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