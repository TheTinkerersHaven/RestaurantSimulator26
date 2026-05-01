package restaurantsim.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.border.EmptyBorder;

import restaurantsim.controller.ControllerNavigazione;
import restaurantsim.controller.ControllerNotifiche;
import restaurantsim.model.Notifica;

/**
 * Pannello principale che contiene tutti gli altri pannelli, con un CardLayout per mostrare solo uno alla volta, e un JLayeredPane per mostrare le notifiche sopra a tutto il resto
 */
@SuppressWarnings("serial")
public class PannelloPrincipale extends JPanel {
	/** Nome pannello del menu principale */
	public static final String NOME_PANNELLO_MENU = "menu";
	/** Nome pannello della cucina */
	public static final String NOME_PANNELLO_CUCINA = "cucina";
	/** Nome pannello della sala */
	public static final String NOME_PANNELLO_SALA = "sala";
	/** Nome pannello della classifica */
	public static final String NOME_PANNELLO_CLASSIFICA = "classifica";

	/** Layout a schede per il mainUI, che contiene i pannelli menu, sala, cucina e classifica */
	private CardLayout cardLayoutMainUI;
	/** Pannello del menu principale */
	private PannelloMenu menuPanel;
	/** Pannello della sala */
	private PannelloSala salaPanel;
	/** Pannello della classifica */
	private ClassificaPanel classificaPanel;
	/** Pannello della cucina */
	private PannelloCucina cucinaPanel;
	/** LayeredPane per mostrare le notifiche sopra a tutto il resto */
	private JLayeredPane layeredPane;
	/** Pannello per l'overlay, che contiene le notifiche da mostrare sopra a tutto il resto */
	private JPanel overlayUI;
	/** Pannello per il mainUI, che contiene i pannelli menu, sala, cucina e classifica */
	private JPanel mainUI;

	/** Inizializza i componenti */
	public PannelloPrincipale() {
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

		// L'overlay layout funziona in maniera un po' particolare, quindi per mettere l'overlay in alto a destra, tutto deve essere allinato in alto a destra
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

		menuPanel = new PannelloMenu();
		mainUI.add(menuPanel, NOME_PANNELLO_MENU);

		cucinaPanel = new PannelloCucina();
		mainUI.add(cucinaPanel, NOME_PANNELLO_CUCINA);

		salaPanel = new PannelloSala();
		mainUI.add(salaPanel, NOME_PANNELLO_SALA);

		classificaPanel = new ClassificaPanel();
		mainUI.add(classificaPanel, NOME_PANNELLO_CLASSIFICA);
	}

	/**
	 * Registra gli ascoltatori per la navigazione per i componenti di questo pannello, che in questo caso sono i pannelli interni menuPanel, classificaPanel, salaPanel e cucinaPanel
	 *
	 * @param controllerNavigazione il controller navigazione da registrare come ascoltatore per i componenti di questo pannello
	 */
	public void registraAscoltatoriNavigazioneMain(ControllerNavigazione controllerNavigazione) {
		menuPanel.registraAscoltatoriNavigazione(controllerNavigazione);
		classificaPanel.registraAscoltatoriNavigaione(controllerNavigazione);
		salaPanel.registraAscoltatoriNavigazione(controllerNavigazione);
		cucinaPanel.aggiungiAscoltatoriNavigazione(controllerNavigazione);
	}

	/**
	 * Cambia il pannello mostrato nel mainUI, mostrando quello con il nome specificato.
	 *
	 * Restituisce la dimensione preferita del pannello mostrato, in modo che la finestra possa ridimensionarsi di conseguenza
	 * 
	 * @param pannello il nome del pannello da mostrare, che deve essere uno tra NOME_PANNELLO_MENU, NOME_PANNELLO_CUCINA, NOME_PANNELLO_SALA e NOME_PANNELLO_CLASSIFICA
	 * @return la dimensione preferita del pannello mostrato, in modo che la finestra possa ridimensionarsi di conseguenza
	 */
	public Dimension cambiaPannello(String pannello) {
		cardLayoutMainUI.show(mainUI, pannello);
		if (pannello.equals(NOME_PANNELLO_MENU))
			return new Dimension(600, 450);
		else
			return new Dimension(700, 600);
	}

	/**
	 * Restituisce il pannello della sala
	 * 
	 * @return il pannello della sala
	 */
	public PannelloSala getSalaPanel() {
		return salaPanel;
	}

	/**
	 * Restituisce il pannello della classifica
	 * 
	 * @return il pannello della classifica
	 */
	public ClassificaPanel getClassificaPanel() {
		return classificaPanel;
	}

	/**
	 * Restituisce il pannello della cucina
	 * 
	 * @return il pannello della cucina
	 */
	public PannelloCucina getCucinaPanel() {
		return cucinaPanel;
	}

	/**
	 * Mostra una notifica sopra a tutto il resto, nell'overlay, con i dati della notifica passata come parametro, e registra gli ascoltatori per le notifiche per quella notifica
	 * 
	 * Restituisce il pannello della notifica mostrata, in modo che possa essere rimosso in seguito
	 * 
	 * @param notifica            la notifica da mostrare
	 * @param controllerNotifiche il controller notifiche da registrare come ascoltatore per la notifica mostrata
	 * @return il pannello della notifica mostrata
	 * @see #rimuoviNotifica(NotificaPanel)
	 * @see NotificaPanel
	 */
	public NotificaPanel mostraNotifica(Notifica notifica, ControllerNotifiche controllerNotifiche) {
		NotificaPanel notificaPanel = new NotificaPanel(notifica);
		notificaPanel.registraAscoltatori(controllerNotifiche);

		overlayUI.add(notificaPanel);
		overlayUI.revalidate();
		overlayUI.repaint();

		return notificaPanel;
	}

	/**
	 * Aggiorna le notifiche mostrate nella barra superiore dei pannelli sala e cucina con i dati presenti nella lista
	 * 
	 * @param list                la lista di notifiche da mostrare nella barra superiore dei pannelli sala e cucina
	 * @param controllerNotifiche il controller notifiche da registrare come ascoltatore alle voci del menu delle notifiche nella barra superiore dei pannelli sala e cucina
	 */
	public void aggiornaMenuNotifiche(List<Notifica> list, ControllerNotifiche controllerNotifiche) {
		cucinaPanel.aggiornaNotifiche(list, controllerNotifiche);
		salaPanel.aggiornaNotifiche(list, controllerNotifiche);
	}

	/**
	 * Rimuove una notifica dall'overlay, dato il pannello della notifica da rimuovere
	 * 
	 * @param notificaPanel il pannello della notifica da rimuovere
	 * @see #mostraNotifica(Notifica, ControllerNotifiche)
	 * @see NotificaPanel
	 */
	public void rimuoviNotifica(NotificaPanel notificaPanel) {
		overlayUI.remove(notificaPanel);
		overlayUI.revalidate();
		overlayUI.repaint();
	}
}
