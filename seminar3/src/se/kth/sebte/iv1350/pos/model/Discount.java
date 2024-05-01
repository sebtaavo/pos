package se.kth.sebte.iv1350.pos.model;

import se.kth.sebte.iv1350.pos.integration.DiscountDTO;

/**
 * @author sebastian taavo ek
 * This class contains information about what kind of discount should be applied to a given <code>Sale</code>.
 */
public class Discount {
	double itemListDiscount;
	double totalCostDiscount;
	double customerIDDiscount;
	
	/**
	 * Creates a <code>Discount</code> object. Takes in a <code>DiscountDTO</code>.
	 * @param discountDTO  The <code>DiscountDTO</code> whose attributes will populate this class instance.
	 */
	public Discount(DiscountDTO discountDTO) {
		this.itemListDiscount = discountDTO.itemListDiscount;
		this.totalCostDiscount = discountDTO.totalCostDiscount;
		this.customerIDDiscount = discountDTO.customerIDDiscount;
	}
	
	/**
	 * Getter for the attributes of this class instance, through the use of a DTO
	 * in order to protect the attributes.
	 * @return
	 */
	public DiscountDTO getDiscountDTO() {
		DiscountDTO discountDTO = new DiscountDTO(this.itemListDiscount, this.totalCostDiscount, this.customerIDDiscount);
		return discountDTO;
	}
	
}
