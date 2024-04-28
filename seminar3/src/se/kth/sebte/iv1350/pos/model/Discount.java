package se.kth.sebte.iv1350.pos.model;
import java.util.ArrayList;

import se.kth.sebte.iv1350.pos.integration.DiscountDTO;

public class Discount {
	double itemListDiscount;
	double totalCostDiscount;
	double customerIDDiscount;
	
	public Discount(DiscountDTO discountDTO) {
		this.itemListDiscount = discountDTO.amount;
		this.totalCostDiscount = discountDTO.description;
		this.customerIDDiscount = discountDTO.customerIDDiscount;
	}
	
	
	public DiscountDTO getDiscountDTO() {
		DiscountDTO discountDTO = new DiscountDTO(this.itemListDiscount, this.totalCostDiscount, this.customerIDDiscount);
		return discountDTO;
	}
	
}
