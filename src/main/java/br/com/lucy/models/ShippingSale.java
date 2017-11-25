package br.com.lucy.models;

import java.math.BigDecimal;

public class ShippingSale {
	
	private Long id;
	private Sale sale;
	private Shipping shipping;
	private BigDecimal shippingValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	public Shipping getShipping() {
		return shipping;
	}
	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}
	public BigDecimal getShippingValue() {
		return shippingValue;
	}
	public void setShippingValue(BigDecimal shippingValue) {
		this.shippingValue = shippingValue;
	}
	
}
