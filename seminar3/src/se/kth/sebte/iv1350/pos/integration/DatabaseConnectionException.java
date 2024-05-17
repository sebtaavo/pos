package se.kth.sebte.iv1350.pos.integration;
/**
 * 
 * @author sebastian taavo ek
 * This is an exception sub-class for whenever the program can't reach the external inventory database.
 * There is no way to test this currently since we don't have access to such a database, so this is simulated
 * by trying to access the hard-coded item identifier "3" when scanning an item.
 *
 */
public class DatabaseConnectionException extends Exception {
	
	/**
	 * Constructor for the exception.
	 * @param message The message to be included with the exception.
	 */
	public DatabaseConnectionException(String message) {
		super(message);
	}

}
