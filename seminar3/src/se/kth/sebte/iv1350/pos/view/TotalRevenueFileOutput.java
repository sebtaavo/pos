package se.kth.sebte.iv1350.pos.view;

import se.kth.sebte.iv1350.pos.util.IncomeObserver;
import se.kth.sebte.iv1350.pos.util.Logger;

public class TotalRevenueFileOutput implements IncomeObserver{
	private double totalIncomeSinceLaunch;
	public TotalRevenueFileOutput() {
		this.totalIncomeSinceLaunch = 0;
	}
	
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
