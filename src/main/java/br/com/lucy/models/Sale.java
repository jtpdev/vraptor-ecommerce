package br.com.lucy.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Sale {
	
	private Long id;
	private EStatus status;
	private User user;
	private List<ProductSale> products;
	private Date dateSale;
	private ShippingSale shipping;
	private BigDecimal totalValue;
	private Date finishDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EStatus getStatus() {
		return status;
	}
	public void setStatus(EStatus status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<ProductSale> getProducts() {
		return products;
	}
	public void setProducts(List<ProductSale> products) {
		this.products = products;
	}
	public Date getDateSale() {
		return dateSale;
	}
	public void setDateSale(Date dateSale) {
		this.dateSale = dateSale;
	}
	public BigDecimal getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}
	public ShippingSale getShipping() {
		return shipping;
	}
	public void setShipping(ShippingSale shipping) {
		this.shipping = shipping;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

}
