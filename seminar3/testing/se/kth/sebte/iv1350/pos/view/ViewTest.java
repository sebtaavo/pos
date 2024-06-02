package se.kth.sebte.iv1350.pos.view;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.sebte.iv1350.pos.controller.Controller;
import se.kth.sebte.iv1350.pos.integration.DatabaseConnectionException;
import se.kth.sebte.iv1350.pos.integration.DbHandler;
import se.kth.sebte.iv1350.pos.integration.ItemIdentifierException;

class ViewTest {
	private DbHandler dbHandler;
	private Controller contr;
	private View view;
	
	@BeforeEach
	void setup() {
		dbHandler = new DbHandler();
		contr = new Controller(dbHandler);
		view = new View(contr);
	}

	@Test
	void testStartSale() {
		view.startSale();
		//Test that a sale was initiated
		boolean wasInitialized = (contr.getCurrentSale() != null);
		assertEquals(wasInitialized, true, "Failed to initialize a Sale");
		
		//Test that we registered two observers to the sale
		assertEquals(contr.getCurrentSale().observerListLength(), 2, "Unexpected number of observers in Sale.");
		
		//Test that the controller initialized a Basket.
		wasInitialized = (contr.getCurrentSale().getBasket() != null);
		assertEquals(wasInitialized, true, "Failed to initialize a Basket in the Sale.");
	}

	@Test
	void testScanItem() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(outContent));
		
		view.startSale();
		//Test that an ItemIdentifierException is thrown when we scan an item with a negative ID:
		view.scanItem(69, 2);
		
		//Save the time for use later when checking the exception log.
		LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = timestamp.format(formatter);
		
        //Test that the exception was generated
		assertEquals(view.getExceptions().size(), 1, "Did not generate an exception");
		
		//Given that it was generated, test that its of the correct type.
		if(view.getExceptions().size() == 1) {
			boolean isItemIdentifierException = (view.getExceptions().get(0) instanceof ItemIdentifierException);
			assertEquals(isItemIdentifierException, true, "Generated exception not of type ItemIdentifierException. Expected otherwise.");
		}
		
		//Test that we printed an error message to the user.
		assertTrue(outContent.toString().contains("Barcode did not yield a valid item identifier. Re-scan or enter product manually"), "Failed to print item identifier exception message to user.");
		
		//Test that we wrote the exception to the log file.
		try {
			String logContent = new String(Files.readAllBytes(Paths.get("logs/ExceptionLog.txt")));
	        String logEntry = "Item ID 69 could not be found in the database";
			assertTrue(logContent.contains(logEntry), "Log did not contain the expected exception.");
		}catch(IOException ie) {
			ie.printStackTrace();
			System.out.println("Failed to access exception log when unit testing View.");
		}
		
		//Test that we didn't add an item to the basket, since we generated an exception.
		boolean emptyBasket = (view.contr.getCurrentSale().getBasket().getItemList().size() == 0);
		assertTrue(emptyBasket, "Generated an item despite an exception occuring.");
		
		//Test the same for a database connection exception, hard coded to item id 3:
		//Reset the framework
		dbHandler = new DbHandler();
		contr = new Controller(dbHandler);
		view = new View(contr);
		
		view.startSale();
		view.scanItem(3, 2);
		
		//Test
		//Save the time for use later when checking the exception log.
		timestamp = LocalDateTime.now();
        formattedTime = timestamp.format(formatter);
        
        //Test that the exception was generated
        assertEquals(view.getExceptions().size(), 1, "Item ID 3 did not generate an exception.");
        
        //Given that it was generated, test that its of the correct type.
       	if(view.getExceptions().size() == 1) {
       		boolean isDatabaseConnectionException = (view.getExceptions().get(0) instanceof DatabaseConnectionException);
      		assertEquals(isDatabaseConnectionException, true, "Generated exception not of type DatabaseConnectionException. Expected otherwise.");
      	}
       	
        //Test that we printed an error message to the user.
      	assertTrue(outContent.toString().contains("Failed to connect to the inventory server. Check internet connection or status of inventory server."), "Failed to print exception database exception message to user.");
      	System.setOut(originalOut);
      	
        //Test that we wrote the exception to the log file.
      	try {
			String logContent = new String(Files.readAllBytes(Paths.get("logs/ExceptionLog.txt")));
	        String logEntry = "[" + formattedTime + "] " + "Failed to connect to external inventory server when fetching item data. Verify internet connection or status of server.";
			assertTrue(logContent.contains(logEntry));
      		}catch(IOException ie) {
      			ie.printStackTrace();
      			System.out.println("Failed to access exception log when unit testing View.");
      		}
      	
      	//Test that we didn't add an item to the basket, since an exception was generated.
      	emptyBasket = (view.contr.getCurrentSale().getBasket().getItemList().size() == 0);
      	assertTrue(emptyBasket, "Generated an item despite an exception occuring.");
		
	}

	@Test
	void testRequestDiscount() {
		view.startSale();
		view.scanItem(1, 2);
		view.scanItem(2, 1);
		view.endSale();
		view.requestDiscount(1337);
		
		//Test that a discount was delivered to the sale object.
		boolean generated = view.contr.getCurrentSale().appliedDiscount != null;
		assertTrue(generated, "Discount was not delivered to the sale object.");
		
		//Test that the view received a total price to present to the customer.
		boolean generatedPrice = (view.totalPrice != 0);
		assertTrue(generatedPrice, "Discount was not delivered to the sale object.");
	}

	@Test
	void testPresentPayment() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(outContent));
		
		view.startSale();
		view.scanItem(1, 2);
		view.scanItem(2, 1);
		view.endSale();
		view.requestDiscount(1337);
		view.presentPayment(500);
		
		//Test that we printed a receipt.
		assertTrue(outContent.toString().contains("------------------ Begin receipt-------------------"), "Receipt was not printed to console.");
		System.setOut(originalOut);
		
		
		//Test that the user gets to see how much change to return to the customer.
		double expectedResult = 500 - (view.contr.getCurrentSale().totalCostAndVAT.total + view.contr.getCurrentSale().totalCostAndVAT.VAT);
		assertEquals(view.change, expectedResult, "Received unexpected amount of change to give the customer in view.");
	}

}
