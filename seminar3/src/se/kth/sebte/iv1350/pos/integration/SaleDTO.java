package se.kth.sebte.iv1350.pos.integration;

import java.time.LocalDateTime;

/**
 * 
 * @author sebastian taavo ek
 * DTO used to pass information about the current sale to the external accounting system.
 */
public class SaleDTO {
	LocalDateTime timeOfSale;
	double total;
	double netPrice;
	double VAT;
	/**
	 * Creates the DTO.
	 * @param total  The total price paid for this sale.
	 * @param VAT  The total VAT paid for this sale.
	 */
	public SaleDTO(double total, double VAT) {
		this.timeOfSale = LocalDateTime.now();
		this.total = total;
		this.VAT = VAT;
	}
}
