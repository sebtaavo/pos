package se.kth.sebte.iv1350.pos.view;
import se.kth.sebte.iv1350.pos.controller.Controller;

public class View {
	Controller contr;
	public View(Controller contr) {
		this.contr = contr;
	}
	
	public void startSale() {
		this.contr.startSale();
	}
}
