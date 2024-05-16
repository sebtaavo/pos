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
	public Item fetchItem(int itemID, int quantity) throws ItemIdentifierException, DatabaseConnectionException{
		ItemDTO itemDTO;
		itemDTO = this.requestItemDTOFromDb(itemID, quantity);
		return new Item(itemDTO);
		

	}
	
	private ItemDTO requestItemDTOFromDb(int itemID, int quantity) throws ItemIdentifierException, DatabaseConnectionException{
		ItemDTO itemDTO;
		if(verifyItemID(itemID)) {
			switch(itemID) { 
			case(1):
				itemDTO = new ItemDTO("BigWheel Oatmeal", itemID, quantity, "BigWheel Oatmeal 500g, whole grain oats, high fiber, gluten free", 29.9, 6);
				return itemDTO;
			case(2):
				itemDTO = new ItemDTO("YouGoGo Blueberry", itemID, quantity, "YouGoGo Blueberry 240g, low sugar yoghurt, blueberry flavour", 14.9, 6);
				return itemDTO;
			case(3): //This is a dummy case to illustrate what would happen if we failed to connect to the hypothetical server.
				throw new DatabaseConnectionException("Failed to connect to external inventory server when fetching item data. Verify internet connection or status of server.");
			default:
				throw new ItemIdentifierException("Item ID " + itemID + " could not be found in the database.");
			}
				
		}else {
			throw new ItemIdentifierException("Item ID " + itemID + " was not found in the external inventory database.");
		}
	}
		
	private boolean verifyItemID(int itemID){ //This class would have deeper logic if we knew how the external inventory system was structured.
		if(itemID < 0) { //This method would query the database to see if the itemID is valid, and probably return the itemDTO and not a boolean.
			return false;
		}
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
