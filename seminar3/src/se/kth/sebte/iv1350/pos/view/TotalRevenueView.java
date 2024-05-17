package se.kth.sebte.iv1350.pos.view;

import se.kth.sebte.iv1350.pos.util.IncomeObserver;

/**
 * 
 * @author sebastian taavo ek
 * Class which keeps track of a running total of income made from sales during runtime of the program, and displays it to the user.
 * In the absence of a real display, it simply prints this information to the standard System output stream.
 *
 */
public class TotalRevenueView implements IncomeObserver{
	private double totalIncomeSinceLaunch;

	/**
	 * Constructor for the class.
	 */
	public TotalRevenueView() {

	}
	
	/**
	 * Implemented method from the <code>IncomeObserver</code> interface. Is called when this class is notified of a new sale being made
	 * by the observed object. Updates the running total and notifies the user of the new running total.
	 */
	@Override
	public void newSale(double payment) {
		this.totalIncomeSinceLaunch = totalIncomeSinceLaunch + payment;
		System.out.println("New total revenue since program start: " + totalIncomeSinceLaunch);
	}
	
}
