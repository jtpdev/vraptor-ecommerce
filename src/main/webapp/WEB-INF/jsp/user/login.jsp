<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cart - WineStore</title>
<%@include file="../common/scripts.jsp"%>
</head>
<body>
	<%@include file="../common/header.jsp"%>
	<div class="form-login col-sm-12 col-xs-12 col-lg-12 col-xg-12">
		<div id="login-message">
			${loginmessage}
		</div>
		<h2>Login:</h2>
		<form action="<c:url value="/dologin"/>"
			method="POST">
<!-- 			<fieldset> -->
<!-- 				<legend>Login</legend> -->
				<div class="col-sm-12 col-xs-12 col-lg-12 col-xg-12">
					<div class="col-sm-6 col-xs-12 col-lg-6 col-xg-6">
						<label	for="email">
							Login (email):
						</label>
					</div>
					<div class="col-sm-6 col-xs-12 col-lg-6 col-xg-6">
						<input id="email" class="required"
							type="text" name="user.email" value="${user.email}" />
					</div>
				</div>
				<div class="col-sm-12 col-xs-12 col-lg-12 col-xg-12">
					<div class="col-sm-6 col-xs-12 col-lg-6 col-xg-6">
						<label for="password">
							Password:
						</label>
					</div>
					<div class="col-sm-6 col-xs-12 col-lg-6 col-xg-6">
						<input id="password" class="required"
							type="password" name="user.password" value="${user.password}"/>
					</div>
				</div>
				<button type="submit">Login</button>
<!-- 			</fieldset> -->
		</form>
		<div class="col-sm-12 col-xs-12 col-lg-12 col-xg-12">
			<a href="<c:url value="/user/'Create now!'"/>">Create an account!</a>
		</div>
	</div>

	<%@include file="../common/footer.jsp"%>
</body>
</html>