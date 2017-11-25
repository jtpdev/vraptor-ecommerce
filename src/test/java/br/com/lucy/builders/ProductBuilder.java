package br.com.lucy.builders;

import java.util.List;

import br.com.lucy.models.Category;
import br.com.lucy.models.Price;
import br.com.lucy.models.Product;
import br.com.lucy.models.Special;

public class ProductBuilder implements Builder<Product>{

	private Product product;
	
	public ProductBuilder(){
		this(new Product());
	}

	public ProductBuilder(Product product) {
		this.product = product;
	}

	@Override
	public Product getValue() {
		return product;
	}

	public ProductBuilder setId(Long id) {
		product.setId(id);
		return this;
	}

	public ProductBuilder setName(String name) {
		product.setName(name);
		return this;
	}

	public ProductBuilder setDescription(String description) {
		product.setDescription(description);
		return this;
	}

	public ProductBuilder setPricing(List<Price> pricing) {
		product.setPricing(pricing);
		return this;
	}

	public ProductBuilder setSpecials(List<Special> specials) {
		product.setSpecials(specials);
		return this;
	}

	public ProductBuilder setCategory(Category category) {
		product.setCategory(category);
		return this;
	}

}
