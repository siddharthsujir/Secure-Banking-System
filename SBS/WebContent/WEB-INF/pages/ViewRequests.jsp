<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Merchant Requests</title>
</head>
<body>

  <form action="extReqConfirm" method='POST'>
  You have request from  Merchants. The bank merchant can make transactions on your behalf, if you accept.<br><br>So please choose the option )Select
<select id="merchantList" name="merchantList">
  <c:forEach var="r" items="${reqList}" >
    <option value="${r.merchant}">${r.merchant}</option>.<br><br> 
     </c:forEach>
 </select> 
 <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
 <input type="hidden" name="user"
			value="${user}" />  
	<input type="submit" value="Approve" >
	<input type="submit" value="reject" onclick="form.action='extReqReject';">
			
			
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