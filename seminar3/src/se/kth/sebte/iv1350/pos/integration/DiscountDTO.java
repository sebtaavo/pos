package se.kth.sebte.iv1350.pos.integration;

/**
 * 
 * @author sebastian taavo ek
 * DTO used to transfer information about a <code>Discount</code> object.
 */
public class DiscountDTO {
	public double itemListDiscount;
	public double totalCostDiscount;
	public double customerIDDiscount;
	
	/**
	 * Creates a <code>DiscountDTO</code>.
	 * @param itemListDiscount  Contains information about any discount applied through the item-list parameter.
	 * @param totalCostDiscount  Contains information about any discount applied through the total cost parameter.
	 * @param customerIDDiscount  Contains information about any discount applied through the customer ID parameter.
	 */
	public DiscountDTO(double itemListDiscount, double totalCostDiscount, double customerIDDiscount) {
		this.itemListDiscount = itemListDiscount;
		this.totalCostDiscount = totalCostDiscount;
		this.customerIDDiscount = customerIDDiscount;
	}
}
