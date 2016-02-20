<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
  <script type="text/javascript">

  function validate_transaction_info(){

  if( document.TransactionPage.from.value == "selecttype" )
  {
  alert( "Please provide account type" );
  return false;
  }

  if( document.TransactionPage.recepientname.value == ""  )
  {
  alert( "Please provide recepient id" );
  document.TransactionPage.recepientname.focus() ;
  return false;
  }

  if( document.TransactionPage.amount.value == "" ||
  isNaN( document.TransactionPage.amount.value ) ||
  document.TransactionPage.amount.value >2000 ||
  document.TransactionPage.amount.value < 0
  )
  {
  alert( "You can  provide the amount upto 2000 $" );
  document.TransactionPage.amount.focus() ;
  return false;
  }

  alert("Transaction Details verified");
  return true;
  }

  </script>
<body>
  <br>
  <h1>Bank of Tempe</h1>
  <br>
  <br>
  <form name="TransactionPage"  action="TransactionPage.jsp"  method='POST' onsubmit="return validate_transaction_info()">
  <fieldset>
  <legend>Transaction information:</legend>
  From:               <select name="from">
  <option value="selecttype">Transfer money from</option>
  <option value="Account1">Checking Account</option>
  <option value="Account2">Savings Account</option></select>
  <br>
  To :                <input type="text" name="recepientname" ><br>
  Amount:             $ <input type="text" name="amount"><br>
  Message(Optional):  <input type="text" name="message"><br>
  
  <input type="submit" value="Submit">
  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
  </fieldset>
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