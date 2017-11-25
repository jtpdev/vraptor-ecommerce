<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="products-on-cart" align="center">
	<c:forEach items="${products}" var="productCart">
		<div class="product-on-cart col-sm-12 col-xs-12 col-lg-12 col-xg-12">
			<div
				class="image-product-on-cart col-sm-2 col-xs-12 col-lg-2 col-xg-2">
				<a href="<c:url value="/product/${productCart.product.id}"/>">
					<img src="<c:url value="/image/product/${productCart.product.id}.jpg"/>" />
				</a>
			</div>
			<div class="name-product-on-cart col-sm-5 col-lg-5 col-xg-5">
				<h4>${productCart.product.name}</h4>
			</div>
			<div class="qtd-product-on-cart col-sm-3 col-xs-12 col-lg-3 col-xg-3">
				<input type="text" onblur="addQuantity(this,'<c:url value="/cart/${productCart.product.id}/add"/>');"
					value="${productCart.quantity}" /> 
				<button type="button"
					onclick="call('<c:url value="/cart/add/${productCart.product.id}/.cart"/>');"
					class="plus-product-on-cart">+</button>
				<button type="button"
					onclick="call('<c:url value="/cart/remove/${productCart.product.id}"/>');"
					class="sub-product-on-cart">-</button>
				<button type="button"
					onclick="call('<c:url value="/cart/removeall/${productCart.product.id}"/>');"
					class="remove-product-on-cart">
					<img 
						src="<c:url value="/image/remove_all.png"/>" />
				</button>
			</div>
			<div
				class="value-product-on-cart col-sm-2 col-xs-2 col-lg-2 col-xg-2">
				$ ${productCart.value}
			</div>
		</div>
	</c:forEach>
	<div class="shipping-methods col-sm-12 col-xs-12 col-lg-12 col-xg-12">
		<h4>Shipping Method(s)</h4>
	</div>
	<c:forEach items="${shippings}" var="shipping">
		<div class="col-sm-12 col-xs-12 col-lg-12 col-xg-12">
			<div class="shipping col-sm-8 col-xs-12 col-lg-12 col-xg-8">
				<label>
				<c:choose>
					    <c:when test="${shipping.id==cartVO.shipping.id}">
							<input checked="checked" type="radio" name="${shipping.name}" value="${shipping.id}" onchange="callByCheck(this,'<c:url value="/shipping/${shipping.id}"/>');"> ${shipping.name} - ${shipping.description}<br>
					    </c:when>    
					    <c:otherwise>
							<input type="radio" name="${shipping.name}" value="${shipping.id}" onchange="callByCheck(this,'<c:url value="/shipping/${shipping.id}"/>');"> ${shipping.name} - ${shipping.description}<br>
					    </c:otherwise>
					</c:choose>
				</label>
			</div>
		</div>
	</c:forEach>
	<div class="col-sm-12 col-xs-12 col-lg-12 col-xg-12">
		<h4>Total</h4>
	</div>
	<div class="col-sm-12 col-xs-12 col-lg-12 col-xg-12">
			<div class="cart-subtotal col-sm-2 col-xs-12 col-lg-4 col-xg-2">
					$ ${cartVO.subTotal}
			</div>
	</div>
	<div class="col-sm-12 col-xs-12 col-lg-12 col-xg-12">
			<div class="shipping-total col-sm-2 col-xs-12 col-lg-4 col-xg-2">
					$ ${cartVO.shippingValue}
			</div>
	</div>
	<div class="col-sm-12 col-xs-12 col-lg-12 col-xg-12">
			<div class="cart-total col-sm-2 col-xs-12 col-lg-4 col-xg-2">
					$ ${cartVO.total}
			</div>
	</div>
	<div class="col-sm-12 col-xs-12 col-lg-12 col-xg-12">
		<div class="checkout col-sm-4 col-xs-12 col-lg-4 col-xg-4">
				<button class="btn-add-product" type="button"
					onclick="call('<c:url value="/checkout"/>');"
					class="checkout-button">
					Checkout now!
				</button>
		</div>
	</div>
</div>