<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<body>
  <h1> Send Request</h1>
  <form action="extReqSent"  method='POST'>
  <select id="userList" name="userList">
  <c:forEach var="u" items="${userList}" >
    <option value="${u.username}">${u.username}</option>.<br><br> 
     </c:forEach>
 </select> 
  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
			<input type="hidden" name="merchantname"
			value="${merchant}" />  
	<input type="submit" value="send" >
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