<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <h1> ${title}</h1>
  <h1> <center> Approve Pending Transactions</center> </h1>
  <sec:authentication var="principal" property="principal" />
Welcome, ${principal.username}

<p>
The Following transactions can be modified
<p>
  <form action="modiTransactions"  method='POST'>
  <select name="transaclist">
<c:forEach items="${transactionlist}" var="translist">
     <option value="${translist.transaction_id}">${translist.transaction_id}</option>
   </c:forEach> 
   </select>
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

<a href="javascript:formSubmit()"> Logout</a>
</body>
</html>