package se.kth.sebte.iv1350.pos.startup;
import se.kth.sebte.iv1350.pos.controller.Controller;
import se.kth.sebte.iv1350.pos.integration.DbHandler;
import se.kth.sebte.iv1350.pos.view.View;

/**
 * 
 * @author sebastian taavo ek
 * The <code>Main</code> class represents the start-up class of the program. It creates the
 * <code>View</code>, integration and <code>Controller</code> layers of the model. 
 * It is also temporarily responsible for issuing commands to the <code>View</code>,
 * in the absence of a real user interface.
 */

public class Main {
	private DbHandler dbHandler = new DbHandler();
	private Controller contr = new Controller(dbHandler);
	private View view = new View(contr);

	/**
	 * Launches the program.
	 * @param args  the default Java input parameter to <code>public static void main</code>.
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.view.startSale();
		main.view.scanItem(69, 2);
		main.view.scanItem(2, 1);
		main.view.endSale();
		main.view.requestDiscount(1337);
		main.view.presentPayment(500);
		
		main.view.startSale();
		main.view.scanItem(1, 2);
		main.view.scanItem(2, 1);
		main.view.endSale();
		main.view.requestDiscount(1337);
		main.view.presentPayment(500);
		
		main.view.startSale();
		main.view.scanItem(1, 2);
		main.view.scanItem(2, 1);
		main.view.endSale();
		main.view.requestDiscount(1337);
		main.view.presentPayment(500);
		
	}

}
