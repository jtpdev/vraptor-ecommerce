package br.com.lucy.vos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import br.com.lucy.models.Product;
import br.com.lucy.models.Shipping;
import br.com.lucy.models.User;
import br.com.lucy.utils.ProductUtil;

@SessionScoped
public class CartVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<Product> products = new ArrayList<Product>();
	private User user;
	private BigDecimal subTotal = BigDecimal.ZERO.setScale(2);
	private BigDecimal total = BigDecimal.ZERO.setScale(2);
	private BigDecimal shippingValue = BigDecimal.ZERO.setScale(2);
	private Shipping shipping;
	
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		if(products.isEmpty()){
			setSubTotal(BigDecimal.ZERO.setScale(2));
		}
		for (Product product : products) {
			setSubTotal(ProductUtil.getPrice(product).add(getSubTotal()));
		}
		this.products = products;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public void add(Product product, Long quantity){
		removeAll(product.getId());
		for (int i = 0; i < quantity; i++) {
			add(product);
		}
	}
	
	public void add(Product product){
		getProducts().add(product);
		setSubTotal(ProductUtil.getPrice(product).add(getSubTotal()));
	}
	
	public void remove(Long product_id){
		for (int i = 0; i < getProducts().size(); i++) {
			Product product = getProducts().get(i);
			if(product.getId() == product_id){
				getProducts().remove(i);
				break;
			}
		}
		setSubTotal(BigDecimal.ZERO);
		for (Product p : products) {
			setSubTotal(ProductUtil.getPrice(p).add(getSubTotal()));
		}
	}
	
	public void removeAll(Long product_id){
		
		Iterator<Product> products = getProducts().iterator();
		while (products.hasNext()) {
			Product product = products.next();
			if(product.getId() == product_id){
				products.remove();
			}
		}
		
		setSubTotal(BigDecimal.ZERO);
		for (Product p : getProducts()) {
			setSubTotal(ProductUtil.getPrice(p).add(getSubTotal()));
		}
	}
	public Shipping getShipping() {
		return shipping;
	}
	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}
	public BigDecimal getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
		setTotal(subTotal.add(getShippingValue()));
	}
	public BigDecimal getShippingValue() {
		return shippingValue;
	}
	public void setShippingValue(BigDecimal shippingValue) {
		this.shippingValue = shippingValue;
		setTotal(shippingValue.add(getSubTotal()));
	}

}
