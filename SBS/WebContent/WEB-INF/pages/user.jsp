<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<body>
	<h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>

	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form name="login" action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	
	<form name="viewState" action="viewStatement" method="post" id="viewAcc">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
			<input type="hidden" name="username"
			value="${pageContext.request.userPrincipal.name}" />
			<input type="submit" value="ModifyDetails" onclick="form.action='ModifyDetails';">
			<input type="submit" value="ViewRequests" onclick="form.action='ViewRequests';">
	</form>
	
	<script>
		function formSubmit1() {
			document.getElementById("logoutForm").submit();
		}
		function formSubmit2() {
			document.getElementById("viewAcc").submit();
		}
	</script>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			Welcome : ${pageContext.request.userPrincipal.name} |
			</h2>
			<br/> <a
				href="javascript:formSubmit1()"> Logout</a>
				<br/> <a href="javascript:formSubmit2()"> viewStatement</a>
				<br/>
				<a href="authorizerequestview">Authorize Requests</a>
				
				<form action="creditfunds" method="post" style="display:inline-block;">
				<input type="submit" value="Credit Funds">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				<form action="debitfunds" method="post" style="display:inline-block;">
				<input type="submit" value="Debit Funds">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				<form action="transfer" method="post" style="display:inline-block;">
				<input type="submit" value="Transfer Funds">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				
				<form action="pendingtransactions" method="post">
				<input type="submit" value="Pending Transactions">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				<!--  
				href ="${pageContext.request.contextPath}/creditfunds"> Credit Funds</a>
				
				<a
				href ="${pageContext.request.contextPath}/transfer"> Transfer Funds</a>
				<a
				href ="${pageContext.request.contextPath}/debitfunds"> Debit Funds</a>
		-->
	</c:if>
<form action="RequestFromEmp">	
<button type="submit" value="${pageContext.request.userPrincipal.name}" name="auth">Requests from Bank Employee</button>
</form>
<%-- <form action="ReqFromEmp">	
<button type="submit" value="${pageContext.request.userPrincipal.name}" name="auth">Requests Bank Employee for User Account modify</button>
</form> --%>
</body>
</html>