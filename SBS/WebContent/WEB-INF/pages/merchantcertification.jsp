<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page session="true"%>
<html>
<body>
	<h1>PKI Certificate Upload</h1>

	<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		
	<form:form method="POST" modelAttribute="fileupload" action="uploadpkimerchant" enctype="multipart/form-data">
		File to upload: <input type="file" name="file" id="file"><br/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="submit" value="Upload">
	</form:form>
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