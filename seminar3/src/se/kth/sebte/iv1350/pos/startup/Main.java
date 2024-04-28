package se.kth.sebte.iv1350.pos.startup;
import se.kth.sebte.iv1350.pos.controller.Controller;
import se.kth.sebte.iv1350.pos.integration.DbHandler;
import se.kth.sebte.iv1350.pos.view.View;


public class Main {
	private DbHandler dbHandler = new DbHandler();
	private Controller contr = new Controller(dbHandler);
	private View view = new View(contr);

	public static void main(String[] args) {
		System.out.println("Test");
		Main main = new Main();
	}

}
