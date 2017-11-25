package br.com.lucy.utils;

import java.math.BigDecimal;

public class BigDecimalBuilder {
	
	private BigDecimal value;

	public BigDecimalBuilder() {
		this(BigDecimal.ZERO);
	}
	
	public BigDecimalBuilder(BigDecimal value) {
		this.value = value;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	public BigDecimalBuilder add(BigDecimal value){
		this.value = this.value.add(value);
		return this;
	}

	public BigDecimalBuilder subtract(BigDecimal subtrahend) {
		this.value = value.subtract(subtrahend);
		return this;
	}

	public BigDecimalBuilder multiply(BigDecimal multiplicand) {
		value = value.multiply(multiplicand);
		return this;
	}

	public BigDecimalBuilder divide(BigDecimal divisor) {
		value = value.divide(divisor);
		return this;
	}

	public BigDecimalBuilder pow(int n) {
		value =  value.pow(n);
		return this;
	}

	public String toString() {
		return value.toString();
	}

}
