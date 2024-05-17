package se.kth.sebte.iv1350.pos.view;

import se.kth.sebte.iv1350.pos.util.IncomeObserver;
import se.kth.sebte.iv1350.pos.util.Logger;

/**
 * 
 * @author sebastian taavo ek
 * Class which generates a file output of the running total sum of sales in the program. Updates the external txt file
 * every time a new sale is closed. Implements an <code>IncomeObserver</code> to be notified of when this occurs.
 *
 */
public class TotalRevenueFileOutput implements IncomeObserver{
	private double totalIncomeSinceLaunch;
	/**
	 * Constructor for this class. Sets the running total to 0.
	 */
	public TotalRevenueFileOutput() {
		this.totalIncomeSinceLaunch = 0;
	}
	/**
	 * Method called from the implemented interface, updates our running total and calls a <code>Logger</code> to document the sum
	 * in an external text document.
	 */
	@Override
	public void newSale(double payment) {
		this.totalIncomeSinceLaunch += payment;
		this.logTotalSinceLaunch();
	}
	
	private void logTotalSinceLaunch() {
		String entry = "Running total (incl. VAT): " + Double.toString(totalIncomeSinceLaunch) + " SEK";
		Logger.log(entry, "logs/CurrentSessionIncome.txt", false);
	}

}
