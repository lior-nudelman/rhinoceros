<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<style>
.error {
	color: #ff0000;
}
.errorblock{
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding:8px;
	margin:16px;
}

</style>
</head>

<body>
<h2>Rhino Email service registration</h2>

<form:form method="POST" commandName="userReg" action="userReg.do">

<form:errors path="*" cssClass="errorblock" element="div"/>

<table>
<tr>
<td>UserName : </td>
<td><form:input path="userName" /></td>
<td><form:errors path="userName" cssClass="error" /></td>
</tr>
<tr>
<td>Password : </td>
<td><form:password path="password" /></td>
<td><form:errors path="password" cssClass="error" /></td>
</tr>
<tr>
<td>Confirm Password : </td>
<td><form:password path="confirmPassword" /></td>
<td><form:errors path="confirmPassword" cssClass="error" /></td>
</tr>
<tr>
<td>Email Address : </td>
<td><form:input path="address" /></td>
<td><form:errors path="address" cssClass="error" /></td>
</tr>
<tr>
<td>Email Password : </td>
<td><form:password path="emailPassword" /></td>
<td><form:errors path="emailPassword" cssClass="error" /></td>
</tr>

<form:hidden path="secretValue" />

<tr>
<td colspan="3"><input type="submit" /></td>
</tr>
</table>
</form:form>

</body>
</html>