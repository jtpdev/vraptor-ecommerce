<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container" id="infinite"
	data-infinite-scroll='{ "path": ".pagination__next", "append": ".post", "history": false }'>
	<c:forEach items="${products}" var="product">
		<div class="product col-lg-4 col-md-4 col-sm-6 col-xs-6 post">
			<div class="product-caption">
				<div class="product-img" align="center">
					<a href="<c:url value="/product/${product.id}"/>"> <img src="<c:url value="/image/product/${product.id}.jpg"/>" />
					</a>
				</div>
				<div class="description">
					<h4>${product.name}</h4>
					<p>
					<c:choose>
					    <c:when test="${product.special==null}">
							<span class="price-new">$ ${product.price}</span>
					    </c:when>    
					    <c:otherwise>
							<span class="price-old">$ ${product.price}</span>
							- 
							<span class="price-new">$ ${product.special}</span>
					    </c:otherwise>
					</c:choose>
					</p>
				</div>
				<button type="button" onclick="call('<c:url value="/cart/add/${product.id}/.category.${category.id}"/>')" class="btn-add-product">Buy it!</button>
			</div>
		</div>
	</c:forEach>
</div>