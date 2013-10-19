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
<form name="login_form" action="<c:url value='j_spring_security_check'/>" method="POST">
<img src="<c:url value="/resources/images/logoSmall.png"/>">
<div class="row-fluid">
<div class="span4">
<h1>Welcome !</h1>
<fieldset id="user-details"><label for="j_username">User Name : <span class="required">*</span></label> <input id="j_username" type="text" name="j_username" value="" /> <label for="password">Password: <span class="required">*</span> </label> <input id="j_password" type="password" name="j_password" value="" />
<input class="submit btn" type="submit" name="submit" value="Login" /></fieldset>
</div>
<div class="span7 offset1 well about-well">
<h2>Sign Up Now !</h2>
<ul class="unstyled" style="text-align: justify;">
	<li><em class="icon-tags"></em> <small> Find out how much you pay  !</small></li>
	<li><em class="icon-tags"></em> <small> Download our Mobile App and help us in getting the latest bank debit !</small></li>
	<li><em class="icon-tags"></em> <small> Create your own bank report ! </small></li>
	<li><em class="icon-tags"></em> <small> Find out how much you can save over the month before only! </small></li>
</ul>
<a class="btn" href="userReg.do">Register »</a>
</div>
</div>
</form>
</body>
</html>