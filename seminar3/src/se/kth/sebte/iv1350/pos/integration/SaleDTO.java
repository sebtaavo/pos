package se.kth.sebte.iv1350.pos.integration;

import java.time.LocalDateTime;

public class SaleDTO {
	LocalDateTime timeOfSale;
	double total;
	double netPrice;
	double VAT;
	public SaleDTO(double total, double VAT) {
		this.timeOfSale = LocalDateTime.now();
		this.total = total;
		this.VAT = VAT;
	}
}
