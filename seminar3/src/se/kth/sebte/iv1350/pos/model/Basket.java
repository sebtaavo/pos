package se.kth.sebte.iv1350.pos.model;
import java.util.ArrayList;

import se.kth.sebte.iv1350.pos.integration.BasketDTO;

/**
 * 
 * @author sebastian taavo ek
 * This class is responsible for keeping track of the items currently being sold
 * in a given <code>Sale</code>. It also handles updating the list of items each time
 * an item is scanned, and can return the total cost and VAT of the items in the list.
 *
 */
public class Basket {
	ArrayList<Item> itemList;
	
	/**
	 * Creates a <code>Basket</code> object.
	 */
	public Basket() {
		itemList = new ArrayList<Item>();
	}
	
	
	/**
	 * Updates the basket with a new item. Updates quantity if an item of the same ID already exists in the list.
	 * @param item  The new item scanned in through a bar-code.
	 */
	void updateBasket(Item item) {
		boolean itemAlreadyInBasket = false;
		for(Item i: this.itemList) {
			if(i.itemID == item.itemID) {
				i.quantity = i.quantity + item.quantity;
				itemAlreadyInBasket = true;
			}
		}
		if(itemAlreadyInBasket == false) {
			this.itemList.add(item);
		}
	}
	
	/**
	 * Creates an object of type <code>Cost</code>, containing the total price and VAT
	 * tally'd up from all items in the current item list.
	 * @return newTotal   The <code>Cost</code> including both price and VAT of all items combined.
	 */
	public Cost getGrossTotal() {
		double tallyVAT = 0;
		double tallyTotal = 0;
		for(Item i: this.itemList) {
			for(int j = 0; j < i.quantity; j++) {
				tallyVAT = tallyVAT + (i.price*(i.VAT/100));
				tallyTotal = tallyTotal + i.price;
			}
		}
		Cost newTotal = new Cost(tallyTotal, tallyVAT);
		return newTotal;
	}
	
	/**
	 * Getter for the current items in the list.
	 * @return
	 */
	public ArrayList<Item> getItemList(){
		return itemList;
	}
	
	/**
	 * Getter for a DTO object containing information about the items currently in this basket.
	 * @return
	 */
	public BasketDTO getBasketDTO(){
		return new BasketDTO(this);
	}
}
