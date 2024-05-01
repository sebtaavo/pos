package se.kth.sebte.iv1350.pos.model;
import se.kth.sebte.iv1350.pos.integration.ItemDTO;

/**
 * @author sebastian taavo ek
 * <code>Item</code> represents a product article that has been scanned at a point of sale.
 * 
 */
public class Item {
	public String name;
	public int itemID;
	public int quantity;
	public String description;
	public double price;
	public double VAT;
	
	/**
	 * Creates an <code>Item</code> object.
	 * @param itemDTO   Takes in an ItemDTO object created by the <code>InventoryDbHandler</code>.
	 */
	public Item(ItemDTO itemDTO) {
		this.name = itemDTO.name;
		this.itemID = itemDTO.itemID;
		this.quantity = itemDTO.quantity;
		this.description = itemDTO.description;
		this.price = itemDTO.price;
		this.VAT = itemDTO.VAT;
	}
	
	/**
	 * Method to pass on the attributes of this item in a way where they can't be edited for this
	 * instance of the object by the caller.
	 * @return itemDTO  Returns a data transfer object of this <code>Item</code>.
	 */
	public ItemDTO getItemDTO() {
		ItemDTO itemDTO = new ItemDTO(this.name, this.itemID, this.quantity, this.description, this.price, this.VAT);
		return itemDTO;
	}
}
