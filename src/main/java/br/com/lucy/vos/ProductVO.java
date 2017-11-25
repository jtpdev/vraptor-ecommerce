package br.com.lucy.vos;

import java.math.BigDecimal;
import java.util.Date;

import br.com.lucy.models.Category;
import br.com.lucy.models.Price;
import br.com.lucy.models.Product;
import br.com.lucy.models.Special;

public class ProductVO implements VO<Product> {

	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private BigDecimal special;
	private Category category;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getSpecial() {
		return special;
	}

	public void setSpecial(BigDecimal special) {
		this.special = special;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public Product toModel() {
		Product product = new Product();
		product.setCategory(getCategory());
		product.setId(getId());
		product.setDescription(getDescription());
		product.setName(getName());
		return product;
	}

	@Override
	public void toVO(Product model) {
		if (model == null) {
			return;
		}
		setId(model.getId());
		setCategory(model.getCategory());
		setDescription(model.getDescription());
		setName(model.getName());
		setPrice(getPrice(model));
		if (model.getSpecials() != null && !model.getSpecials().isEmpty() && !model.getSpecials().contains(null)) {
			setSpecial(getSpecial(model));
		}

	}

	private BigDecimal getPrice(Product product) {
		Date today = new Date();
		for (Price price : product.getPricing()) {
			if(price.getDateStart().compareTo(today) <= 0
					&& (price.getDateFinish() == null || price.getDateFinish().compareTo(today) >= 0)){
				return price.getPriceValue();
			}
		}
		return null;
	}
	
	private BigDecimal getSpecial(Product product) {
		Date today = new Date();
		for (Special special : product.getSpecials()) {
			if(special.getDateStart().compareTo(today) <= 0
					&& (special.getDateFinish() == null || special.getDateFinish().compareTo(today) >= 0)){
				return special.getPriceValue();
			}
		}
		return null;
	}

	public BigDecimal getValue() {
		return special != null ? special : price;
	}

}
