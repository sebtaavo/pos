package se.kth.sebte.iv1350.pos.integration;

/**
 * 
 * @author sebastian taavo ek
 * A DTO used to create objects of the type <code>Item</code>. Fetched from the <code>InventoryDbHandler</code> using an itemID.
 *
 */
public class ItemDTO {
	public String name;
	public int itemID;
	public int quantity;
	public String description;
	public double price;
	public double VAT;
	
	/**
	 * Creates an ItemDTO.
	 * @param name  The name of the <code>Item</code>.
	 * @param itemID   The integer ID of the <code>Item</code>.
	 * @param quantity  The quantity of this <code>Item</code> to be registered to the current <code>Sale</code>.
	 * @param description  A <code>String</code> description of the <code>Item</code>.
	 * @param price  The price of the <code>Item</code>.
	 * @param VAT   The VAT of this <code>Item</code>.
	 */
	public ItemDTO(String name, int itemID, int quantity, String description, double price, double VAT) {
		this.name = name;
		this.itemID = itemID;
		this.quantity = quantity;
		this.description = description;
		this.price = price;
		this.VAT = VAT;
	}

}
