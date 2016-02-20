<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<body>
  <h1> ${title}</h1>
  <form action="submitauthrequest"  method='GET'>
  
<h1> <center> Request Authorization from the user</center> </h1>

<sec:authentication var="principal" property="principal" />
Welcome, ${principal.username}
<select name="employee">
		<option value=${principal.username}>${principal.username}</option>
</select>


<p>
 External User:<select name="customer">
  					<c:forEach var="user" items="${userlist}">
    					<option value="${user.username}">${user.username}</option>
   					</c:forEach>
 			   </select> 
  <p>
  <input type="submit" value="Submit">
  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
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

<a href="javascript:formSubmit()"> Logout</a>> 
</body>
</html>