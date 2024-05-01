package se.kth.sebte.iv1350.pos.controller;
import se.kth.sebte.iv1350.pos.integration.DbHandler;
import se.kth.sebte.iv1350.pos.integration.PrinterHandler;
import se.kth.sebte.iv1350.pos.integration.SaleDTO;
import se.kth.sebte.iv1350.pos.integration.ScanResult;
import se.kth.sebte.iv1350.pos.model.Basket;
import se.kth.sebte.iv1350.pos.model.Discount;
import se.kth.sebte.iv1350.pos.model.Item;
import se.kth.sebte.iv1350.pos.model.Sale;

/**
 * 
 * @author sebastian taavo ek
 * Class representing the "controller" layer of our design. Responsible for mediating information
 * share between the <code>View</code> and the integration layer as well as model.
 *
 */
public class Controller {
	public Basket basket;
	private Sale currentSale;
	private DbHandler dbHandler;
	private PrinterHandler printerHandler;
	
	/**
	 * Creates an instance of this object.
	 * @param dbHandler  A database handler responsible for communicating with all other databases.
	 */
	public Controller(DbHandler dbHandler) {
		this.dbHandler = dbHandler;
		this.basket = new Basket();
		this.printerHandler = new PrinterHandler();
	}
	
	
	/**
	 * Signals that a sale has started. Creates a new <code>Sale</code> object.
	 */
	public void startSale() {
		currentSale = new Sale();
	}
	
	/**
	 * Fetches an item from a bar-code scan. Returns that item, updated basket, and "valid scan" boolean to the <code>View</code>.
	 * @param itemID  The item identifier obtained through a bar-code scan.
	 * @param quantity  The amount of the item being purchased.
	 * @return scanResult  The result of the bar-code scan.
	 */
	public ScanResult scanItem(int itemID, int quantity) {
		boolean validScan = true;
		Item item = this.dbHandler.fetchItem(itemID, quantity);
		this.basket = this.currentSale.scanItem(item);
		if(item == null) {
			validScan = false;
		}
		ScanResult scanResult = new ScanResult(this.basket, item, validScan);
		return scanResult;
	}
	
	/**
	 * Lets the databases know how much money was paid by the customer, and contacts the model to find out how
	 * much change the customer is owed.
	 * This method also lets the printer know that its time to print a receipt, since we now know how much money
	 * was paid and how much change is paid back.
	 * @param amount  Amount of money paid by the customer.
	 * @return change   Amount of money the customer is owed by the cashier in return.
	 */
	public double acceptPayment(double amount) { 
		this.dbHandler.updateInventory(currentSale.getBasket().getItemList());
		SaleDTO saleDTO = new SaleDTO(currentSale.totalCostAndVAT.total, currentSale.totalCostAndVAT.VAT);
		this.dbHandler.updateAccounting(saleDTO);
		double change = amount - (currentSale.totalCostAndVAT.total + currentSale.totalCostAndVAT.VAT);
		this.createReceipt(currentSale, amount, change);

		return change;
	}
	
	private void createReceipt(Sale currentSale, double amount, double change) {
		this.printerHandler.createReceipt(currentSale, amount, change);
	}
	
	/**
	 * Requests a <code>Discount</code> from the <code>DbHandler</code>, and then applies it to the current sale.
	 * If there was no <code>Discount</code> found in the database, all attributes are 0-values.
	 * @param customerID  Unique integer customer identifier.
	 * @return totalAfterDiscount  The total cost that the customer needs to pay after the discount is applied, excluding VAT.
	 */
	public double requestDiscount(int customerID) {
		Discount discount = this.dbHandler.fetchDiscount(customerID, currentSale.getBasket().getGrossTotal().total, basket.getItemList());
		double totalAfterDiscount = this.currentSale.applyDiscount(discount);
		return totalAfterDiscount;
	}
}

