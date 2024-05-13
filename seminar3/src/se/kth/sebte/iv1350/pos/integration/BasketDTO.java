package se.kth.sebte.iv1350.pos.integration;

import java.util.ArrayList;
import se.kth.sebte.iv1350.pos.model.Basket;
import se.kth.sebte.iv1350.pos.model.Cost;
import se.kth.sebte.iv1350.pos.model.Item;

/**
 * @author sebastian taavo ek
 * <code>BasketDTO</code> is used to represent the data contained in the basket in a way that can be passed between the layers
 * of the program.
 * 
 */
public class BasketDTO {
ArrayList<ItemDTO> itemList;
	
	/**
	 * Creates a <code>BasketDTO</code> object.
	 */
	public BasketDTO(Basket basket) {
		itemList = new ArrayList<ItemDTO>();
		for(Item item: basket.getItemList()) {
			ItemDTO newItemDTO = item.getItemDTO();
			itemList.add(newItemDTO);
		}
	
	}
	
	public Cost getGrossTotal() {
		double tallyVAT = 0;
		double tallyTotal = 0;
		for(ItemDTO i: this.itemList) {
			for(int j = 0; j < i.quantity; j++) {
				tallyVAT = tallyVAT + (i.price*(i.VAT/100));
				tallyTotal = tallyTotal + i.price;
			}
		}
		Cost newTotal = new Cost(tallyTotal, tallyVAT);
		return newTotal;
	}
	
	public ArrayList<ItemDTO> getItemList(){
		return itemList;
	}
}
