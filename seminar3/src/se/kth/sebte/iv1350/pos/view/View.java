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
	
	public void endSale() {
		System.out.println("End sale: ");
		String line = "Total cost before discounts (incl VAT): " + String.valueOf(this.basket.getGrossTotal().total + this.basket.getGrossTotal().VAT + "\n");
		System.out.println(line);
		
	}
	
	public void scanItem(int itemID, int quantity) {
		ScanResult scanResult = contr.scanItem(itemID, quantity);
		if(scanResult.validItemID) {
			this.basket = scanResult.basket;
			this.printScanToConsole(scanResult);
		}
		else {
			System.out.println("Scan yielded invalid itemID. Basket content unchanged.");
		}
	}
	
	public void requestDiscount(int customerID) {
		this.totalPrice = this.contr.requestDiscount(customerID);
	}
	
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

}
