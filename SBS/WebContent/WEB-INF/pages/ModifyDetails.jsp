<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
  <head>
  <script type="text/javascript">

  function validateEmail()
  {
  var emailID = document.reguser.email.value;
  atpos = emailID.indexOf("@");
  dotpos = emailID.lastIndexOf(".");

  if (atpos < 1 || ( dotpos - atpos < 2 ))
  {
 <!-- alert("Please enter correct email ID"); -->
  document.reguser.email.focus() ;
  return false;
  }
  return( true );
  }

    function validate_info(){


  if( document.reguser.fname.value == "" ||
  document.reguser.fname.value.length>20
  )
  {
  alert( "Please provide your first name!" );
  document.reguser.fname.focus() ;
  return  false;
  }

  if( document.reguser.lname.value == "" ||
  document.reguser.lname.value.length>20
  )
  {
  alert( "Please provide your last name!" );
  document.reguser.lname.focus() ;
  return  false;
  }



  if( document.reguser.designation.value == "selecttype" )
  {
  alert( "Please provide user type" );
  return false;
  }

  if( document.reguser.address.value == "" ||
  document.reguser.address.value.length >35
  )
  {
  alert( "Please provide your address" );
  document.reguser.address.focus() ;
  return false;
  }

  if( document.reguser.phno.value == "" ||
  isNaN( document.reguser.phno.value ) ||
  document.reguser.phno.value.length != 10 )
  {
  alert( "Please provide the phone number in the format ##########" );
  document.reguser.phno.focus() ;
  return false;
  }

  var bool = validateEmail();
  var bool2=false;

  if( document.reguser.email.value == "" || bool == false )
  {
  bool2 = true;
  alert( "Please provide valid  Email!" );
  document.reguser.email.focus() ;
  return false;
  }

  if (bool2 == false)
  {
  alert("Form validated successfully");
  }
  return true;

  }


  </script>
  </head>
<br>
  <h1>Bank of Tempe</h1>
  <br>
  <br>


  <form action="ModifyDetailsUser" name="reguser"  method='POST' onsubmit="return validate_info()" >
  <fieldset>
  <c:out value="${message}"/>
  <legend>Personal information of <c:out value="${customer.username}"/></legend>
  <input type="hidden" name="username" value="${customer.username}">
  <input type="hidden" name="firstName" value="${customer.firstName}">
  <input type="hidden" name="lastName" value="${customer.lastName}">
  <input type="hidden" name="role" value="${customer.role}">
   <input type="hidden" name="customerId" value="${customer.customerId}">
 
  Address:      <input type="text" name="address" value="${customer.address}"><br>
  Phone Number: <input type="text" name="phoneNumber" value="${customer.phoneNumber}"><br>
  Email Id:     <input type="text" name="emailId" value="${customer.emailId}"><br>
  
  <input type="submit" value="Submit">

  <!--<input  name="fname" type="text" pattern="[a-zA-Z]={4,}" title="Minimum 5 letters" required />-->

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