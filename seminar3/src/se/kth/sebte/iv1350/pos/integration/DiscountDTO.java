package se.kth.sebte.iv1350.pos.integration;

public class DiscountDTO {
	public double itemListDiscount;
	public double totalCostDiscount;
	public double customerIDDiscount;
	
	public DiscountDTO(double itemListDiscount, double totalCostDiscount, double customerIDDiscount) {
		this.itemListDiscount = itemListDiscount;
		this.totalCostDiscount = totalCostDiscount;
		this.customerIDDiscount = customerIDDiscount;
	}
}
