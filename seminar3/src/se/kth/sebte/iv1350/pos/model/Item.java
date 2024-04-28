package se.kth.sebte.iv1350.pos.model;
import se.kth.sebte.iv1350.pos.integration.ItemDTO;

public class Item {
	public String name;
	public int itemID;
	public int quantity;
	public String description;
	public double price;
	public double VAT;
	
	public Item(ItemDTO itemDTO) {
		this.name = itemDTO.name;
		this.itemID = itemDTO.itemID;
		this.quantity = itemDTO.quantity;
		this.description = itemDTO.description;
		this.price = itemDTO.price;
		this.VAT = itemDTO.VAT;
	}
	
	public ItemDTO getItemDTO() {
		ItemDTO itemDTO = new ItemDTO(this.name, this.itemID, this.quantity, this.description, this.price, this.VAT);
		return itemDTO;
	}
}
