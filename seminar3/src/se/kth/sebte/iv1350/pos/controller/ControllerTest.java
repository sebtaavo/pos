package se.kth.sebte.iv1350.pos.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.sebte.iv1350.pos.integration.DbHandler;
import se.kth.sebte.iv1350.pos.integration.ScanResult;
import se.kth.sebte.iv1350.pos.model.Cost;
import se.kth.sebte.iv1350.pos.model.Discount;
import se.kth.sebte.iv1350.pos.model.Item;


class ControllerTest {

	private Controller contr;
	private DbHandler dbHandler;
	
	@BeforeEach
	public void startUpTest() {
		dbHandler = new DbHandler();
		contr = new Controller(dbHandler);
	}
	
	
	@Test
	public void startSaleTest() {
		contr.startSale();
		//Test that start sale initialized the currentSale variable in Controller, and that the Sale object initialized a Basket.
		assertNotNull(contr.getCurrentSale());
		assertNotNull(contr.getCurrentSale().getBasket());
		
	}
	
	@Test
	public void scanItemTest() {
		//Test that an object of type Item is returned by the method
		contr.startSale();
		ScanResult scan1 = contr.scanItem(1, 1);
		boolean isItem = scan1.item instanceof Item;
		assertEquals(true, isItem);
		
		//Test that a null object is returned by the method if our item ID is invalid.
		contr.startSale();
		ScanResult scan2 = contr.scanItem(-1, 1);
		boolean isNull = (scan2.item == null);
		assertEquals(true, isNull);
		//Test that the validScan boolean was flagged as false as well.
		assertEquals(false ,scan2.validItemID);
		//Test that the basket was not updated with information since the item was null.
		int expectedResult = 0;
		assertEquals(expectedResult, contr.getCurrentSale().getBasket().getItemList().size());
		
	}
	
	@Test
	public void requestDiscountTest() {
		contr.startSale();
		contr.scanItem(1, 1);
		contr.scanItem(2, 1);
		double total = contr.requestDiscount(1);
		//Test that the total amount for the customer to pay (excluding VAT) contains the correct amount. Expects 29,9 + 14,9 = 44,8.
		assertEquals(44.8, total);
		
		//Tests that the current sale has a Discount initialized.
		boolean isDiscount = contr.getCurrentSale().appliedDiscount instanceof Discount;
		assertEquals(true, isDiscount);
		
		//Tests that the current sale has a VAT and total price after discount calculated.
		boolean hasFinalCostCalculated = contr.getCurrentSale().totalCostAndVAT instanceof Cost;
		assertEquals(true, hasFinalCostCalculated);
		
	}
	
	@Test
	public void acceptPaymentTest() { 
		contr.startSale();
		contr.scanItem(1, 1);
		contr.scanItem(2, 1);
		contr.requestDiscount(1);
		
		//Test if a receipt was created and sent to the console.
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(outContent));
		double change = contr.acceptPayment(100);
		assertTrue(outContent.toString().contains("Begin receipt"));
		System.setOut(originalOut);
		
		//Test if change is calculated correctly. Should be 100 - ((29,9 + 14,9) + (1,794 + 0,894))
		assertEquals(100-29.9-14.9-1.794-0.894, change);
	}	

}
