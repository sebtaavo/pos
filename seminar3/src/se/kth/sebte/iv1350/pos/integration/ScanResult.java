package se.kth.sebte.iv1350.pos.integration;
import se.kth.sebte.iv1350.pos.model.Basket;

public class ScanResult {
	public Basket basket;
	public boolean validItemID;
	
	public ScanResult(Basket basket, boolean validItemID){
		this.basket = basket;
		this.validItemID = validItemID;
	}

}
