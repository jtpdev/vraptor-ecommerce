<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Email Sent - WineStore</title>
	<%@include file="../common/scripts.jsp"%>
</head>
<body>
	<%@include file="../common/header.jsp"%>
	<div class="email col-sm-12 col-xs-12 col-lg-12 col-xg-12">
		<div class="col-sm-12 col-xs-12 col-lg-12 col-xg-12">
			<h4>
				${done}
			</h4>
		</div>
		<div class="col-sm-12 col-xs-12 col-lg-12 col-xg-12">
			<p>
				${email}
			</p>
		</div>
	</div>
	<%@include file="../common/footer.jsp"%>
</body>
</html>