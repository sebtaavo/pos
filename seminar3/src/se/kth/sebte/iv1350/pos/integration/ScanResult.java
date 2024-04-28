package se.kth.sebte.iv1350.pos.integration;
import se.kth.sebte.iv1350.pos.model.Basket;
import se.kth.sebte.iv1350.pos.model.Item;

public class ScanResult {
	public Basket basket;
	public boolean validItemID;
	public Item item;;
	
	public ScanResult(Basket basket, Item item, boolean validItemID){
		this.basket = basket;
		this.validItemID = validItemID;
		this.item = item;
	}

}
