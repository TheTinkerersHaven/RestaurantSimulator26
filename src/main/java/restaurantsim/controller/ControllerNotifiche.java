package restaurantsim.controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.Timer;

import restaurantsim.model.Gioco;
import restaurantsim.model.Notifica;
import restaurantsim.view.PannelloPrincipale;
import restaurantsim.view.NotificaPanel;

/**
 * Controller che si occupa di gestire le notifiche.
 *
 * Si occupa di mostrare le notifiche, aggiornare il menu delle notifiche e gestire i click sulle notifiche.
 */
public class ControllerNotifiche implements MouseListener, ActionListener {
	/** Nome del componente che mostra il testo di una notifica */
	public static final String NOME_TESTO_NOTIFICA = "textAreaNotif";
	/** Nome del componente che permette di chiudere una notifica */
	public static final String NOME_LABEL_CHIUDI_NOTIFICA = "lblCloseNotif";

	/** Action command usato per cancellare tutte le notifiche */
	public static final String CANCELLA_TUTTE_NOTIFICHE = "del_all";

	/** Indica che la notifica ha origine nella sala */
	public static final String ORIGINE_SALA = PannelloPrincipale.NOME_PANNELLO_SALA;
	/** Indica che la notifica ha origine nella cucina */
	public static final String ORIGINE_CUCINA = PannelloPrincipale.NOME_PANNELLO_CUCINA;

	/**
	 * Il gioco, necessario per accedere alla lista delle notifiche e per rimuovere le notifiche quando vengono cliccate.
	 */
	private Gioco gioco;
	/**
	 * Il pannello principale, necessario per aggiornare il menu delle notifiche e per mostrare le notifiche.
	 */
	private PannelloPrincipale pannelloPrincipale;
	/**
	 * Il controller di navigazione, necessario per cambiare menu quando una notifica viene cliccata.
	 * 
	 * Non può essere inizializzato nel costruttore a causa della dipendenza circolare tra i due controller.
	 * 
	 * @see #setControllerNavigazione(ControllerNavigazione)
	 */
	private ControllerNavigazione controllerNavigazione;
	/**
	 * Mappa che associa ad ogni pannello di notifica il timer che si occupa di rimuoverlo dopo 5 secondi.
	 * 
	 * In questo modo, quando l'utente passa il mouse sopra una notifica, è possibile fermare il timer e far sì che la notifica non venga rimossa finché l'utente non sposta il mouse fuori dalla notifica.
	 */
	private HashMap<NotificaPanel, Timer> timerPannelli;

	/**
	 * Costruttore del controller delle notifiche.
	 * 
	 * Il controller una volta inizializato non è ancora completamente funzionante, è necessario chiamare il metodo {@link #setControllerNavigazione(ControllerNavigazione)} per settare il controller di navigazione prima di poter gestire i click sulle notifiche.
	 * 
	 * @param gioco              Il gioco, necessario per accedere alla lista delle notifiche e per rimuovere le notifiche quando vengono cliccate.
	 * @param pannelloPrincipale Il pannello principale, necessario per aggiornare il menu delle notifiche e per mostrare le notifiche.
	 */
	public ControllerNotifiche(Gioco gioco, PannelloPrincipale pannelloPrincipale) {
		this.gioco = gioco;
		this.pannelloPrincipale = pannelloPrincipale;

		this.timerPannelli = new HashMap<>();
	}

	/**
	 * Imposta il controller di navigazione per questo controller delle notifiche.
	 * 
	 * Per via della dipendenza circolare tra ControllerNotifiche e ControllerNavigazione, è necessario settare il controller di navigazione dopo la creazione del controller delle notifiche.
	 * 
	 * @param controllerNavigazione Il controller di navigazione da associare a questo controller delle notifiche
	 */
	public void setControllerNavigazione(ControllerNavigazione controllerNavigazione) {
		this.controllerNavigazione = controllerNavigazione;
	}

