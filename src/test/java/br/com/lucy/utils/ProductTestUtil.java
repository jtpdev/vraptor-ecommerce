package br.com.lucy.utils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import br.com.lucy.daos.CategoryDAO;
import br.com.lucy.models.Category;
import br.com.lucy.models.Price;
import br.com.lucy.models.Product;
import br.com.lucy.models.Special;

public class ProductTestUtil {
	
	private static final Random RANDOM = new Random();
	
	private static final String[] VARIETIES = {
			"Airén",
			"Garnacha",
			"Ugnilanc",
			"Merlot",
			"Cabernet Sauvignon",
			"Rkatsiteli",
			"Monastrell",
			"Tempranillo",
			"Chardonnay",
			"Sangiovese",
			"Cinsault",
			"Welschriesling",
			"Catarratto",
			"Aligoté ",
			"Moscatel",
			"Pinot Noir"
	};
	
	private static final String[] TRADES = {
			"Almeida Porto",
			"Hofstader",
			"Canops",
			"Mendoza",
			"Yelow Finger",
			"Göujath",
			"Matrioska",
			"Porto Belo",
			"Östch"
	};
	
	private static final String SYMBOL = "ABCDEFGHIJKLNOPQRSTUVWXYZ";

	public static String createNameProduct() {
		return TRADES[RANDOM.nextInt(TRADES.length-1)] + " - " + VARIETIES[RANDOM.nextInt(VARIETIES.length)];
	}

	public static String createDescriptionProduct() {
		String value = "";
		while (value.length() < 200) {
			String space = RANDOM.nextBoolean() ? " " : "";
			char c = RANDOM.nextBoolean() ? SYMBOL.toCharArray()[RANDOM.nextInt(SYMBOL.length())]
					: SYMBOL.toLowerCase().toCharArray()[RANDOM.nextInt(SYMBOL.length())];
			value += c + space;
		}
		return value;
	}

	public static Category getCategory(CategoryDAO categorydao) {
		try {
			Category category = new Category();
			category.setId((long)RANDOM.nextInt(categorydao.nextId().intValue()-1)+1);
			return category;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Price> createPricing(Product product) {
		return createPricing(product, 1);
	}

	private static List<Price> createPricing(Product product, int count) {
		ArrayList<Price> pricing = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			pricing.add(createPrice(product));
		}
		return pricing;
	}

	public static Price createPrice(Product product) {
		Price price = new Price();
		price.setDateStart(new Date());
		price.setPriceValue(BigDecimal.valueOf(RANDOM.nextInt(250)/3d).setScale(2, BigDecimal.ROUND_HALF_UP));
		price.setProduct(product);
		return price;
	}

	public static List<Special> createSpecials(Product product) {
		return createSpecials(product, 1);
	}

	private static List<Special> createSpecials(Product product, int count) {
		ArrayList<Special> pricing = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			pricing.add(createSpecial(product));
		}
		return pricing;
	}

	private static Special createSpecial(Product product) {
		if(RANDOM.nextBoolean())
			return null;
		Special special = new Special();
		special.setDateStart(new Date());
		BigDecimal priceValue = product.getPricing().get(0).getPriceValue();
		BigDecimal specialValue = priceValue.compareTo(BigDecimal.TEN) > 0 ? priceValue.subtract(BigDecimal.TEN) : priceValue.subtract(BigDecimal.ONE);
		special.setPriceValue(specialValue);
		special.setProduct(product);
		return special;
	}

	public static void setSpecialOnProduct(Product product) {
		List<Special> createSpecials = ProductTestUtil.createSpecials(product);
		if(!createSpecials.contains(null))
			product.setSpecials(createSpecials);
	}

}
