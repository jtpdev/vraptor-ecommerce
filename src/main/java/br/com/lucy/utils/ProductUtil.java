package br.com.lucy.utils;

import java.math.BigDecimal;
import java.util.Date;

import br.com.lucy.models.Price;
import br.com.lucy.models.Product;
import br.com.lucy.models.Special;

public class ProductUtil {
	
	public static BigDecimal getPrice(Product product) {
		final Date today = new Date();
		if (product.getSpecials() != null && !product.getSpecials().isEmpty() && !product.getSpecials().contains(null)) {
			for (Special special : product.getSpecials()) {
				if(special.getDateStart().compareTo(today) <= 0
						&& (special.getDateFinish() == null || special.getDateFinish().compareTo(today) >= 0)){
					return special.getPriceValue();
				}
			}
		}
		for (Price price : product.getPricing()) {
			if(price.getDateStart().compareTo(today) <= 0
					&& (price.getDateFinish() == null || price.getDateFinish().compareTo(today) >= 0)){
				return price.getPriceValue();
			}
		}
		return null;
	}

}
