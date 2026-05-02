package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

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
	/** Carica la partita salvata e naviga alla sala */
	public static final String NAVIGA_CARICA_PARTITA = "carica_partita";
	/** Naviga al menu */
	public static final String NAVIGA_INDIETRO_CLASSIFICA = "indietro_classifica";
	/** Naviga alla cucina */
	public static final String NAVIGA_VAI_CUCINA_DA_SALA = "vai_cucina_da_sala";
	/** Naviga alla sala */
	public static final String NAVIGA_VAI_SALA_DA_CUCINA = "vai_sala_da_cucina";
	/** Salva la partita nel file */
	public static final String NAVIGA_SALVA_PARTITA = "salva_partita";
	/** Naviga al menu e termina la partita */
	public static final String NAVIGA_MENU_TORNA_A_MENU = "menu_torna_a_menu";
	/** Esci dall'applicazione */
	public static final String NAVIGA_MENU_ESCI = "menu_esci";

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
			case NAVIGA_CARICA_PARTITA:
				try {
					controllerPartita.caricaPartita();
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(finestra, "Errore nel caricamento della partita.", "Errore caricamento", JOptionPane.ERROR_MESSAGE);
					break;
				}
				cambiaMenu(PannelloPrincipale.NOME_PANNELLO_SALA);
				break;
			case NAVIGA_INDIETRO_CLASSIFICA:
				cambiaMenu(PannelloPrincipale.NOME_PANNELLO_MENU);
				break;
			case NAVIGA_VAI_CUCINA_DA_SALA:
				cambiaMenu(PannelloPrincipale.NOME_PANNELLO_CUCINA);
				break;
			case NAVIGA_VAI_SALA_DA_CUCINA:
				cambiaMenu(PannelloPrincipale.NOME_PANNELLO_SALA);
				break;
			case NAVIGA_SALVA_PARTITA:
				try {
					controllerPartita.salvaPartita();
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(finestra, "Impossibile salvare il gioco. Riprova più tardi.", "Errore salvataggio", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case NAVIGA_MENU_TORNA_A_MENU:
				try {
					controllerPartita.salvaPartita();
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(finestra, "Impossibile salvare il gioco. Non verrai portato al menu.", "Errore salvataggio", JOptionPane.ERROR_MESSAGE);
					break;
				}
				cambiaMenu(PannelloPrincipale.NOME_PANNELLO_MENU);
				break;
			case NAVIGA_MENU_ESCI:
				boolean conferma = controllerFinestra.chiediConfermaChiusura("Sei sicuro di voler uscire? I tuoi dati saranno salvati.");
				if(!conferma) break;
				try {
					controllerPartita.salvaPartita();
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(finestra, "Impossibile salvare il gioco. Il gioco non verrà chiuso.", "Errore salvataggio", JOptionPane.ERROR_MESSAGE);
					break;
				}
				controllerFinestra.chiudiFinestra();
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
