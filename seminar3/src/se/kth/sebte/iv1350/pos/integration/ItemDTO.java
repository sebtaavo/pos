package se.kth.sebte.iv1350.pos.integration;

public class ItemDTO {
	public String name;
	public int itemID;
	public int quantity;
	public String description;
	public double price;
	public double VAT;
	
	public ItemDTO(String name, int itemID, int quantity, String description, double price, double VAT) {
		this.name = name;
		this.itemID = itemID;
		this.quantity = quantity;
		this.description = description;
		this.price = price;
		this.VAT = VAT;
	}

}
