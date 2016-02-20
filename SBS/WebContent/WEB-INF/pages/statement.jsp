<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <script type="text/javascript">
        function doCSV(){
            var table=document.getElementById("t01").innerHTML ;
            var data=table.replace(/<thead>/g,' ').replace(/<\/thead>/g,'').replace(/<tbody>/g,' ').replace(/<\/tbody>/g,'').replace(/<tr>/g,' ').replace(/<\/tr>/g,'\r\n').replace(/<th>/g,' ')
                    .replace(/<\/th>/g,';').replace(/<td>/g,' ').replace(/<\/td>/g,';').replace(/\t/g,'').replace(/\n/g,'');
            var myLink =document.createElement('a');
            myLink.download="csvname.csv";
            myLink.href="data:application/csv,"+escape(data);
            myLink.click();

            alert(data);
/*
            var table2=document.getElementById("t02").innerHTML ;
            var data2=table2.replace(/<thead>/g,' ').replace(/<\/thead>/g,'').replace(/<tbody>/g,' ').replace(/<\/tbody>/g,'').replace(/<tr>/g,' ').replace(/<\/tr>/g,'\r\n').replace(/<th>/g,' ')
                    .replace(/<\/th>/g,';').replace(/<td>/g,' ').replace(/<\/td>/g,';').replace(/\t/g,'').replace(/\n/g,'');
            var myLink2 =document.createElement('a');
            myLink2.download="csvname2.csv";
            myLink2.href="data:application/csv,"+escape(data2);
            myLink2.click();

            alert(data2);

            var table3=document.getElementById("t03").innerHTML ;
            var data3=table3.replace(/<thead>/g,' ').replace(/<\/thead>/g,'').replace(/<tbody>/g,' ').replace(/<\/tbody>/g,'').replace(/<tr>/g,' ').replace(/<\/tr>/g,'\r\n').replace(/<th>/g,' ')
                    .replace(/<\/th>/g,';').replace(/<td>/g,' ').replace(/<\/td>/g,';').replace(/\t/g,'').replace(/\n/g,'');
            var myLink3 =document.createElement('a');
            myLink3.download="csvname3.csv";
            myLink3.href="data:application/csv,"+escape(data3);
            myLink3.click();

            alert(data3);

            var table4=document.getElementById("t04").innerHTML ;
            var data4=table4.replace(/<thead>/g,' ').replace(/<\/thead>/g,'').replace(/<tbody>/g,' ').replace(/<\/tbody>/g,'').replace(/<tr>/g,' ').replace(/<\/tr>/g,'\r\n').replace(/<th>/g,' ')
                    .replace(/<\/th>/g,';').replace(/<td>/g,' ').replace(/<\/td>/g,';').replace(/\t/g,'').replace(/\n/g,'');
            var myLink4 =document.createElement('a');
            myLink4.download="csvname4.csv";
            myLink4.href="data:application/csv,"+escape(data4);
            myLink4.click();

            alert(data4);

            var table5=document.getElementById("t05").innerHTML ;
            var data5=table5.replace(/<thead>/g,' ').replace(/<\/thead>/g,'').replace(/<tbody>/g,' ').replace(/<\/tbody>/g,'').replace(/<tr>/g,' ').replace(/<\/tr>/g,'\r\n').replace(/<th>/g,' ')
                    .replace(/<\/th>/g,';').replace(/<td>/g,' ').replace(/<\/td>/g,';').replace(/\t/g,'').replace(/\n/g,'');
            var myLink5 =document.createElement('a');
            myLink5.download="csvname5.csv";
            myLink5.href="data:application/csv,"+escape(data5);
            myLink5.click();

            alert(data5);
*/
        }
    </script>
</head>
<body>
<h1> ${title}</h1>
<h1> <center> ViewStatement</center> </h1>
<sec:authentication var="principal" property="principal" />
Welcome, ${principal.username}
<br/>
<form action="user" name="viewStatement" method='POST'>

    <c:out value="${acc}"/> account details
    <c:out value="${chAmount}"/>

    <table id="t01">
        <tr>
            <th>Account Number</th>
            <th><c:out value="${accNo}"/></th>
        </tr>
    </table>
    <table id="t02" style="width:100%" border="1">
        <caption>All Transactions</caption>
        <tr>

            <td> Transaction ID </td>
            <td> Date </td>
            <td> From Account </td>
            <td> To Account </td>
            <td> Status </td>
            <td> Description </td>
            <td> Amount </td>
        </tr>
        <c:forEach items="${transactionlist}" var="translist">
            <tr>
                <td>${translist.transaction_id}</td>
                <td>${translist.transactiontime}</td>
                <td>${translist.fromAccount}</td>
                <td>${translist.toAccount}</td>
                <td>${translist.transactionStatus}</td>
                <td>${translist.description}</td>
                <td>${translist.amount}</td>
            </tr>
        </c:forEach>
    </table>
    <br/><br/>
    <table id="t03" style="width:100%" border="1">
        <caption>All Debits</caption>
        <tr>

            <td> Transaction ID </td>
            <td> Date </td>
            <td> To Account </td>
            <td> Status </td>
            <td> Description </td>
            <td> Amount </td>
        </tr>
        <c:forEach items="${debitlist}" var="translist">
            <tr>
                <td>${translist.transaction_id}</td>
                <td>${translist.transactiontime}</td>
                <td>${translist.toAccount}</td>
                <td>${translist.transactionStatus}</td>
                <td>${translist.description}</td>
                <td>${translist.amount}</td>
            </tr>
        </c:forEach>
    </table><br/><br/>
    <table id="04" style="width:100%" border="1">
        <caption>All Credits</caption>
        <tr>

            <td> Transaction ID </td>
            <td> Date </td>
            <td> From Account </td>
            <td> Status </td>
            <td> Description </td>
            <td> Amount </td>
        </tr>
        <c:forEach items="${creditlist}" var="translist">
            <tr>
                <td>${translist.transaction_id}</td>
                <td>${translist.transactiontime}</td>
                <td>${translist.fromAccount}</td>
                <td>${translist.transactionStatus}</td>
                <td>${translist.description}</td>
                <td>${translist.amount}</td>
            </tr>
        </c:forEach>
    </table> <br/><br/>
    <table id="t05">
        <tr>
            <th>Current Balance</th>
            <th><c:out value="${Balance}"/></th>
        </tr>
    </table><br/><br/>
    <button onclick="doCSV()">
        Export to CSV
    </button>
    <!-- Select transaction: <select name="transaction">
                <c:forEach var="trans" items="${transactionlist}">
                         <option value="${trans.transaction_id}">${trans.transaction_id}</option>
                </c:forEach>
    </select>
     <p>
     Do you want to approve this transaction?
     <input type="submit" name="decision" value="Approve">
     <input type="submit" name="decision" value="Reject">

     <p> -->
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />
</form>
<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
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