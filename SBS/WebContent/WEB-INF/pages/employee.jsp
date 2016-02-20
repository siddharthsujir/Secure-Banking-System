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

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			Welcome : ${pageContext.request.userPrincipal.name} | <a
				href="javascript:formSubmit()"> Logout</a>
		</h2>
	</c:if>

<form action="RequestForAuth" method="POST">
	<input type="submit" value="Request User for Transaction Authorization ">
  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
    
   <%--  <form action="RequestForUserAuth">	
    <button type="submit" value="${pageContext.request.userPrincipal.name}" name="auth">Request User for Account Authorization </button>
    </form> --%>
    
    
    <form action="RequestAcceptedByUser">	
    <button type="submit" value="${pageContext.request.userPrincipal.name}" name="auth">Requests Accepted By User</button>
    </form>
    
    <%-- <form action="RequestAccByUser">	
    <button type="submit" value="${pageContext.request.userPrincipal.name}" name="auth">Requests Accepted By User For Account</button>
    </form>
    
    <form action="TimeOutEmployee">	
    <button type="submit" value="submit" >Request PII Authorization</button>
    </form> --%>
    
	
</body>
</html>