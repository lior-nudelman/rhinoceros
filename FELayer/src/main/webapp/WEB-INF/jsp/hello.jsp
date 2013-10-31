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
<img src="<c:url value="/resources/images/logoSmall.png"/>">
	<h3>${message}</h3>
	
	
	<table class="table table-striped">
		<tr>
			<th>From</th>
			<th>Date</th>
			<th>Amount</th>
			<th>Type</th>
			<th>receipt #</th>
		</tr>
		<c:forEach items="${report}" var="report" varStatus="status">
			<tr>
				<td>${report.from}</td>
				<td>${report.formatedDate}</td>
				<td>${report.amount}</td>
				<td>${report.type}</td>
				<td>${report.duplicationCounter}</td>
			</tr>
		</c:forEach>
		<tr>
			<td>Total: ${total}</td>
		</tr>
	</table>
</body>
</html>
