<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="header">
	<div id="logo" class="col-sm-2 col-xs-12 col-lg-3 col-xg-2" align="center">
		<a href="<c:url value="/"/>"><img
			src="<c:url value="/image/logo.png"/>" /></a>
	</div>
	<div class="category-button">
		<button class="btn-header" id="btn-category">
			<div class="col-xs-9">
				Categories
			</div>
			<div class="col-xs-3">
				<img src="#" />
			</div>
		</button>
	</div>
	<div id="categories" class="col-sm-6 col-xs-12 col-lg-5 col-xg-7"
		align="center">
		<c:forEach items="${categories}" var="category">
			<div class="category col-sm-3 col-xs-12 col-lg-3 col-xg-3">
				<button type="button" class="btn-header"
					onclick="call('<c:url value="/category/${category.id}"/>')">
					${category.name}
				</button>
			</div>
		</c:forEach>
	</div>
	<div id="cart" class="col-sm-4 col-xs-12 col-lg-4 col-xg-3">
		<button type="button" class="btn-header" onclick="call('<c:url value="/cart"/>');">
				<img src="<c:url value="/image/bag.png"/>" id="img-value"/> 
				 $ ${total}
		</button>
	</div>
	<div class="user col-sm-3 col-xs-12 col-lg-4 col-xg-2" align="center">
		Welcome! <a href="<c:url value="/clientdashboard"/>">${usermessage}</a>
	</div>
</div>