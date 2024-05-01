package se.kth.sebte.iv1350.pos.integration;

import java.util.ArrayList;
import se.kth.sebte.iv1350.pos.model.Discount;
import se.kth.sebte.iv1350.pos.model.Item;
/**
 * 
 * @author sebastian taavo ek
 * Handler class responsible for contacting the external <code>Discount</code> database, and retrieving information
 * to the program about which discounts are applicable to the sale.
 *
 */
public class DiscountDbHandler {
	
	/**
	 * Creates a <code>DiscountDbHandler</code>.
	 */
	public DiscountDbHandler() {
		
	}
	
	/**
	 * Fetches a <code>Discount</code> object by contacting the external database with the necessary parameters.
	 * @param customerID  The integer ID used to identify a unique customer.
	 * @param totalPrice  The total price of the current <code>Sale</code>.
	 * @param items  The full list of items currently being purchased by the customer.
	 * @return discount   The <code>Discount</code> object which will be used to alter the price the customer has to pay.
	 */
	public Discount requestDiscount(int customerID, double totalPrice, ArrayList<Item> items) {
		final double discountFromList = itemListDiscount(items);
		final double discountFromTotal = totalCostDiscount(totalPrice);
		final double discountFromCustomerID = customerIDDiscount(customerID);
		
		DiscountDTO discountDTO = new DiscountDTO(discountFromList, discountFromTotal, discountFromCustomerID);
		Discount discount = new Discount(discountDTO);
		return discount;
	}
	
	private double itemListDiscount(ArrayList<Item> items) {
		return 0; //returns a sum to be reduced
	}
	
	private double totalCostDiscount(double totalPrice) {
		return 0; //Returns a percentage to be reduced from total price
	}
	
	private double customerIDDiscount(int customerID) {
		return 0; //returns a percentage to be reduced from total price
	}
	
}
