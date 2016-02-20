<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>Customer view</h1>

	<button type="button" onclick="location.href = 'creditfunds'">Credit
		Funds</button>
	<button type="button" onclick ="location.href = 'debitfunds'">Debit Funds</button>
	<button type="button" onclick ="location.href = 'transferfunds'">Transfer Funds</button>
	<button type="button">Request Statement</button>
	<button type="button">Request Modification</button>
	<div>${msg}</div>
	<a href="transaction.jsp">Transaction</a>
</body>
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
</html>