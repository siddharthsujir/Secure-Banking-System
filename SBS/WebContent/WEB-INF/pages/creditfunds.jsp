<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%-- <script type="text/javascript" src="<c:url value='/resources/js/test.js' />"></script> --%>
<!-- <script type="text/javascript">
window.history.forward(1);
</script> -->
<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
	<h1>Credit Funds</h1>
	<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
	<form action="updatecredit" method = "post" onsubmit="validateJS()">
		<div>
			Account Type: <select name = "accounttype">
				<option value="Checking">Checking</option>
				<option value="Savings">Savings</option>
			</select>
		</div>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<div>
			Description: <input type="text" name="description">
			
		</div>
		<div>
			Amount: <input type="text" name="creditamount" value="0.00">
			<!-- <div class="g-recaptcha" data-sitekey="6Le45g8TAAAAAPykFFpwOFFN3EciU_664p1nJqtN"></div> -->
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