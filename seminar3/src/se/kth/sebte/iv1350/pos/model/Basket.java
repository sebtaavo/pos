package se.kth.sebte.iv1350.pos.model;
import java.util.ArrayList;

public class Basket {
	ArrayList<Item> itemList;
	private Cost basketTotalCost;
	public Basket() {
		itemList = new ArrayList<Item>();
	}
	
	
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
		this.basketTotalCost = getGrossTotal();
	}
	
	int applyDiscount(Discount discount) {
		return 0;
		
	}
	
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
	
	public ArrayList<Item> getItemList(){
		return itemList;
	}
}
