package se.kth.sebte.iv1350.pos.view;
import java.util.ArrayList;

import se.kth.sebte.iv1350.pos.controller.Controller;
import se.kth.sebte.iv1350.pos.integration.BasketDTO;
import se.kth.sebte.iv1350.pos.integration.DatabaseConnectionException;
import se.kth.sebte.iv1350.pos.integration.ItemIdentifierException;
import se.kth.sebte.iv1350.pos.integration.ScanResult;
import se.kth.sebte.iv1350.pos.util.Logger;

/**
 * 
 * @author sebastian taavo ek
 * The <code>View</code> class represents the interactive cash register and display that a cashier
 * uses at a point-of-sale in a retail store. It takes in an object of type  <code>Controller</code>,
 * through which it sends and reads information to and from the model layer of the design.
 * 
 */
public class View {
	Controller contr;
	/**
	 * <code>Basket</code> object containing information about the current sale. 
	 * Is continuously updated upon each new item scan.
	 */
	BasketDTO basket;
	double totalPrice;
	double change;
	private TotalRevenueView revenueView;
	private TotalRevenueFileOutput revenueFileOutput;
	/**
	 * This is list of exceptions is only used for unit testing that they execute correctly.
	 */
	private ArrayList<Exception> exceptions;
	
	/**
	 * Creates a new <code>View</code> object to give input to the sale process at a retail store.
	 * 
	 * @param contr  The <code>Controller</code> object which mediates information exchange between the <code>View</code>
	 * and the model.
	 */
	
	public View(Controller contr) {
		this.contr = contr;
		this.revenueFileOutput = new TotalRevenueFileOutput();
		this.revenueView = new TotalRevenueView();
		contr.addIncomeObserver(revenueFileOutput);
		contr.addIncomeObserver(revenueView);
		exceptions = new ArrayList<Exception>();
	}
	
	/**
	 * Tells the <code>Controller</code> to start a new <code>Sale</code>.
	 */
	
	public void startSale() {
		this.contr.startSale();
	}
	
	/**
	 * Prints the total cost before discounts, marking the end of the sale.
	 */
	
	public void endSale() {
		System.out.println("End sale: ");
		String line = "Total cost before discounts (incl VAT): " + String.valueOf(this.basket.getGrossTotal().total + this.basket.getGrossTotal().VAT + "\n");
		System.out.println(line);
		
	}
	
	/**
	 * Adds a new item of a specified quantity to the sale through a bar-code scan.
	 * Also updates the <code>View</code>'s information about the current basket.
	 * Checks for exceptions if the item identifier was invalid or not found in the database,
	 * as well as if the database connection failed to establish.
	 * 
	 * @param itemID  The integer item ID obtained from scanning a product bar-code.
	 * @param quantity  The user-inputed amount of the scanned item.
	 */
	public void scanItem(int itemID, int quantity) {
		try {
			ScanResult scanResult = contr.scanItem(itemID, quantity);
			this.basket = scanResult.basket;
			this.printScanToConsole(scanResult);
		}catch(ItemIdentifierException e1) {
			this.exceptions.add(e1);
			e1.printStackTrace();
			Logger.log(e1.getMessage(), "logs/ExceptionLog.txt", true);
			System.out.println("Barcode did not yield a valid item identifier. Re-scan or enter product manually.");
		}catch(DatabaseConnectionException e2) {
			this.exceptions.add(e2);
			e2.printStackTrace();
			Logger.log(e2.getMessage(), "logs/ExceptionLog.txt", true);
			System.out.println("Failed to connect to the inventory server. Check internet connection or status of inventory server.");
		}
		
	}
	
	/**
	 * Signals to the <code>Controller</code> that a discount needs to be searched for and/or applied.
	 * 
	 * @param customerID User-inputed integer unique to each customer. Given by the customer, entered by the cashier.
	 */
	
	public void requestDiscount(int customerID) {
		this.totalPrice = this.contr.requestDiscount(customerID);
	}
	
	/**
	 * Signals to the <code>Controller</code> how much the customer has paid for the sale, receiving information
	 * about the amount of change the customer is owed in return. Also prints information to the console 
	 * about the updated databases, seeing as the transaction is now finalized when the products have changed hands.
	 * 
	 * @param amount  the amount of money that the customer has paid for the sale.
	 */
	
	public void presentPayment(double amount) {
		System.out.println("Customer pays " + String.valueOf(amount));
		System.out.println("Sent sale info to external accounting system");
		for(int i = 0; i < this.basket.getItemList().size(); i++) {
			System.out.println("\nTold external inventory system to decrease inventory quantity");
			System.out.printf("of item %d by %d units.", this.basket.getItemList().get(i).itemID, this.basket.getItemList().get(i).quantity);
		}
		System.out.println("");
		this.change = contr.acceptPayment(amount);
	}
	
	/**
	 * Takes in a <code>ScanResult</code> object, which contains a <code>Basket</code> of the current sale,
	 * the last scanned <code>Item</code>, and a boolean telling the code whether or not the bar-code scan was valid.
	 * 
	 * Prints information to the console concerning said scan and the involved item found through the bar-code.
	 * 
	 * @param scanResult  data transfer object containing information about the current <code>Basket</code> and the last
	 * scanned <code>Item</code>.
	 */
	
	private void printScanToConsole(ScanResult scanResult) {
		String line = "Added " + String.valueOf(scanResult.item.quantity) + " item(s) with ID " + String.valueOf(scanResult.item.itemID) + " :";
		System.out.println(line);
		line = "Item ID: " + String.valueOf(scanResult.item.itemID);
		System.out.println(line);
		line = "Item name: " + scanResult.item.name;
		System.out.println(line);
		line = "Item cost: " + scanResult.item.price + " SEK";
		System.out.println(line);
		line = "VAT: " + scanResult.item.VAT + "%";
		System.out.println(line);
		line = "Item description: " + scanResult.item.description + "\n";
		System.out.println(line);
		
		line = "Total cost (incl VAT): " + String.valueOf(this.basket.getGrossTotal().total + this.basket.getGrossTotal().VAT);
		System.out.println(line);
		line = "Total VAT: " + String.valueOf(this.basket.getGrossTotal().VAT) + "\n";
		System.out.println(line);
		
	}
	
	/**
	 * Getter for the list of exceptions collected by this instance of the object,
	 * is only used for unit testing.
	 */
	public ArrayList<Exception> getExceptions() {
		return this.exceptions;
	}

}
