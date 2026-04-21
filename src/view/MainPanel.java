package view;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.OverlayLayout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.JLayeredPane;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.border.EmptyBorder;

import controller.ControllerNavigazione;

import java.awt.Font;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	private CardLayout cardLayoutMainUI;
	private MenuPanel menuPanel;
	private SalaPanel salaPanel;
	private ClassificaPanel classificaPanel;
	private CucinaPanel cucinaPanel;
	private JLayeredPane layeredPane;
	private JPanel overlayUI;
	private JPanel mainUI;

	public MainPanel() {
		setLayout(new BorderLayout(0, 0));

		// Il LayeredPane consente di avere 2 cose una sopra l'altra
		layeredPane = new JLayeredPane();
		// L'overlayLayout consente di posizionare 2 cose una sopra l'altra senza
		// absolute (default di JLayeredPane)
		layeredPane.setLayout(new OverlayLayout(layeredPane));
		add(layeredPane, BorderLayout.CENTER);

		mainUI = new JPanel();
		overlayUI = new JPanel();

		// Aggiungi le 2 UI al layeredPane
		layeredPane.add(mainUI, JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(overlayUI, JLayeredPane.PALETTE_LAYER);

		// L'overlay layout funziona in maniera un po particolare, quindi per mettere
		// l'overlay in alto a destra, tutto deve essere allinato in alto a destra
		mainUI.setAlignmentY(Component.TOP_ALIGNMENT);
		mainUI.setAlignmentX(Component.RIGHT_ALIGNMENT);
		overlayUI.setAlignmentY(Component.TOP_ALIGNMENT);
		overlayUI.setAlignmentX(Component.RIGHT_ALIGNMENT);

		// --- OVERLAY UI ---
		overlayUI.setOpaque(false); // Disabilita lo sfondo
		overlayUI.setBorder(new EmptyBorder(10, 10, 10, 10));
		overlayUI.setLayout(new BoxLayout(overlayUI, BoxLayout.Y_AXIS));

		// Messaggi di test
//		overlayUI.add(creaMessaggioOverlay("Tutorial: Hello world!"));
//		overlayUI.add(Box.createVerticalStrut(5));
//		overlayUI.add(creaMessaggioOverlay("Tutorial:\nPer cucinare qualcosa in cucina fai click destro su un forno"));

		// --- MAIN UI ---
		cardLayoutMainUI = new CardLayout(0, 0);
		mainUI.setLayout(cardLayoutMainUI);

		menuPanel = new MenuPanel();
		mainUI.add(menuPanel, "menu");

		cucinaPanel = new CucinaPanel();
		mainUI.add(cucinaPanel, "cucina");

		salaPanel = new SalaPanel();
		mainUI.add(salaPanel, "sala");

		classificaPanel = new ClassificaPanel();
		mainUI.add(classificaPanel, "classifica");
	}

	@SuppressWarnings("unused")
	private JTextArea creaMessaggioOverlay(String testo) {
		JTextArea textArea = new JTextArea(testo);

		textArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		// Dato che non sappiamo che font sia in uso, usa quello di default ma con il
		// bold
		textArea.setFont(textArea.getFont().deriveFont(textArea.getFont().getStyle() | Font.BOLD));
		textArea.setEnabled(false);
		textArea.setEditable(false);
		// Di default il testo disattivato ha un colore grigio, reimposta a nero
		textArea.setDisabledTextColor(Color.BLACK);
		textArea.setAlignmentX(Component.RIGHT_ALIGNMENT);
		textArea.setBackground(Color.YELLOW);
		// getPreferredSize è calcolato in base al testo, se impostiamo il max a
		// preferred gli diamo lo spazio minimo
		textArea.setMaximumSize(textArea.getPreferredSize());

		return textArea;
	}

	public void setStatoTavolo(int tavolo, int stato) {
		switch (stato) {
		case 1:
			salaPanel.getTavolo(tavolo).setBackground(Color.green);
			break;
		case 2:
			salaPanel.getTavolo(tavolo).setBackground(Color.yellow);
			break;
		case 3:
			salaPanel.getTavolo(tavolo).setBackground(Color.red);
			break;
		}
	}
	
	public CardLayout getCardLayoutMainUI() {
		return cardLayoutMainUI;
	}
	
	public JPanel getMainUI() {
		return mainUI;
	}

	public void registraAscoltatoriMain(ControllerNavigazione c) {
		menuPanel.registraAscoltatori(c);
		classificaPanel.registraAscoltatori(c);
		salaPanel.registraAscoltatori(c);
		cucinaPanel.aggiungiAscoltatori(c);
	}
	
	public Dimension cambiaPannello(String pannello) {
		cardLayoutMainUI.show(mainUI, pannello);
		if(pannello.equals("sala")) return new Dimension(600, 600);
		else return new Dimension(600, 450);
	}
}
