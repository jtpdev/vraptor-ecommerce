<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container" id="infinite"
	data-infinite-scroll='{ "path": ".pagination__next", "append": ".post", "history": false }'>
	<c:forEach items="${specials}" var="product">
		<div class="product col-lg-4 col-md-4 col-sm-6 col-xs-6 post">
			<div class="product-caption">
				<div class="product-img" align="center">
					<a href="<c:url value="/product/${product.id}"/>"> <img src="<c:url value="/image/product/${product.id}.jpg"/>" />
					</a>
				</div>
				<div class="description">
					<h4>${product.name}</h4>
					<p>
						<span class="price-old">$ ${product.price}</span> - 
						<span class="price-new">$ ${product.special}</span>
					</p>
				</div>
				<button type="button" onclick="call('<c:url value="/cart/add/${product.id}/index"/>')" class="btn-add-product">Buy it!</button>
			</div>
		</div>
	</c:forEach>
</div>