<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>One's Account - We tell you how much you pay</title>
<meta name="description"
	content="One's Account - We tell you how much you pay">


<link rel="stylesheet"
	href="<c:url value="resources/css/normalize.css"/>" />
<link rel="stylesheet" href="<c:url value="resources/css/main.css"/>" />

<link rel="stylesheet"
	href="<c:url value="resources/css/bootstrap-responsive.min.css"/>" />
<link rel="stylesheet"
	href="<c:url value="resources/css/bootstrap.min.css"/>" />
<link rel="stylesheet" href="<c:url value="resources/css/style.css"/>" />

<script src="<c:url value="resources/js/jquery-1.10.2.min.js" />"></script>
<script src="<c:url value="resources/js/modernizr-2.6.2.min.js"/>"></script>
</head>

<body>



<form:form method="POST" commandName="userReg" action="userReg.do">

<form:errors path="*" cssClass="errorblock" element="div"/>

<table>
<img src="<c:url value="/resources/images/logoSmall.png"/>">

<h2>Rhino Email service registration</h2>
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