	/**
	 * Quando viene cliccata una notifica, viene rimossa dalla lista delle notifiche del gioco e viene aggiornato il menu delle notifiche.
	 * 
	 * Se viene cliccato il pulsante per cancellare tutte le notifiche, vengono rimosse tutte le notifiche dalla lista delle notifiche del gioco e viene aggiornato il menu delle notifiche.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(CANCELLA_TUTTE_NOTIFICHE)) {
			gioco.cancellaNotifiche();
			aggiornaNotifiche();
		} else {
			int index = Integer.parseInt(e.getActionCommand());
			Notifica notif = gioco.getNotifica(index);

			gioco.rimuoviNotifica(index);

			aggiornaNotifiche();
			controllerNavigazione.cambiaMenu(notif.getOrigine());
		}
	}

	/**
	 * Se viene cliccata una notifica, in base a cosa viene cliccato (testo o pulsante di chiusura), viene cambiato il menu o semplicemente rimossa la notifica.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Container parent = e.getComponent().getParent().getParent();
		if (!(parent instanceof NotificaPanel))
			return;

		NotificaPanel notificaPanel = (NotificaPanel) parent;

		pannelloPrincipale.rimuoviNotifica(notificaPanel);

		switch (e.getComponent().getName()) {
			case NOME_TESTO_NOTIFICA:
				controllerNavigazione.cambiaMenu(notificaPanel.getOrigine());
				break;
			case NOME_LABEL_CHIUDI_NOTIFICA:
				// Non serve fare altro
				break;
			default:
				// Se il componente cliccato non è uno di quelli gestiti, non facciamo nulla invece di lanciare eccezione
				break;
		}
	}

	/**
	 * Aggiorna il menu delle notifiche con le notifiche attualmente presenti nella lista delle notifiche del gioco.
	 */
	public void aggiornaNotifiche() {
		pannelloPrincipale.aggiornaMenuNotifiche(gioco.getNotifiche(), this);
	}

	/**
	 * Pulisce tutte le notifiche del gioco, rimuove quelle visualizzate nell'overlay
	 * e interrompe tutti i timer di rimozione automatica attivi.
	 */
	public void pulisciTutto() {
		gioco.cancellaNotifiche();
		pannelloPrincipale.pulisciNotificheOverlay();
		aggiornaNotifiche();
		timerPannelli.forEach((panel, timer) -> timer.stop());
		timerPannelli.clear();
	}

	/**
	 * Mostra una nuova notifica, aggiungendola all'overlay del pannello principale e aggiornando il menu delle notifiche.
	 * 
	 * @param notifica La notifica da mostrare
	 */
	public void mostraNotifica(Notifica notifica) {
		NotificaPanel notificaPanel = pannelloPrincipale.mostraNotifica(notifica, this);
		pannelloPrincipale.aggiornaMenuNotifiche(gioco.getNotifiche(), this);

		Timer timer = new Timer(5000, event -> {
			pannelloPrincipale.rimuoviNotifica(notificaPanel);
			timerPannelli.remove(notificaPanel);
		});

		timerPannelli.put(notificaPanel, timer);

		timer.setRepeats(false);
		timer.start();
	}

	/** {@inheritDoc} */
	@Override
	public void mousePressed(MouseEvent e) {
		// Non usato.
	}

	/** {@inheritDoc} */
	@Override
	public void mouseReleased(MouseEvent e) {
		// Non usato.
	}

	/**
	 * Quando il mouse entra in una notifica, viene fermato il timer che si occupa di rimuovere la notifica dopo 5 secondi e viene cambiato il colore di sfondo della notifica per indicare che è attiva.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		Container cont = e.getComponent().getParent().getParent();
		if (!(cont instanceof NotificaPanel))
			return;

		NotificaPanel notifPanel = (NotificaPanel) cont;
		JPanel panel = notifPanel.getPanel();

		timerPannelli.get(notifPanel).stop();

		panel.setBackground(Color.GREEN);
	}

	/**
	 * Quando il mouse esce da una notifica, viene riavviato il timer che si occupa di rimuovere la notifica dopo 5 secondi e viene cambiato il colore di sfondo della notifica per indicare che non è più attiva.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		Container cont = e.getComponent().getParent().getParent();
		if (!(cont instanceof NotificaPanel))
			return;

		NotificaPanel notifPanel = (NotificaPanel) cont;
		JPanel panel = notifPanel.getPanel();

		timerPannelli.get(notifPanel).start();

		panel.setBackground(Color.YELLOW);
	}

	
}
