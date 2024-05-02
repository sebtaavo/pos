package se.kth.sebte.iv1350.pos.integration;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import se.kth.sebte.iv1350.pos.model.Receipt;
import se.kth.sebte.iv1350.pos.model.Sale;

/**
 * 
 * @author sebastian taavo ek
 * Class used to create the receipt once a sale has finished. In future iterations
 * it will be used to contact the external printer and send it a <code>String</code> array-list
 * with the receipt information contained in the <code>Receipt</code> object. For now, it prints to the system
 * console instead.
 */
public class PrinterHandler {
	private Receipt receipt;
	/**
	 * Creates a new PrinterHandler.
	 */
	public PrinterHandler() {
		
	}
	
	/**
	 * Creates a receipt using all the necessary information from the just-finished sale.
	 * At the end of the method, it sends the receipt to the external printer and
	 * prints the text to the system console.
	 * @param currentSale  The current sale. This could be changed to a SaleDTO, but we will not need the object again once the receipt is printed.
	 * @param amount  The amount paid by the customer.
	 * @param change  The change that the customer is owed.
	 */
	public void createReceipt(Sale currentSale, double amount, double change) {
		DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
		df.setRoundingMode(RoundingMode.HALF_UP);
		this.receipt = new Receipt();
		this.receipt.addTimeOfSaleLine(LocalDateTime.now());
		this.receipt.addItemLines(currentSale.getBasket().getItemList());
		this.receipt.addTotalLine(currentSale.getBasket().getGrossTotal().total);
		this.receipt.addDiscountLine(currentSale.appliedDiscount);
		String roundedDiscountedTotal = df.format(currentSale.totalCostAndVAT.total + currentSale.totalCostAndVAT.VAT);
		this.receipt.addTotalAfterDiscountLine(roundedDiscountedTotal);
		String roundedVAT = df.format(currentSale.totalCostAndVAT.VAT);
		this.receipt.addVATLine(roundedVAT);
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
