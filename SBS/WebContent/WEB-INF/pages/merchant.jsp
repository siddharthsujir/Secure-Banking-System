<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<body>
	<h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>

	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
	
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
			<input type="hidden" name="merchantname"
			value="${pageContext.request.userPrincipal.name}" />
			<input type="submit" value="request payment" onclick="form.action='requestPayment';">
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
			Welcome : ${pageContext.request.userPrincipal.name} |</h2>
			
			<br/> <a
				href="javascript:formSubmit1()"> Logout</a>
				<br/> <a
				href="javascript:formSubmit2()"> viewStatement</a>
				<br/>
				<a href="authorizerequestview">Authorize Requests</a>
				
				
				<form action="merchanttransfer" method="post" style="display:inline-block;">
				<input type="submit" value="Merchant Transfer Funds">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				
				<form action="merchanttransferfund" method="post" style="display:inline-block;">
				<input type="submit" value="Merchant Payment">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				
				<form action="creditfunds" method="post" style="display:inline-block;">
				<input type="submit" value="Credit Funds">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				<form action="debitfunds" method="post" style="display:inline-block;">
				<input type="submit" value="Debit Funds">
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
<form action="ReqFromEmp">	
<button type="submit" value="${pageContext.request.userPrincipal.name}" name="auth">Requests Bank Employee for User Account modify</button>
</form>
</body>
</html>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			Welcome : ${pageContext.request.userPrincipal.name} | <a
				href="javascript:formSubmit()"> Logout</a>
		</h2>
	</c:if>
     
     <a href="UserAuthEmp">Authorize for Employee</a>
     <a href = "${pageContext.request.contextPath}/transfer">Transfer</a>
</body>
</html>