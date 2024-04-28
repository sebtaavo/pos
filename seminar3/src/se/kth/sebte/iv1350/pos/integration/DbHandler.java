package se.kth.sebte.iv1350.pos.integration;
import se.kth.sebte.iv1350.pos.model.Item;

public class DbHandler {
	private AccountingDbHandler accountingDbHandler;
	private InventoryDbHandler inventoryDbHandler;
	private DiscountDbHandler discountDbHandler;
	private PrinterHandler printerHandler;
	
	
	public DbHandler() {
		accountingDbHandler = new AccountingDbHandler();
		inventoryDbHandler = new InventoryDbHandler();
		discountDbHandler = new DiscountDbHandler();
		printerHandler = new PrinterHandler();
	}
	
	public Item fetchItem(int itemID, int quantity) {
		return this.inventoryDbHandler.fetchItem(itemID, quantity);
	}
}
