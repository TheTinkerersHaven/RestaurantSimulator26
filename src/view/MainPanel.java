package view;

import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.JLayeredPane;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.border.EmptyBorder;

import controller.ControllerCuoco;
import controller.ControllerNavigazione;
import controller.ControllerNotifiche;

import java.util.function.Function;

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
		// L'overlayLayout consente di posizionare 2 cose una sopra l'altra senza absolute (default di JLayeredPane)
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
	
	public JPanel getOverlayUI() {
		return overlayUI;
	}

	public void registraAscoltatoriNavigazioneMain(ControllerNavigazione controllerNavigazione) {
		menuPanel.registraAscoltatoriNavigazione(controllerNavigazione);
		classificaPanel.registraAscoltatoriNavigaione(controllerNavigazione);
		salaPanel.registraAscoltatoriNavigazione(controllerNavigazione);
		cucinaPanel.aggiungiAscoltatoriNavigazione(controllerNavigazione);
	}
	
	public void registraAscoltatoriCuochiMain(Function<PannelloCuoco, ControllerCuoco> creaControllerCuoco) {
		cucinaPanel.aggiungiAscoltatoriCuochi(creaControllerCuoco);
	}
	
	public Dimension cambiaPannello(String pannello) {
		cardLayoutMainUI.show(mainUI, pannello);
		if(pannello.equals("sala")) return new Dimension(600, 600);
		else return new Dimension(600, 450);
	}

    public SalaPanel getSalaPanel() {
		return salaPanel;
    }
    
    public void mostraNotifica(String text, ControllerNotifiche cn) {
    	NotificaPanel notif = new NotificaPanel(text);
    	Component strut = Box.createVerticalStrut(5);
    	notif.registraAscoltatori(cn);
		overlayUI.add(notif);
		overlayUI.add(strut);
		overlayUI.revalidate();
		overlayUI.repaint();
		
		Timer timer = new Timer(5000, event -> {
			if(notif != null && strut != null) {
				overlayUI.remove(notif);
				overlayUI.remove(strut);
				overlayUI.revalidate();
				overlayUI.repaint();		
			}
		});
		
		timer.setRepeats(false);
		timer.start();
	}

	public CucinaPanel getCucinaPanel() {
		return cucinaPanel;
	}

	
}
