<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<body>
  <h1> ${title}</h1>
  <form action="submittransactionapproval"  method='GET'>
  
<h1> <center> Approve Critical Transactions</center> </h1>

<sec:authentication var="principal" property="principal" />
Welcome, ${principal.username}

<p>
The Following transactions need your approval!
<p>
 <table border="1">
 <tr>
		<td> Transaction ID </td>
 		<td> Transaction Time </td>
 		<td> Transaction Type </td>
 		<td> Transaction Status </td>
 		<td> To Account number </td>
 		<td> Amount </td>
 </tr>
<c:forEach items="${transList}" var="transList">
    <tr>
        <td>${transList.transaction_id}</td>
        <td>${transList.transactiontime}</td>
        <td>${transList.transactionType}</td>
        <td>${transList.transactionStatus}</td>
        <td>${transList.toAccount}</td>
        <td>${transList.amount}</td>
    </tr>
</c:forEach>
</table>
<br/>
<p>
Debit Transactions
<p>
 <table border="1">
 <tr>
 		<td> Transaction ID </td>
 		<td> Transaction Time </td>
 		<td> Transaction Type </td>
 		<td> Transaction Status </td>
 		<td> To Account number </td>
 		<td> Amount </td>
 </tr>
<c:forEach items="${debitList}" var="debitList">
    <tr>
        <td>${debitList.transaction_id}</td>
        <td>${debitList.transactiontime}</td>
        <td>${debitList.transactionType}</td>
        <td>${debitList.transactionStatus}</td>
        <td>${debitList.toAccount}</td>
        <td>${debitList.amount}</td>
    </tr>
</c:forEach>
</table>
<br/>
 
<br/>
<p>
Credit Transactions
<p>
 <table border="1">
 <tr>
 		<td> Transaction ID </td>
 		<td> Transaction Time </td>
 		<td> Transaction Type </td>
 		<td> Transaction Status </td>
 		<td> To Account number </td>
 		<td> Amount </td>
 </tr>
<c:forEach items="${creditList}" var="creditList">
    <tr>
        <td>${creditList.transaction_id}</td>
        <td>${creditList.transactiontime}</td>
        <td>${creditList.transactionType}</td>
        <td>${creditList.transactionStatus}</td>
        <td>${creditList.fromAccount}</td>
        <td>${creditList.amount}</td>
    </tr>
</c:forEach>
</table>
<br/>

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