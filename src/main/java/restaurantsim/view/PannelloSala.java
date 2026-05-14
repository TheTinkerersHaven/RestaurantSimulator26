package restaurantsim.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.net.URL;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import restaurantsim.controller.ControllerNavigazione;
import restaurantsim.controller.ControllerNotifiche;
import restaurantsim.controller.DragAndDropMouseController;
import restaurantsim.controller.PiattoTransferHandle;
import restaurantsim.model.Notifica;
import restaurantsim.model.Piatto;
import restaurantsim.model.TransferPiatto;
import javax.swing.BoxLayout;

/**
 * Pannello che mostra la sala, con i suoi tavoli, il bancone con i piatti pronti, una barra superiore e le notifiche
 */
public class PannelloSala extends JPanel {
	/** Serial Version UID */
	private static final long serialVersionUID = 1L;
	/** Pannello del tavolo 1 */
	private PannelloTavolo tavolo1;
	/** Pannello del tavolo 2 */
	private PannelloTavolo tavolo2;
	/** Pannello del tavolo 3 */
	private PannelloTavolo tavolo3;
	/** Pannello del tavolo 4 */
	private PannelloTavolo tavolo4;
	/** Panel che contiene i tavoli */
	private JPanel panelTavoli;
	/** ScrollPane per il bancone con i piatti pronti */
	private JScrollPane scrollPaneBancone;
	/** Panel che contiene le etichette dei piatti pronti da mostrare nel bancone */
	private JPanel scrollPaneViewportView;
	/** Barra superiore del pannello sala */
	private BarraSuperiore barraSuperiore;
	/** Pannello superiore del pannello sala, che contiene la barra superiore e il punteggio */
	private JPanel pannelloSuperiore;
	/** Label per mostrare il punteggio attuale */
	private JPanel pannelloPunteggio;
	/** Label per mostrare il testo "Punteggio: " prima del punteggio */
	private JLabel lblPunteggioTesto;
	/** Label per mostrare il punteggio attuale */
	private JLabel lblPunteggio;

	/**
	 * Dimensione dell'immagine del piatto nel bancone, in pixel.
	 * 
	 * 72 è quello che sembra essere la dimensione corretta per essere centrata in verticale
	 */
	private final int DIMENSIONE_PIATTO = 72;

