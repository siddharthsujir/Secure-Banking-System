<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
  <head>
  <script type="text/javascript">

   </script>
  </head>
<br>
  <h1>Bank of Tempe</h1>
  <br>
  <br>

<h1> System Log <h1>
<p>
 <table border="1">
 <tr>
 		<td> User Name </td>
 		<td> Description</td>
 		<td> TimeStamp </td>
 </tr>
<c:forEach items="${systemlog}" var="systemlog">
    <tr>
        <td>${systemlog.username}</td>
        <td>${systemlog.description}</td>
        <td>${systemlog.timestamp}</td>
    </tr>
</c:forEach>
</table>
<br/>