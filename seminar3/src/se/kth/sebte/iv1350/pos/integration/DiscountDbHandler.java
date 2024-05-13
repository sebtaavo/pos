package se.kth.sebte.iv1350.pos.integration;

import java.util.ArrayList;
import se.kth.sebte.iv1350.pos.model.Discount;
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
	public Discount requestDiscount(int customerID, double totalPrice, ArrayList<ItemDTO> items) {
		final double discountFromList = itemListDiscount(items);
		final double discountFromTotal = totalCostDiscount(totalPrice);
		final double discountFromCustomerID = customerIDDiscount(customerID);
		
		DiscountDTO discountDTO = new DiscountDTO(discountFromList, discountFromTotal, discountFromCustomerID);
		Discount discount = new Discount(discountDTO);
		return discount;
	}
	
	private double itemListDiscount(ArrayList<ItemDTO> items) {
		return 0; //returns a sum to be reduced
	}
	
	private double totalCostDiscount(double totalPrice) {
		return 0; //Returns a percentage to be reduced from total price
	}
	
	private double customerIDDiscount(int customerID) {
		if(customerID < 0) { //This is a substitute for a real method checking if the customer ID exists in the external database.
			//This block executes if the ID was invalid.
			return -1;
		}
		return 0; //returns a percentage to be reduced from total price
	}
	
}
