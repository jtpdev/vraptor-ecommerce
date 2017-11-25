package br.com.lucy.controllers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.lucy.daos.ProductDAO;
import br.com.lucy.daos.SaleDAO;
import br.com.lucy.daos.ShippingDAO;
import br.com.lucy.daos.UserDAO;
import br.com.lucy.models.EStatus;
import br.com.lucy.models.Product;
import br.com.lucy.models.ProductSale;
import br.com.lucy.models.Sale;
import br.com.lucy.models.Shipping;
import br.com.lucy.models.ShippingSale;
import br.com.lucy.utils.BigDecimalBuilder;
import br.com.lucy.utils.ControllerUtil;
import br.com.lucy.utils.EmailUtil;
import br.com.lucy.utils.ProductUtil;
import br.com.lucy.utils.UriUtil;
import br.com.lucy.vos.CartVO;
import br.com.lucy.vos.ProductCartVO;
import br.com.lucy.vos.ProductVO;
import br.com.lucy.vos.UserVO;

@Controller
public class CartController {

	private static final BigDecimal SHIPPING_VALUE = BigDecimal.valueOf(24.9).setScale(2, BigDecimal.ROUND_HALF_UP);
	private final Result result;
	private final SaleDAO saleDAO;
	private final ProductDAO productDAO;
	private final CartVO cart;
	private final UserVO userVO;
	private final UserDAO userDAO;
	private final ShippingDAO shippingDAO;
	private final ControllerUtil util;
	private final UriUtil uri;

	/**
	 * @deprecated CDI eyes only
	 */
	protected CartController() {
		this(null, null, null, null, null, null, null, null, null);
	}

	@Inject
	public CartController(Result result, SaleDAO saleDAO, CartVO cart, ProductDAO productDAO, UserVO userVO,
			UserDAO userDAO, ControllerUtil util, ShippingDAO shippingDAO, UriUtil uri) {
		this.result = result;
		this.saleDAO = saleDAO;
		this.cart = cart;
		this.productDAO = productDAO;
		this.userVO = userVO;
		this.userDAO = userDAO;
		this.util = util;
		this.shippingDAO = shippingDAO;
		this.uri = uri;
	}

	@Path("/cart")
	public CartVO cart() {
		result.include("products", getProductsVO(cart.getProducts()));
		try {
			result.include("shippings", shippingDAO.list());
			util.writeHeader();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cart;
	}

	@Path("/shipping/{shipping.id}")
	public void shipping(Shipping shipping) {
		cart.setShipping(shipping);
		cart.setShippingValue(SHIPPING_VALUE);
		result.redirectTo(CartController.class).cart();
	}

	@Dependent
	@Path("/cart/add/{product_id}/{uri}")
	public void add(Long product_id, String uri) {
		try {
			cart.add(productDAO.find(product_id));
			result.include("products", getProductsVO(cart.getProducts()));
			String url = uri.equals("index") ? "/" : uri.replace(".", "/");
			result.redirectTo(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Path("/cart/{product_id}/add/{qtd}")
	public void add(Long product_id, Long qtd) {
		try {
			cart.add(productDAO.find(product_id), qtd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result.include("products", getProductsVO(cart.getProducts()));
		result.redirectTo(this).cart();
	}

	@Path("/cart/remove/{product_id}")
	public void remove(Long product_id) {
		cart.remove(product_id);
		result.include("products", getProductsVO(cart.getProducts()));
		result.redirectTo(this).cart();
	}
	
	@Path("/cart/removeall/{product_id}")
	public void removeAll(Long product_id) {
		cart.removeAll(product_id);
		result.include("products", getProductsVO(cart.getProducts()));
		result.redirectTo(this).cart();
	}

	@Path("/checkout")
	public void checkout() {
		try {
			if(!userVO.getLogged()){
				result.redirectTo(UserController.class).login("User is offline... Login before!", "/checkout");
			}else{
				Sale sale = new Sale();
				sale.setDateSale(new Date());
				sale.setProducts(getProductSales(sale));
				sale.setShipping(getShippingSale(sale));
				sale.setTotalValue(getTotalValue(sale));
				sale.setStatus(EStatus.PENDING);
				sale.setUser(userDAO.find(userVO.getId()));
				Sale savedSale = saleDAO.save(sale);
				if(savedSale != null) {
					EmailUtil.sendEmailForSale(savedSale);
					result.redirectTo(CartController.class).sentEmail();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Path("/email")
	public void sentEmail() {
		cart.setProducts(new ArrayList<Product>());
		cart.setShipping(null);
		cart.setShippingValue(BigDecimal.ZERO);
		try {
			util.writeHeader();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result.include("done","Order is done!");
		result.include("email","A email has sent to your email box!");
	}

	private BigDecimal getTotalValue(Sale sale) {
		BigDecimalBuilder totalValue = new BigDecimalBuilder();
		for (ProductSale product : sale.getProducts()) {
			totalValue.add(product.getSaleValue());
		}
		totalValue.add(sale.getShipping().getShippingValue());
		return totalValue.getValue();
	}

	private ShippingSale getShippingSale(Sale sale) {
		ShippingSale shippingSale = new ShippingSale();
		shippingSale.setSale(sale);
		shippingSale.setShipping(cart.getShipping());
		shippingSale.setShippingValue(SHIPPING_VALUE); // TODO only for tests
		return shippingSale;
	}

	private List<ProductSale> getProductSales(Sale sale) {
		List<ProductSale> productsSale = new ArrayList<>();
		for (Product product : cart.getProducts()) {
			ProductSale productSale = new ProductSale();
			productSale.setProduct(product);
			productSale.setSale(sale);
			productSale.setSaleValue(ProductUtil.getPrice(product));
			productsSale.add(productSale);
		}
		return productsSale;
	}
	public List<ProductCartVO> getProductsVO(List<Product> products) {
		Map<Long,Long> values = new HashMap<Long,Long>();
		for (Product product : products) {
			if(values.containsKey(product.getId())){
				values.put(product.getId(), values.get(product.getId())+1);
			}else{
				values.put(product.getId(), 1l);
			}
		}
		
		List<ProductCartVO> productsVO = new ArrayList<>();
		for (Entry<Long, Long> value : values.entrySet()) {
			ProductCartVO productCartVO = new ProductCartVO();
			ProductVO productVO = new ProductVO();
			for (Product product : products) {
				if(product.getId() == value.getKey()){
					productVO.toVO(product);
					productCartVO.setProduct(productVO);
					productCartVO.setQuantity(value.getValue());
					productCartVO.setValue(ProductUtil.getPrice(product).multiply(new BigDecimal(value.getValue())));
				}
			}
			productsVO.add(productCartVO);
			
		}
		
		return productsVO;
	}

}
