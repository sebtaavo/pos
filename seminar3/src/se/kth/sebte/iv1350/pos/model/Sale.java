package se.kth.sebte.iv1350.pos.model;

import java.util.ArrayList;
import java.util.List;

import se.kth.sebte.iv1350.pos.integration.BasketDTO;
import se.kth.sebte.iv1350.pos.util.IncomeObserver;

/**
 * 
 * @author sebastian taavo ek
 * The <code>Sale</code> class is a container for the current sale at a point-of-sale.
 * It contains the <code>Basket</code> object that we fill with items to be purchased,
 * as well as information about the total cost and VAT after discounts. It also stores
 * the information of exactly which <code>Discount</code> was applied to the sale.
 */

public class Sale{
	private Basket basket;
	private List<IncomeObserver> incomeObservers = new ArrayList<>();
	/**
	 * Stored public information about the total price and VAT to be paid for this purchase,
	 * after a discount has been applied.
	 * Is calculated upon the <code>View</code> telling the <code>Controller</code> to fetch
	 * a discount.
	 */
	public Cost totalCostAndVAT;
	/**
	 * Stored information about what discount, if any, was applied. If no discount was applied, all attributes are 0.
	 */
	public Discount appliedDiscount;
	
	/**
	 * Creates a new Sale object, which in turn creates a <code>Basket</code>.
	 */
	public Sale() {
		basket = new Basket();
	}
	
	
	/**
	 * Method to add a new item to the current basket. All logic is handled on the side of the <code>Basket</code> class.
	 * Returns the updated <code>Basket</code> object to the <code>Controller</code> which calls this method.
	 * 
	 * @param item  The item obtained through a new bar-code scan.
	 * @return this.basket  The updated basket containing all the items in our basket.
	 */
	public BasketDTO scanItem(Item item){
		if(item != null) {
			this.basket.updateBasket(item);
		}
		return this.basket.getBasketDTO();
	}
	
	/**
	 * Applies a discount to our current sale. If no discount was requested or no discount was found
	 * in the <code>DiscountDbHandler</code>, the input parameter has only 0-value attributes.
	 * 
	 * @param discount The <code>Discount</code> object containing numerical information about how much money to discount.
	 * @return this.totalCostAndVAT.total  The total SEK that the customer owes for the current sale, excluding VAT,
	 * after discounts have been applied.
	 */
	public double applyDiscount(Discount discount) {
		this.appliedDiscount = discount;
		this.totalCostAndVAT = this.basket.getGrossTotal();
		this.totalCostAndVAT.total = this.totalCostAndVAT.total - (discount.itemListDiscount);
		this.totalCostAndVAT.total = this.totalCostAndVAT.total * (1 - discount.customerIDDiscount/100);
		this.totalCostAndVAT.total = this.totalCostAndVAT.total * (1 - discount.totalCostDiscount/100);
		
		return this.totalCostAndVAT.total;
	}
	
	/**
	 * Getter for the <code>Basket</code> of the current sale. Used extensively by classes that 
	 * monitor the progress of the sale. This dependency is not very elegant and can almost certainly
	 * be improved in later versions.
	 * @return this.basket   The basket of the this <code>Sale</code>.
	 */
	public Basket getBasket() {
		return this.basket;
	}
	
	/**
	 * Adds an observer to this instance of the class, implemented by <code>TotalRevenueFileOutput</code> and <code>TotalRevenueView</code>
	 * to be notified when a new sale is completed, and register a new total sum of revenue.
	 * @param incomeObserver The observer to register to this class instance.
	 */
	public void addIncomeObserver(IncomeObserver incomeObserver) {
		this.incomeObservers.add(incomeObserver);
	}
	
	/**
	 * Called when the sale is completed and the customer has paid the cashier. Returns the cost so that
	 * a calculation can be made for the change. This method also notifies all observers of the new payment.
	 * @return cost  The total amount owed for this sale.
	 */
	public double acceptPayment() {
		double cost = totalCostAndVAT.total + totalCostAndVAT.VAT;
		this.notifyObservers(cost);
		return cost;
	}
	
	private void notifyObservers(double payment) {
		for(IncomeObserver obs: incomeObservers) {
			obs.newSale(payment);
		}
	}
	/**
	 * Getter for the observer list length. Used in the unit testing of the View.
	 * @return Length of the observer list.
	 */
	public int observerListLength() {
		return this.incomeObservers.size();
	}
	
	
	
}