	/** Inizializza i componenti */
	public PannelloSala() {
		setLayout(new BorderLayout());

		pannelloSuperiore = new JPanel();
		pannelloSuperiore.setLayout(new BoxLayout(pannelloSuperiore, BoxLayout.Y_AXIS));
		add(pannelloSuperiore, BorderLayout.NORTH);

		barraSuperiore = new BarraSuperiore("Vai a cucina", ControllerNavigazione.NAVIGA_VAI_CUCINA_DA_SALA);
		pannelloSuperiore.add(barraSuperiore);

		pannelloPunteggio = new JPanel();
		pannelloSuperiore.add(pannelloPunteggio);

		lblPunteggioTesto = new JLabel("Punteggio: ");
		pannelloPunteggio.add(lblPunteggioTesto);

		lblPunteggio = new JLabel("0");
		pannelloPunteggio.add(lblPunteggio);

		panelTavoli = new JPanel();
		panelTavoli.setBorder(new EmptyBorder(30, 50, 30, 50));
		panelTavoli.setLayout(new GridLayout(2, 2, 50, 50));
		add(panelTavoli, BorderLayout.CENTER);

		tavolo1 = new PannelloTavolo(1);
		panelTavoli.add(tavolo1);
		tavolo2 = new PannelloTavolo(2);
		panelTavoli.add(tavolo2);
		tavolo3 = new PannelloTavolo(3);
		panelTavoli.add(tavolo3);
		tavolo4 = new PannelloTavolo(4);
		panelTavoli.add(tavolo4);

		scrollPaneViewportView = new JPanel();
		scrollPaneViewportView.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 5));

		scrollPaneBancone = new JScrollPane(scrollPaneViewportView, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneBancone.setPreferredSize(new Dimension(0, 100));

		add(scrollPaneBancone, BorderLayout.SOUTH);
	}

	/**
	 * Crea un'etichetta per un piatto, con l'immagine del piatto e il transfer handler per il drag and drop.
	 * 
	 * @param link           il link dell'immagine del piatto da mostrare nell'etichetta
	 * @param transferHandle il transfer handler da assegnare all'etichetta per il drag and drop del piatto
	 * @return l'etichetta creata per il piatto, con l'immagine del piatto e il transfer handler per il drag and drop
	 * @see PiattoTransferHandle
	 * @see DragAndDropMouseController
	 * @see #aggiornaBancone(List)
	 */
	private JLabel createPiattoLabel(URL link, PiattoTransferHandle transferHandle) {
		JLabel label = new JLabel(new ScaledImageIcon(link));
		label.setPreferredSize(new Dimension(DIMENSIONE_PIATTO, DIMENSIONE_PIATTO));

		// Assegna il transfer handle e aggiungi il MouseListener per il drag
		label.setTransferHandler(transferHandle);
		label.addMouseListener(new DragAndDropMouseController());

		return label;
	}

	/**
	 * Registra gli ascoltatori per la navigazione per i componenti di questo pannello
	 * 
	 * @param controllerNavigazione il controller navigazione da registrare come ascoltatore per i componenti di questo pannello
	 */
	public void registraAscoltatoriNavigazione(ControllerNavigazione controllerNavigazione) {
		barraSuperiore.registraAscoltatori(controllerNavigazione);
	}

	/**
	 * Registra gli i TransferHandler per i tavoli di questo pannello
	 * 
	 * @param creaTransferHandle una funzione che, dato un pannello tavolo, restituisce il TransferHandler da assegnare a quel pannello tavolo per il drag and drop dei piatti
	 */
	public void registraTransferHandlerPiatto(restaurantsim.view.Function<PannelloTavolo, PiattoTransferHandle> creaTransferHandle) {
		tavolo1.setTransferHandler(creaTransferHandle.apply(tavolo1));
		tavolo2.setTransferHandler(creaTransferHandle.apply(tavolo2));
		tavolo3.setTransferHandler(creaTransferHandle.apply(tavolo3));
		tavolo4.setTransferHandler(creaTransferHandle.apply(tavolo4));
	}

	/**
	 * Aggiorna il bancone con i piatti pronti.
	 * 
	 * Rimuove tutti i piatti attualmente mostrati nel bancone e li sostituisce con quelli della lista, creando per ognuno un'etichetta con l'immagine del piatto e il transfer handler per il drag and drop.
	 * 
	 * @param piatti la lista di piatti pronti da mostrare nel bancone
	 */
	public void aggiornaBancone(List<Piatto> piatti) {
		scrollPaneViewportView.removeAll();
		for (int idx = 0; idx < piatti.size(); idx++) {
			Piatto p = piatti.get(idx);
			scrollPaneViewportView.add(createPiattoLabel(p.getImmaginePiatto(), new PiattoTransferHandle(new TransferPiatto(p, idx))));
		}
	}

	/**
	 * Aggiorna le notifiche mostrate nella barra superiore con i dati presenti nella lista
	 * 
	 * @param lista               la lista di notifiche da mostrare nella barra superiore
	 * @param controllerNotifiche il controller notifiche da registrare come ascoltatore alle voci del menu delle notifiche nella barra superiore
	 */
	public void aggiornaNotifiche(List<Notifica> lista, ControllerNotifiche controllerNotifiche) {
		barraSuperiore.aggiornaMenuNotifiche(lista, controllerNotifiche);
	}

	/**
	 * Restituisce il pannello del tavolo dato il numero del tavolo
	 * 
	 * @param numeroTavolo il numero del tavolo di cui restituire il pannello (da 1 a 4)
	 * @return il pannello del tavolo corrispondente al numero del tavolo dato
	 * @throws IllegalArgumentException se il numero del tavolo dato non è valido (non compreso tra 1 e 4)
	 */
	public PannelloTavolo getPannelloTavolo(int numeroTavolo) {
		switch (numeroTavolo) {
			case 1:
				return tavolo1;
			case 2:
				return tavolo2;
			case 3:
				return tavolo3;
			case 4:
				return tavolo4;
			default:
				throw new IllegalArgumentException("Numero tavolo non valido: " + numeroTavolo);
		}
	}

	/**
	 * Aggiorna il punteggio mostrato nel pannello, dato il nuovo punteggio da mostrare
	 * 
	 * @param punteggio il nuovo punteggio da mostrare nel pannello
	 */
	public void aggiornaPunteggio(int punteggio) {
		lblPunteggio.setText(String.valueOf(punteggio));
	}
}
