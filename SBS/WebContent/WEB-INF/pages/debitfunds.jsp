<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>Debit Funds</h1>
	<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
	<form action="updatedebit" method = "post">
		<div>
			Account Type: <select name = "accounttype">
				<option value="Checking">Checking</option>
				<option value="Savings">Savings</option>
			</select>
		</div>
		<div>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		</div>
		<div>
			Amount: <input type="text" name="debitamount" value="0.00">
			
		</div>
		<div>
			Description: <input type="text" name="description">
			<input type="submit" value="Submit">
		</div>
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