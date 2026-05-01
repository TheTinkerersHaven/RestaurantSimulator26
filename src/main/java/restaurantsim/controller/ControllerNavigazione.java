package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import restaurantsim.model.Gioco;
import restaurantsim.view.PannelloPrincipale;
import restaurantsim.view.Finestra;

/**
 * Controller che si occupa di gestire la navigazione tra i vari pannelli della finestra.
 */
public class ControllerNavigazione implements ActionListener {
	/** Naviga alla classifica */
	public static final String NAVIGA_CLASSIFICA = "classifica";
	/** Naviga alla sala e fai una nuova partita */
	public static final String NAVIGA_NUOVA_PARTITA = "nuova_partita";
	/** Naviga al menu */
	public static final String NAVIGA_INDIETRO_CLASSIFICA = "indietro_classifica";
	/** Naviga alla cucina */
	public static final String NAGIVA_VAI_CUCINA_DA_SALA = "vai_cucina_da_sala";
	/** Naviga alla sala */
	public static final String NAGIVA_VAI_SALA_DA_CUCINA = "vai_sala_da_cucina";
	/** Naviga al menu e termina la partita */
	public static final String NAGIVA_MENU_TORNA_A_MENU = "menu_torna_a_menu";
	/** Esci dall'applicazione */
	public static final String NAGIVA_MENU_ESCI = "menu_esci";

	/**
	 * La finestra da gestire. Ogni pannello può richiedere una dimensione diversa, quindi è necessario avere un riferimento alla finestra per poterla ridimensionare.
	 */
	private Finestra finestra;
	/**
	 * Pannello principale della finestra, che contiene tutti i pannelli. Necessario per poter cambiare il pannello visualizzato.
	 */
	private PannelloPrincipale pannelloPrincipale;
	/**
	 * Controller della partita, necessario per poter iniziare e terminale la partita.
	 */
	private ControllerPartita controllerPartita;
	/**
	 * Controller della finestra, necessario per poter chiudere la finestra anche dai menu di gioco.
	 */
	private ControllerFinestra controllerFinestra;

	/**
	 * Costruttore del controller di navigazione. Registra se stesso come ascoltatore dei bottoni di navigazione.
	 * 
	 * @param gioco              Il gioco, necessario per poter iniziare e terminale la partita.
	 * @param finestra           La finestra da gestire.
	 * @param controllerPartita  Controller della partita, necessario per poter iniziare e terminale la partita.
	 * @param controllerFinestra Controller della finestra, necessario per poter chiudere la finestra anche dai menu di gioco.
	 */
	public ControllerNavigazione(Gioco gioco, Finestra finestra, ControllerPartita controllerPartita, ControllerFinestra controllerFinestra) {
		this.finestra = finestra;
		this.pannelloPrincipale = finestra.getPanelloPrincipale();
		this.controllerPartita = controllerPartita;
		this.controllerFinestra = controllerFinestra;

		pannelloPrincipale.registraAscoltatoriNavigazioneMain(this);
	}

	/**
	 * Viene cliccato un pulsante di navigazione.
	 * 
	 * Cambia il pannello visualizzato in base al pulsante cliccato o inizia o termina la partita se necessario.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case NAVIGA_CLASSIFICA:
				cambiaMenu(PannelloPrincipale.NOME_PANNELLO_CLASSIFICA);
				break;
			case NAVIGA_NUOVA_PARTITA:
				cambiaMenu(PannelloPrincipale.NOME_PANNELLO_SALA);
				controllerPartita.nuovaParita();
				break;
			case NAVIGA_INDIETRO_CLASSIFICA:
				cambiaMenu(PannelloPrincipale.NOME_PANNELLO_MENU);
				break;
			case NAGIVA_VAI_CUCINA_DA_SALA:
				cambiaMenu(PannelloPrincipale.NOME_PANNELLO_CUCINA);
				break;
			case NAGIVA_VAI_SALA_DA_CUCINA:
				cambiaMenu(PannelloPrincipale.NOME_PANNELLO_SALA);
				break;
			case NAGIVA_MENU_TORNA_A_MENU:
				controllerPartita.finisciPartita(ControllerPartita.ESCI_SOLO_NOME);
				cambiaMenu(PannelloPrincipale.NOME_PANNELLO_MENU);
				break;
			case NAGIVA_MENU_ESCI:
				controllerFinestra.chiudiFinestraConConferma();
				break;
		}
	}

	/**
	 * Cambia il pannello visualizzato in base al nome del pannello.
	 *
	 * Ridimensiona la finestra in base alla dimensione del pannello visualizzato.
	 *
	 * @param nome Il nome del pannello da visualizzare. Deve essere uno dei nomi dei pannelli definiti in PannelloPrincipale: {@link PannelloPrincipale#NOME_PANNELLO_MENU}, {@link PannelloPrincipale#NOME_PANNELLO_SALA}, {@link PannelloPrincipale#NOME_PANNELLO_CUCINA}, {@link PannelloPrincipale#NOME_PANNELLO_CLASSIFICA}
	 */
	public void cambiaMenu(String nome) {
		finestra.setSize(pannelloPrincipale.cambiaPannello(nome));
	}
}
