package se.kth.sebte.iv1350.pos.controller;
import se.kth.sebte.iv1350.pos.integration.DbHandler;
import se.kth.sebte.iv1350.pos.integration.PrinterHandler;
import se.kth.sebte.iv1350.pos.integration.SaleDTO;
import se.kth.sebte.iv1350.pos.integration.ScanResult;
import se.kth.sebte.iv1350.pos.model.Basket;
import se.kth.sebte.iv1350.pos.model.Discount;
import se.kth.sebte.iv1350.pos.model.Item;
import se.kth.sebte.iv1350.pos.model.Sale;

public class Controller {
	public Basket basket;
	private Sale currentSale;
	private DbHandler dbHandler;
	private PrinterHandler printerHandler;
	
	public Controller(DbHandler dbHandler) {
		this.dbHandler = dbHandler;
		this.basket = new Basket();
		this.printerHandler = new PrinterHandler();
	}
	
	
	public void startSale() {
		currentSale = new Sale();
	}
	
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
	
	public double acceptPayment(double amount) { //view receives information of how much change the customer is owed
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
	
	public double requestDiscount(int customerID) { //view receives information about how much the prices is discounted
		Discount discount = this.dbHandler.fetchDiscount(customerID, currentSale.getBasket().getGrossTotal().total, basket.getItemList());
		double totalAfterDiscount = this.currentSale.applyDiscount(discount);
		return totalAfterDiscount;
	}
}

