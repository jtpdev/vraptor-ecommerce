<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Winestore - ${productVO.name}</title>
	<%@include file="../common/scripts.jsp"%>
</head>
<body>
	<%@include file="../common/header.jsp"%>
	<div class="product-container col-sm-12 col-xs-12 col-lg-12 col-xg-12">
		<div class="product-image col-sm-6 col-xs-12 col-lg-6 col-xg-6" align="center">
			<img src="<c:url value="/image/product/${productVO.id}.jpg"/>"/>
		</div>
		<div class="product-description col-sm-6 col-xs-12 col-lg-6 col-xg-6">
			<h2>${productVO.name}</h2>
			<p>
				${productVO.description}
			</p>
			<button type="button" onclick="call('<c:url value="/cart/add/${productVO.id}/.product.${productVO.id}"/>')" class="btn-add-product">Only $ ${productVO.value}, Buy now!</button>
		</div>
	</div>
	<%@include file="../common/footer.jsp"%>
</body>
</html>