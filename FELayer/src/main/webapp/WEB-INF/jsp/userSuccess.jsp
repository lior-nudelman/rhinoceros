<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<h2>Rhino Email service registration confirmation</h2>

<table>
<tr>
<td>UserName :</td><td>${userReg.userName}</td>
</tr>
<tr>
<td>Address :</td><td>${userReg.address}</td>
</tr>

</table>

<form:form method="POST" commandName="userReg" action="hello.do">
<tr>
<td colspan="3"><input type="submit" value="OK"/></td>
</tr>
</form:form>

</body>
</html>