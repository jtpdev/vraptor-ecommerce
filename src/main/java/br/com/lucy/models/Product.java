package br.com.lucy.models;

import java.util.ArrayList;
import java.util.List;

public class Product {

	private Long id;
	private String name;
	private String description;
	private List<Price> pricing = new ArrayList<>();
	private List<Special> specials = new ArrayList<>();
	private Category category;

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

	public List<Price> getPricing() {
		return pricing;
	}

	public void setPricing(List<Price> pricing) {
		this.pricing = pricing;
	}

	public List<Special> getSpecials() {
		return specials;
	}

	public void setSpecials(List<Special> specials) {
		this.specials = specials;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return getId() + " - " + getName() + " - " + getCategory();
	}
}
