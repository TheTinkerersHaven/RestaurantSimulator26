package main;

import controller.ControllerNavigazione;
import view.Window;

public class Main {
	public static void main(String[] args) {
		Window window = new Window();
		new ControllerNavigazione(window);
	}
}
