package restaurantsim.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import restaurantsim.controller.ControllerNavigazione;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	private JPanel panelMenu;
	private JPanel panelPulsantiMenu;
	private JLabel lblMenuImage;
	private JButton btnClassifica;
	private JButton btnNuovaPartita;

	public MenuPanel() {
		setLayout(new BorderLayout(0, 0));
		panelMenu = new JPanel();
		add(panelMenu);
		panelMenu.setLayout(new BorderLayout(0, 0));

		lblMenuImage = new JLabel(new ScaledImageIcon(MainPanel.class.getResource("/images/Restaurant Simulator V2.png")));
		lblMenuImage.setOpaque(true);
		panelMenu.add(lblMenuImage, BorderLayout.CENTER);

		panelPulsantiMenu = new JPanel();
		add(panelPulsantiMenu, BorderLayout.SOUTH);

		btnClassifica = new JButton("Classifica");
		btnClassifica.setActionCommand("classifica");
		panelPulsantiMenu.add(btnClassifica);

		btnNuovaPartita = new JButton("Nuova partita");
		btnNuovaPartita.setActionCommand("nuova_partita");
		panelPulsantiMenu.add(btnNuovaPartita);
	}

	public void registraAscoltatoriNavigazione(ControllerNavigazione controllerNavigazione) {
		btnClassifica.addActionListener(controllerNavigazione);
		btnNuovaPartita.addActionListener(controllerNavigazione);
	}
}
