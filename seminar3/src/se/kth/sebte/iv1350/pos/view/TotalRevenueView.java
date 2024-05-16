package se.kth.sebte.iv1350.pos.view;

import se.kth.sebte.iv1350.pos.util.IncomeObserver;

public class TotalRevenueView implements IncomeObserver{
	private double totalIncomeSinceLaunch;

	public TotalRevenueView() {

	}

	@Override
	public void newSale(double payment) {
		this.totalIncomeSinceLaunch = totalIncomeSinceLaunch + payment;
		System.out.println("New total revenue since program start: " + totalIncomeSinceLaunch);
	}
	
}
