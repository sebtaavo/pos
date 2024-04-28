package se.kth.sebte.iv1350.pos.controller;
import se.kth.sebte.iv1350.pos.integration.DbHandler;
import se.kth.sebte.iv1350.pos.model.Basket;
import se.kth.sebte.iv1350.pos.model.Item;
import se.kth.sebte.iv1350.pos.model.Sale;

public class Controller {
	public Basket basket = new Basket();
	private Sale currentSale;
	private DbHandler dbHandler;
	
	public Controller(DbHandler dbHandler) {
		this.dbHandler = dbHandler;
	}
	
	
	public void startSale() {
		currentSale = new Sale();
	}
	
	public Basket scanItem(int itemID, int quantity) {
		Item item = this.dbHandler.fetchItem(itemID, quantity);
		this.basket = this.currentSale.scanItem(item);
		return this.basket;
	}
	
	public double endSale() { //view receives information of how much the customer needs to pay
		
	}
	
	public double acceptPayment(int paidAmount) { //view receives information of how much change the customer is owed
		
	}
	
	public double requestDiscount(int customerID) { //view receives information about how much the prices is discounted
		
	}
}

