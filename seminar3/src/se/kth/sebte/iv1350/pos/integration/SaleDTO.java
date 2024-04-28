package se.kth.sebte.iv1350.pos.integration;

import java.time.LocalDateTime;

public class SaleDTO {
	LocalDateTime timeOfSale;
	double grossPrice;
	double netPrice;
	public SaleDTO(double grossPrice, double netPrice) {
		timeOfSale = LocalDateTime.now();
		this.grossPrice = grossPrice;
		this.netPrice = netPrice;
	}
}
