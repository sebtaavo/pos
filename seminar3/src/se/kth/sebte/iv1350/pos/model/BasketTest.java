package se.kth.sebte.iv1350.pos.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.sebte.iv1350.pos.integration.ItemDTO;

/**
 * 
 * @author sebastian taavo ek
 * Unit testing class for the class  <code>Basket</code>.
 */
class BasketTest {
	private Basket basket;
	
    @BeforeEach
    public void setUp() {
        basket = new Basket();
    }

    
    @Test
    public void testUpdateBasket() {
    	//Create two test items to put in the basket.
    	ItemDTO itemDTO1 = new ItemDTO("First item", 1, 5, "Test item 1", 50, 6);
    	ItemDTO itemDTO2 = new ItemDTO("Second item", 2, 1, "Test item 2", 50, 6);
        Item item1 = new Item(itemDTO1);
        Item item2 = new Item(itemDTO2);
        
        basket.updateBasket(item1);
        basket.updateBasket(item2);
        basket.updateBasket(item1);
        
        //Test to see that we only added "Test item 1" one time to the list, and updated its quantity on the second add.
        int expectedResult = 2;
        assertEquals(expectedResult, basket.getItemList().size()); //Should only be two items in the list.
        
        //Test to see that the quantity attribute got updated appropriately. Should be 10 since both Item 1's were created with quantity 5.
        expectedResult = 10;
        assertEquals(expectedResult, basket.getItemList().get(0).quantity);
        
    }
    
    @Test
    public void testGetGrossTotal() {
    	//Create two test items to put in the basket. The third update just updates the quantity of the first item.
    	ItemDTO itemDTO1 = new ItemDTO("First item", 1, 5, "Test item 1", 50, 6);
    	ItemDTO itemDTO2 = new ItemDTO("Second item", 2, 1, "Test item 2", 50, 6);
        Item item1 = new Item(itemDTO1);
        Item item2 = new Item(itemDTO2);
        
        basket.updateBasket(item1);
        basket.updateBasket(item2);
        basket.updateBasket(item1);
        
       //Test that the total is correct. Should be 10 * 50 (quantity times price of Item 1) + 1 * 50 (quantity times price of Item 2) = 550.
       Cost grossTotal = basket.getGrossTotal();
       double expectedResult = 550;
       assertEquals(expectedResult, grossTotal.total);
       
       //Test that the VAT is correct. Should be 50 * 0,06 (6% VAT on price 50) * 11 (10 quantity of first item, 1 quantity of second item.) = 33.
       expectedResult = 33;
       assertEquals(expectedResult, grossTotal.VAT);
        
    }

}
