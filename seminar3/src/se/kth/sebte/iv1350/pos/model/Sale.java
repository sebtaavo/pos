package se.kth.sebte.iv1350.pos.model;

public class Sale {
	private Basket basket;
	public Sale() {
		basket = new Basket();
	}
	
	
	public Basket scanItem(Item item){
		return basket.updateBasket(Item item);
	}
	
	
}
