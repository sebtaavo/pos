package se.kth.sebte.iv1350.pos.view;
import se.kth.sebte.iv1350.pos.controller.Controller;
import se.kth.sebte.iv1350.pos.integration.ScanResult;
import se.kth.sebte.iv1350.pos.model.Basket;

public class View {
	Controller contr;
	Basket basket;
	double totalPrice;
	double change;
	
	public View(Controller contr) {
		this.contr = contr;
	}
	
	public void startSale() {
		this.contr.startSale();
	}
	
	public void scanItem(int itemID, int quantity) {
		ScanResult scanResult = contr.scanItem(itemID, quantity);
		if(scanResult.validItemID) {
			System.out.println("Scan yielded valid itemID. Added to basket.");
		}
		else {
			System.out.println("Scan yielded invalid itemID. Basket content unchanged.");
		}
		this.basket = scanResult.basket;
	}
	
	public void requestDiscount(int customerID) {
		this.totalPrice = this.contr.requestDiscount(customerID);
	}
	
	public void presentPayment(double amount) {
		this.change = contr.acceptPayment(amount);
		
	}

}
