package se.kth.sebte.iv1350.pos.controller;
import java.util.ArrayList;
import java.util.List;

import se.kth.sebte.iv1350.pos.integration.BasketDTO;
import se.kth.sebte.iv1350.pos.integration.DatabaseConnectionException;
import se.kth.sebte.iv1350.pos.integration.DbHandler;
import se.kth.sebte.iv1350.pos.integration.ItemIdentifierException;
import se.kth.sebte.iv1350.pos.integration.PrinterHandler;
import se.kth.sebte.iv1350.pos.integration.SaleDTO;
import se.kth.sebte.iv1350.pos.integration.ScanResult;
import se.kth.sebte.iv1350.pos.model.Discount;
import se.kth.sebte.iv1350.pos.model.Item;
import se.kth.sebte.iv1350.pos.model.Sale;
import se.kth.sebte.iv1350.pos.util.IncomeObserver;

/**
 * 
 * @author sebastian taavo ek
 * Class representing the "controller" layer of our design. Responsible for mediating information
 * share between the <code>View</code> and the integration layer as well as model.
 *
 */
public class Controller {
	private BasketDTO basket;
	private Sale currentSale;
	private DbHandler dbHandler;
	private PrinterHandler printerHandler;
	private List<IncomeObserver> incomeObservers = new ArrayList<>();
	
	/**
	 * Creates an instance of this object.
	 * @param dbHandler  A database handler responsible for communicating with all other databases.
	 */
	public Controller(DbHandler dbHandler) {
		this.dbHandler = dbHandler;
		this.printerHandler = new PrinterHandler();
	}
	
	
	/**
	 * Signals that a sale has started. Creates a new <code>Sale</code> object.
	 */
	public void startSale() {
		currentSale = new Sale();
		for(IncomeObserver obs: incomeObservers) {
			currentSale.addIncomeObserver(obs);
		}
		this.basket = new BasketDTO(currentSale.getBasket());
	}
	
	/**
	 * Fetches an item from a bar-code scan. Returns that item, updated basket, and "valid scan" boolean to the <code>View</code>.
	 * @param itemID  The item identifier obtained through a bar-code scan.
	 * @param quantity  The amount of the item being purchased.
	 * @return scanResult  The result of the bar-code scan.
	 */
	public ScanResult scanItem(int itemID, int quantity) throws ItemIdentifierException, DatabaseConnectionException{
		Item item = this.dbHandler.fetchItem(itemID, quantity);
		this.basket = this.currentSale.scanItem(item);
		ScanResult scanResult = new ScanResult(this.basket, item.getItemDTO());
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
		double change = amount - currentSale.acceptPayment();
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
	
	public void addIncomeObserver(IncomeObserver incomeObserver) {
		this.incomeObservers.add(incomeObserver);
	}
	
	/**
	 * Getter for the current sale. Only used in the unit testing of this class.
	 * @return currentSale   The current sale.
	 */
	public Sale getCurrentSale() {
		return currentSale;
	}
}

