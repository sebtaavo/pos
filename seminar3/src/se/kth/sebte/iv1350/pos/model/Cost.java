package se.kth.sebte.iv1350.pos.model;

/**
 * 
 * @author sebastian taavo ek
 * This class holds information about the cost of a <code>Sale</code>.
 * It bunches the total VAT with the total price, and makes it easier
 * to pass this information in one neat package between methods and classes.
 */
public class Cost {
	public double total;
	public double VAT;
	
	/**
	 * Creates an instance of this class.
	 * @param total   The total cost of a sale.
	 * @param VAT   The total VAT of a sale.
	 */
	public Cost(double total, double VAT) {
		this.total = total;
		this.VAT = VAT;
	}

}
