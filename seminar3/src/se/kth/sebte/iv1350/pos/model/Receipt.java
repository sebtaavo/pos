package se.kth.sebte.iv1350.pos.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Receipt {
	private ArrayList<String> receiptLines;
	
	public Receipt() {
		receiptLines = new ArrayList<String>();
		receiptLines.add("------------------ Begin receipt-------------------");
	}
	
	public void addTimeOfSaleLine(LocalDateTime timeOfSale) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String formattedTime = timeOfSale.format(formatter);
		receiptLines.add("Time of sale: " + formattedTime+"\n");
	}
	
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
	
	public void addTotalLine(double total) {
		String totalRow = "\nTotal: ";
		String blankSpace = " ".repeat(30);
		totalRow += blankSpace;
		totalRow = totalRow + String.valueOf(total) + " SEK";
		
		this.receiptLines.add(totalRow);
	}
	
	public void addTotalAfterDiscountLine(double totalAfterDiscount) {
		String totalRow = "Total after discounts: ";
		String blankSpace = " ".repeat(14);
		totalRow += blankSpace;
		totalRow = totalRow + String.valueOf(totalAfterDiscount) + " SEK";
		
		this.receiptLines.add(totalRow);
	}
	
	public void addDiscountLine(Discount appliedDiscount) {
		String totalRow = "Discounted: -";
		totalRow = totalRow + String.valueOf(appliedDiscount.customerIDDiscount) + " %, -" 
		+ String.valueOf(appliedDiscount.totalCostDiscount) + " %, -"
		+ String.valueOf(appliedDiscount.itemListDiscount) +" SEK";
	
		this.receiptLines.add(totalRow);
	}
	
	public void addVATLine(double VAT) {
		String totalRow = "VAT: ";
		totalRow = totalRow + String.valueOf(VAT) + " SEK\n";
		
		this.receiptLines.add(totalRow); 
	}
	
	public void addPaymentLine(double amountPaid) {
		String totalRow = "Cash: ";
		String blankSpace = " ".repeat(31);
		totalRow += blankSpace;
		totalRow = totalRow + String.valueOf(amountPaid) + " SEK";
		
		this.receiptLines.add(totalRow);
	}
	
	public void addChangeLine(double change) {
		String totalRow = "Change: ";
		String blankSpace = " ".repeat(29);
		totalRow += blankSpace;
		totalRow = totalRow + String.valueOf(change) + " SEK";
		
		this.receiptLines.add(totalRow); 
	}
	
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
	
	public ArrayList<String> getReceiptLines(){
		return receiptLines;
	}
	
}
