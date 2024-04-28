package se.kth.sebte.iv1350.pos.integration;

import java.util.ArrayList;
import se.kth.sebte.iv1350.pos.model.Discount;
import se.kth.sebte.iv1350.pos.model.Item;

public class DiscountDbHandler {

	public DiscountDbHandler() {
		
	}
	
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
