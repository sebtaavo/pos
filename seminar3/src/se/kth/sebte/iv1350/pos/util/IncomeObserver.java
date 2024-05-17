package se.kth.sebte.iv1350.pos.util;
/**
 * 
 * @author sebastian taavo ek
 * An Observer used for the observer pattern in this program. Is implemented by <code>TotalRevenueFileOutput</code> and <code>TotalRevenueView</code>.
 * Used to register a new total sum from sales while the program is running.
 *
 */
public interface IncomeObserver {
	/**
	 * Notifies any class which implements this interface of a new sale being completed, and how much revenue it generated.
	 * @param payment The new revenue to add to the sum of revenue.
	 */
	void newSale(double payment);
}
