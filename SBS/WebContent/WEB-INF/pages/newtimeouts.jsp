<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 Government granted the permission to view the personal information of ${title} !!!!<br>
 <br>
 To view the details please click the button
 <form action="PIIdetails" method="POST">
	<input type="submit" name="username" value="${title}">
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

<a href="javascript:formSubmit()"> Logout</a>
</body>
</html>