<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<body>
  <h1> ${title}</h1>
  <form action="submittransactionapproval"  method='POST'>
  
<h1> <center> Approve Critical Transactions</center> </h1>

<sec:authentication var="principal" property="principal" />
Welcome, ${principal.username}

<p>
The Following transactions need your approval!
<p>
 <table border="1">
 <tr>
 		<td> Transaction ID </td>
 		<td> Transaction Type </td>
 		<td> Amount </td>
 </tr>
<c:forEach items="${transactionlist}" var="translist">
    <tr>
        <td>${translist.transaction_id}</td>
        <td>${translist.transactionType}</td>
        <td>${translist.amount}</td>
    </tr>
</c:forEach>
</table>
<br/>
 Select transaction: <select name="transaction">
 			<c:forEach var="trans" items="${transactionlist}">
  					<option value="${trans.transaction_id}">${trans.transaction_id}</option>
 			</c:forEach>
 </select>  
  <p>
  Do you want to authorize this transaction?
  <input type="submit" name="decision" value="Yes">
  <input type="submit" name="decision" value="No">

  <p>
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