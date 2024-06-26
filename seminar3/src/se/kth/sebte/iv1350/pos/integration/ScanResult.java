package se.kth.sebte.iv1350.pos.integration;

/**
 * 
 * @author sebastian taavo ek
 * Class containing the results of each item bar-code scan, used to transfer multiple items 
 * of data across layers in the design at once. Contains information about whether or not the
 * scan went through well (validItemID), the current updated <code>Basket</code> for the view, and the
 * newest <code>Item</code> scanned in through the bar-code.
 */
public class ScanResult {
	/**
	 * A DTO containing information about the current contents of the basket in a <code>Sale</code>.
	 */
	public BasketDTO basket;
	/**
	 * A DTO containing information about the <code>Item</code> generated from an item identifier.
	 */
	public ItemDTO item;;
	
	/*
	 * Constructor for this object.
	 */
	public ScanResult(BasketDTO basket, ItemDTO item){
		this.basket = basket;
		this.item = item;
	}

}
