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
	<div id="user-form">
			<div id="login-message">
				${usermessage}
			</div>
		<form action="<c:url value="/save"/>"
			method="POST">
<!-- 			<fieldset> -->
				<legend>Create an account</legend>
				<div class="col-sm-12 col-xs-12 col-lg-12 col-xg-12">
					<div class="col-sm-6 col-xs-12 col-lg-6 col-xg-6">
						<label for="nome">
							Name:
						</label>
					</div>
					<div class="col-sm-6 col-xs-12 col-lg-6 col-xg-6">
						<input id="name" class="required"
							type="text" name="user.name" value="${user.name}" />
					</div>
				</div>
				<div class="col-sm-12 col-xs-12 col-lg-12 col-xg-12">
					<div class="col-sm-6 col-xs-12 col-lg-6 col-xg-6">
						<label	for="email">
							Email:
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
							type="password" name="user.password" />
					</div>
				</div>
				<button type="submit">Done</button>
<!-- 			</fieldset> -->
		</form>
	</div>
	<%@include file="../common/footer.jsp"%>
</body>
</html>