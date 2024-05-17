package se.kth.sebte.iv1350.pos.integration;

/**
 * 
 * @author sebastian taavo ek
 * This is an exception sub-class for whenever the program finds a faulty bar-code scan which does not yield an itemDTO
 * in the external inventory database.
 * This is generated when the scanItem() method in View is called with a parameter < 0 or greater than 3, since we have only
 * hard-coded in results for the item identifiers 1 and 2, in the absence of a real external database.
 */
public class ItemIdentifierException extends Exception {

	/**
	 * Constructor for the exception.
	 * @param message The message that describes the exception.
	 */
	public ItemIdentifierException(String message) {
		super(message);
		
	}

}
