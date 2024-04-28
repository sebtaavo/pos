package se.kth.sebte.iv1350.pos.integration;

import java.time.LocalDateTime;
import java.util.ArrayList;

import se.kth.sebte.iv1350.pos.model.Receipt;
import se.kth.sebte.iv1350.pos.model.Sale;

public class PrinterHandler {
	private Receipt receipt;
	public PrinterHandler() {
		
	}
	public void createReceipt(Sale currentSale, double amount, double change) {
		this.receipt = new Receipt();
		this.receipt.addTimeOfSaleLine(LocalDateTime.now());
		this.receipt.addItemLines(currentSale.getBasket().getItemList());
		this.receipt.addTotalLine(currentSale.getBasket().getGrossTotal().total);
		this.receipt.addDiscountLine(currentSale.appliedDiscount);
		this.receipt.addTotalAfterDiscountLine(currentSale.totalCostAndVAT.total);
		this.receipt.addVATLine(currentSale.totalCostAndVAT.VAT);
		this.receipt.addPaymentLine(amount);
		this.receipt.addChangeLine(change);
		this.receipt.addEndLine();
		
		this.sendReceiptToPrinter(this.receipt);
		this.printReceiptToConsole();
	}
	
	private void sendReceiptToPrinter(Receipt receipt) {
		//This method contacts the external printer. Not possible to implement at this moment.
	}
	
	private void printReceiptToConsole() {
		ArrayList<String> receiptLines = this.receipt.getReceiptLines();
		for(int i = 0; i < receiptLines.size(); i++) {
			System.out.println(receiptLines.get(i));
		}
	}

}
