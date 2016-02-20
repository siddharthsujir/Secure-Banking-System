<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>Transfer Funds</h1>
	<c:if test="${not empty error}">
			<div class="error">${error}</div>
	</c:if>
	<form  action="transferfunds" method="post" >
		<div>
			From Account Of:<h1>${pageContext.request.userPrincipal.name}</h1>
		</div>
		<div>
			
			From Account Type: <select name="fromaccounttype">
				<option value="Checking">Checking</option>
				<option value="Savings">Savings</option> 
			</select>
		</div>
		<div>
			To Account: <select name="toaccountnum">
				<option disabled selected>-- select an option --</option>
				<c:forEach items="${regusers}" var="user">
					<option value="${user}">${user}</option>
				</c:forEach>
			</select>
		</div>
		
		<div>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		</div>
		<div>
			Description: <input type="text" name="description" ></div>
		<div>
			Amount: <input type="text" name="transferamount" value="0.00" >
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