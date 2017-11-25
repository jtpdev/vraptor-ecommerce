package br.com.lucy.models;

import java.util.List;

public class Category {

	private Long id;
	private String name;
	private List<Product> products;
	private Category categoryUp;
	private List<Category> subCategories;

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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Category getCategoryUp() {
		return categoryUp;
	}

	public void setCategoryUp(Category categoryUp) {
		this.categoryUp = categoryUp;
	}

	public List<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<Category> subCategories) {
		this.subCategories = subCategories;
	}
	
	@Override
	public String toString() {
		return getId() + " - " + getName();
	}

}
