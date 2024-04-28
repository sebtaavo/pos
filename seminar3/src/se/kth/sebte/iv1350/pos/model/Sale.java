package se.kth.sebte.iv1350.pos.model;

public class Sale{
	private Basket basket;
	public Cost totalCostAndVAT;
	public Discount appliedDiscount;
	
	public Sale() {
		basket = new Basket();
	}
	
	
	public Basket scanItem(Item item){
		if(item != null) {
			this.basket.updateBasket(item);
		}
		return this.basket;
	}
	
	public double applyDiscount(Discount discount) {
		this.appliedDiscount = discount;
		this.totalCostAndVAT = this.basket.getGrossTotal();
		this.totalCostAndVAT.total = this.totalCostAndVAT.total - (discount.itemListDiscount);
		this.totalCostAndVAT.total = this.totalCostAndVAT.total * (1 - discount.customerIDDiscount/100);
		this.totalCostAndVAT.total = this.totalCostAndVAT.total * (1 - discount.totalCostDiscount/100);
		
		return this.totalCostAndVAT.total;
	}
	
	public Basket getBasket() {
		return this.basket;
	}
	
	
	
}
