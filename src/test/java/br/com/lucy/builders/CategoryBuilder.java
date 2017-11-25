package br.com.lucy.builders;

import java.util.List;

import br.com.lucy.models.Category;
import br.com.lucy.models.Product;

public class CategoryBuilder implements Builder<Category>{
	
	private Category category;
	
	public CategoryBuilder() {
		this(new Category());
	}

	public CategoryBuilder(Category category) {
		this.category = category;
	}

	public CategoryBuilder setId(Long id) {
		category.setId(id);
		return this;
	}

	public CategoryBuilder setName(String name) {
		category.setName(name);
		return this;
	}

	public CategoryBuilder setProducts(List<Product> products) {
		category.setProducts(products);
		return this;
	}

	public CategoryBuilder setCategoryUp(Category categoryUp) {
		category.setCategoryUp(categoryUp);
		return this;
	}

	public CategoryBuilder setSubCategories(List<Category> subCategories) {
		category.setSubCategories(subCategories);
		return this;
	}

	@Override
	public Category getValue() {
		return category;
	}

}
