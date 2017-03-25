package br.com.teste.cleber.util;

public enum EDiscounts {

	DISCOUNT_5_PERCENT(5,5),
	DISCOUNT_10_PERCENT(10,10);
	
	public int quantityTotal;
	public int discountValue;
	
	private EDiscounts(int quantityTotal, int discountValue) {
		this.quantityTotal = quantityTotal;
		this.discountValue = discountValue;
	}
	
}
