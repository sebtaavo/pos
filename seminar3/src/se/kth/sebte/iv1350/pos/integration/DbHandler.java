package se.kth.sebte.iv1350.pos.integration;
import java.util.ArrayList;

import se.kth.sebte.iv1350.pos.model.Discount;
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
	
	public Discount fetchDiscount(int customerID, double totalPrice, ArrayList<Item> items) {
		return this.discountDbHandler.requestDiscount(customerID, totalPrice, items);
	}
	
	public void updateInventory(ArrayList<Item> items) {
		this.inventoryDbHandler.updateInventory(items);
	}
}
