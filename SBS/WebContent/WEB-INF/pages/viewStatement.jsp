<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<body>
	
	
	<h1>ViewStatement</h1>
	
	
	<form name="viewState" method="post" id="viewAcc" action="statement">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
			<input type="hidden" name="accType"
			id="accType"/>
		<table id="t02">
  <tr>
  <th>Checking Account .. Balance</th>
  <th><c:out value="${chAmount}"/></th>
  </tr>
  <tr>
    <th>Savings Account .. Balance</th>
  <th><c:out value="${sAmount}"/></th>
  </tr>
  </table>		
	</form>

	<script>
		function formSubmit1() {
			//document.getElementById("viewState").submit();
			document.getElementById("accType").value=1;//checking
			document.getElementById("viewAcc").submit();
		}
		function formSubmit2() {
			document.getElementById("accType").value=2;//savings
			document.getElementById("viewAcc").submit();
		}
	</script>
	
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			Welcome : ${pageContext.request.userPrincipal.name} |
			
			<br/> <a
				href="javascript:formSubmit1()"> CheckingAcc</a>
				<br/> <a
				href="javascript:formSubmit2()"> SavingsAcc</a>
				
				
			
		</h2>
	</c:if>
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