package se.kth.sebte.iv1350.pos.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author sebastian taavo ek
 * The <code>Receipt</code> class is responsible for creating and holding the <code>String</code>
 * array-list which contains information about the <code>Sale</code>. The <code>Receipt</code>
 * can build its text in any order, and the <code>PrinterHandler</code> class is responsible
 * for calling the methods which create the text lines in whichever order the design demands.
 */

public class Receipt {
	private ArrayList<String> receiptLines;
	/**
	 * Creates a new <code>Receipt</code> object.
	 */
	public Receipt() {
		receiptLines = new ArrayList<String>();
		receiptLines.add("------------------ Begin receipt-------------------");
	}
	
	/**
	 * Creates the line in the receipt documenting the time of the sale.
	 * @param timeOfSale  <code>LocalDateTime</code> object containing the time of the sale.
	 */
	public void addTimeOfSaleLine(LocalDateTime timeOfSale) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String formattedTime = timeOfSale.format(formatter);
		receiptLines.add("Time of sale: " + formattedTime+"\n");
	}
	
	/**
	 * Creates one line in the receipt for each item contained in the basket of the sale. Each line
	 * contains information regarding the individual as well as total price.
	 * @param items The <code>Item</code> array-list contained in the <code>Basket</code>
	 * of the current <code>Sale</code>.
	 */
	public void addItemLines(ArrayList<Item> items) {
		for(int i = 0; i < items.size(); i++) {
			String itemRow = "";
			String namePart = fetchNamePartOfItem(items.get(i));
			String tallyPart = fetchTallyPartOfItem(items.get(i));
			String totalPart = fetchTotalPartOfItem(items.get(i));
			
            namePart = String.format("%-25s", namePart.substring(0, Math.min(namePart.length(), 25)));
            tallyPart = String.format("%-12s", tallyPart.substring(0, Math.min(tallyPart.length(), 12)));
            totalPart = String.format("%-15s", totalPart);
            itemRow = namePart + tallyPart + totalPart;
            this.receiptLines.add(itemRow);
		}
	}
	
	/**
	 * Adds a line to the receipt containing the total price of the sale in SEK.
	 * @param total   The total price owed by the customer.
	 */
	public void addTotalLine(double total) {
		String totalRow = "\nTotal: ";
		String blankSpace = " ".repeat(30);
		totalRow += blankSpace;
		totalRow = totalRow + String.valueOf(total) + " SEK";
		
		this.receiptLines.add(totalRow);
	}
	
	/**
	 * Adds a line to the receipt containing the total price of the sale in SEK, after discounts.
	 * @param totalAfterDiscount  The total price after discounts have been applied.
	 */
	public void addTotalAfterDiscountLine(String totalAfterDiscount) {
		String totalRow = "Total after discounts: ";
		String blankSpace = " ".repeat(14);
		totalRow += blankSpace;
		totalRow = totalRow + totalAfterDiscount + " SEK";
		
		this.receiptLines.add(totalRow);
	}
	
	/**
	 * Adds a line to the receipt containing the three types of discount that can be applied to the sale.
	 * @param appliedDiscount  The applied <code>Discount</code> stored in the <code>Sale</code>.
	 */
	public void addDiscountLine(Discount appliedDiscount) {
		String totalRow = "Discounted: -";
		totalRow = totalRow + String.valueOf(appliedDiscount.customerIDDiscount) + " %, -" 
		+ String.valueOf(appliedDiscount.totalCostDiscount) + " %, -"
		+ String.valueOf(appliedDiscount.itemListDiscount) +" SEK";
	
		this.receiptLines.add(totalRow);
	}
	
	/**
	 * Adds a line to the receipt containing the VAT amount for this purchase.
	 * @param VAT   The VAT value to be printed.
	 */
	public void addVATLine(String VAT) {
		String totalRow = "VAT: ";
		totalRow = totalRow + VAT + " SEK\n";
		
		this.receiptLines.add(totalRow); 
	}
	
	/**
	 * Adds a line to the receipt containing the information of how much the customer has paid.
	 * @param amountPaid  The amount the customer pays.
	 */
	public void addPaymentLine(double amountPaid) {
		String totalRow = "Cash: ";
		String blankSpace = " ".repeat(31);
		totalRow += blankSpace;
		totalRow = totalRow + String.valueOf(amountPaid) + " SEK";
		
		this.receiptLines.add(totalRow);
	}
	
	/**
	 * Adds a line to the receipt showing the amount of change that the customer is owed.
	 * @param change  The amount of change owed.
	 */
	public void addChangeLine(double change) {
		String totalRow = "Change: ";
		String blankSpace = " ".repeat(29);
		totalRow += blankSpace;
		totalRow = totalRow + String.valueOf(change) + " SEK";
		
		this.receiptLines.add(totalRow); 
	}
	
	
	/**
	 * Adds a final line to the receipt for esthetic purposes.
	 */
	public void addEndLine() {
		this.receiptLines.add("------------------ End receipt---------------------");
	}
	
	private String fetchNamePartOfItem(Item item) {
		String namePart = item.name;
		if(namePart.length() > 25) {
			namePart = item.name.substring(0, 25);
		}
		return namePart;
	}
	
	private String fetchTallyPartOfItem(Item item) {
		String tallyPart = String.valueOf(item.quantity) + " x " + String.valueOf(item.price);
		return tallyPart;
	}
	
	private String fetchTotalPartOfItem(Item item) {
		String totalPart = String.valueOf(item.price * item.quantity) + " SEK";
		return totalPart;
	}
	
	/**
	 * Getter for the <code>String</code> array-list containing the full receipt information.
	 * @return receiptLines    Returns the <code>String</code> array list with the sale information.
	 */
	public ArrayList<String> getReceiptLines(){
		return receiptLines;
	}
	
}
