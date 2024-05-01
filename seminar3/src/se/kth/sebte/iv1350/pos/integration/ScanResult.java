package se.kth.sebte.iv1350.pos.integration;
import se.kth.sebte.iv1350.pos.model.Basket;
import se.kth.sebte.iv1350.pos.model.Item;

/**
 * 
 * @author sebastian taavo ek
 * Class containing the results of each item bar-code scan, used to transfer multiple items 
 * of data across layers in the design at once. Contains information about whether or not the
 * scan went through well (validItemID), the current updated <code>Basket</code> for the view, and the
 * newest <code>Item</code> scanned in through the bar-code.
 */
public class ScanResult {
	public Basket basket;
	public boolean validItemID;
	public Item item;;
	
	/*
	 * Creates a ScanResult object.
	 */
	public ScanResult(Basket basket, Item item, boolean validItemID){
		this.basket = basket;
		this.validItemID = validItemID;
		this.item = item;
	}

}
