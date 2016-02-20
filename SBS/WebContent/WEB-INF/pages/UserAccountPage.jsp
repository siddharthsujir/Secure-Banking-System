<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page session="true"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form>
Welcome to ${person} Page<br><br> 
   <button type="submit" value="submit">View</button>
  </form><br>
  <form>
   <button type="submit" value="submit">Modify</button>
  </form><br>
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