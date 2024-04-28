package se.kth.sebte.iv1350.pos.model;
import java.util.ArrayList;

public class Basket {
	ArrayList<Item> itemList;
	public Basket() {
		itemList = new ArrayList<Item>();
	}
	
	
	Basket updateBasket(Item item) {
		boolean itemAlreadyInBasket = false;
		for(Item i: itemList) {
			if(i.itemID == item.itemID) {
				i.quantity = i.quantity + item.quantity;
				itemAlreadyInBasket = true;
			}
		}
		if(itemAlreadyInBasket == false) {
			itemList.add(item);
		}
		return this;
	}
	
	int applyDiscount(Discount discount) {
		return 0;
		
	}
	
	public int getGrossTotal() {
		return 0;
	}
}
