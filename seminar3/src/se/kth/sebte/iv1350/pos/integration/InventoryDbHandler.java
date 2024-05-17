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
	 * Throws exceptions generated in the private method call requestItemDTOFromDb() in case the item identifier was faulty
	 * or if the connection to the external inventory database could not established. The latter is simulated with the hard-coded
	 * value of "3" as the item identifier.
	 * @param itemID  Identifier obtained through a bar-code scan.
	 * @param quantity  Quantity of the scanned item to be registered to the sale.
	 * @return itemDTO  The new ItemDTO fetched from a bar-code scan.
	 * @throws ItemIdentifierException, DatabaseConnectionException  The exceptions thrown all the way up to the <code>View</code> from this method.
	 */
	public Item fetchItem(int itemID, int quantity) throws ItemIdentifierException, DatabaseConnectionException{
		ItemDTO itemDTO;
		itemDTO = this.requestItemDTOFromDb(itemID, quantity);
		return new Item(itemDTO);
		

	}
	
	private ItemDTO requestItemDTOFromDb(int itemID, int quantity) throws ItemIdentifierException, DatabaseConnectionException{
		ItemDTO itemDTO;
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
