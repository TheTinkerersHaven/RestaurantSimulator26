package restaurantsim.view;

import javax.swing.JFrame;

import restaurantsim.controller.ControllerFinestra;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private MainPanel panel;

	public Window() {
		panel = new MainPanel();
		setSize(600, 450);
		setContentPane(panel);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	public void registraController(ControllerFinestra controller) {
		addWindowListener(controller);
	}

	public MainPanel getPanel() {
		return panel;
	}
}
