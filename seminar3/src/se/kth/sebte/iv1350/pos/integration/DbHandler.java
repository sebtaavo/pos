package se.kth.sebte.iv1350.pos.integration;
import java.util.ArrayList;
import se.kth.sebte.iv1350.pos.model.Discount;
import se.kth.sebte.iv1350.pos.model.Item;

/**
 * 
 * @author sebastian taavo ek
 * Class responsible for contacting the various database handling classes in one place.
 * Mediates requests from the <code>Controller</code> to the database handling classes.
 *
 */
public class DbHandler {
	private AccountingDbHandler accountingDbHandler;
	private InventoryDbHandler inventoryDbHandler;
	private DiscountDbHandler discountDbHandler;
	
	
	/**
	 * Creates an instance of this class object.
	 */
	public DbHandler() {
		accountingDbHandler = new AccountingDbHandler();
		inventoryDbHandler = new InventoryDbHandler();
		discountDbHandler = new DiscountDbHandler();
	}
	
	/**
	 * Fetches an <code>Item</code> from the <code>InventoryDbHandler</code>.
	 * @param itemID  The unique item identifier found through the bar-code.
	 * @param quantity   The amount of said item to be processed.
	 * @return item  The item found in the database. Can also be null if the bar-code scan failed.
	 */
	public Item fetchItem(int itemID, int quantity) throws ItemIdentifierException, DatabaseConnectionException{
		return this.inventoryDbHandler.fetchItem(itemID, quantity);
	}
	
	/**
	 * Fetches a <code>Discount</code> from the <code>DiscountDbHandler</code>.
	 * @param customerID  The unique integer customer identifier.
	 * @param totalPrice  Total price of the items currently in the sale.
	 * @param items  The full list of items to-be-purchased.
	 * @return discount   The discount(s) to be applied to the sale.
	 */
	public Discount fetchDiscount(int customerID, double totalPrice, ArrayList<ItemDTO> items) {
		return this.discountDbHandler.requestDiscount(customerID, totalPrice, items);
	}
	
	/**
	 * Updates the inventory with information about which items were sold through the <code>InventoryDbHandler</code>.
	 * @param items  The complete list of items that were sold.
	 */
	public void updateInventory(ArrayList<Item> items) {
		this.inventoryDbHandler.updateInventory(items);
	}
	
	/**
	 * Sends a sale DTO to the <code>AccountingDbHandler</code> so that the handler can
	 * log the sale in the external database.
	 * @param saleDTO  Information about the sale which just transpired.
	 */
	public void updateAccounting(SaleDTO saleDTO) {
		this.accountingDbHandler.updateAccountingSystem(saleDTO);
	}
	
	
}
