package se.kth.sebte.iv1350.pos.integration;
import java.util.ArrayList;

import se.kth.sebte.iv1350.pos.model.Item;

/**
 * 
 * @author sebastian taavo ek
 * A class responsible for handling all contact with the external Inventory system.
 */
public class InventoryDbHandler {
	/**
	 * Creates an <code>InventoryDbHandler</code>.
	 */
	public InventoryDbHandler() {
		
	}
	
	/**
	 * Fetches an <code>Item</code> or <code>Null</code> object by contacting the database with the ItemID information.
	 * @param itemID  Identifier obtained through a bar-code scan.
	 * @param quantity  Quantity of the scanned item to be registered to the sale.
	 * @return
	 */
	public Item fetchItem(int itemID, int quantity) {
		ItemDTO itemDTO;
		if(verifyItemID(itemID)) {
			itemDTO = this.requestItemDTOFromDb(itemID, quantity);
			return new Item(itemDTO);
		}
		else {
			return null;
		}
	}
	
	private ItemDTO requestItemDTOFromDb(int itemID, int quantity) {
		ItemDTO itemDTO;
		switch(itemID) { 
			case(1):
				itemDTO = new ItemDTO("BigWheel Oatmeal", itemID, quantity, "BigWheel Oatmeal 500g, whole grain oats, high fiber, gluten free", 29.9, 6);
				return itemDTO;
			case(2):
				itemDTO = new ItemDTO("YouGoGo Blueberry", itemID, quantity, "YouGoGo Blueberry 240g, low sugar yoghurt, blueberry flavour", 14.9, 6);
				return itemDTO;
			default:
				itemDTO = new ItemDTO("Error", itemID, quantity, "Error", 0, 0);
				return itemDTO;
		}
	}
	
	private boolean verifyItemID(int itemID) {
		return true;
	}
	
	/**
	 * Updates the external inventory system with information about which items were sold.
	 * @param items  The <code>Item</code> list contained in the <code>Basket</code> of the current <code>Sale</code>.
	 */
	public void updateInventory(ArrayList<Item> items) {
		for(Item i: items) {
			removeFromInventory(i);
		}
	}
	
	private void removeFromInventory(Item item) {
		//This method contacts an external database. Not possible to implement at this time.
	}
}
