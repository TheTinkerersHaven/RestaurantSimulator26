package restaurantsim.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.Function;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import restaurantsim.controller.ControllerCuoco;
import restaurantsim.controller.ControllerNavigazione;
import restaurantsim.controller.ControllerNotifiche;
import restaurantsim.model.Notifica;

/**
 * Pannello che mostra la cucina, con i suoi cuochi, una barra superiore e le notifiche
 */
public class PannelloCucina extends JPanel {
	/** Serial Version UID */
	private static final long serialVersionUID = 1L;
	/** Barra superiore del pannello cucina */
	private BarraSuperiore barraSuperiore;
	/** Pannello che contiene i pannelli dei cuochi */
	private JPanel panelCuochi;
	/** Pannello del cuoco 1 */
	private PannelloCuoco pannelloCuoco1;
	/** Pannello del cuoco 2 */
	private PannelloCuoco pannelloCuoco2;
	/** Pannello del cuoco 3 */
	private PannelloCuoco pannelloCuoco3;

	/** Inizializza i componenti */
	public PannelloCucina() {
		setLayout(new BorderLayout(0, 0));

		barraSuperiore = new BarraSuperiore("Vai a sala", ControllerNavigazione.NAVIGA_VAI_SALA_DA_CUCINA);
		add(barraSuperiore, BorderLayout.NORTH);

		panelCuochi = new JPanel();
		panelCuochi.setBorder(new EmptyBorder(30, 30, 30, 30));
		panelCuochi.setLayout(new GridLayout(1, 3, 5, 5));
		add(panelCuochi, BorderLayout.CENTER);

		pannelloCuoco1 = new PannelloCuoco(1);
		panelCuochi.add(pannelloCuoco1);

		pannelloCuoco2 = new PannelloCuoco(2);
		panelCuochi.add(pannelloCuoco2);

		pannelloCuoco3 = new PannelloCuoco(3);
		panelCuochi.add(pannelloCuoco3);
	}

	/**
	 * Registra gli ascoltatori per la navigazione per i componenti di questo pannello
	 * 
	 * @param controllerNavigazione il controller navigazione da registrare come ascoltatore per i componenti di questo pannello
	 */
	public void aggiungiAscoltatoriNavigazione(ControllerNavigazione controllerNavigazione) {
		barraSuperiore.registraAscoltatori(controllerNavigazione);
	}

	/**
	 * Registra gli ascoltatori per i cuochi di questo pannello
	 * 
	 * @param creaControllerCuoco una funzione che dato un pannello cuoco restituisce un controller cuoco da registrare come ascoltatore per quel pannello
	 */
	public void aggiungiAscoltatoriCuochi(Function<PannelloCuoco, ControllerCuoco> creaControllerCuoco) {
		pannelloCuoco1.aggiungiAscoltatori(creaControllerCuoco.apply(pannelloCuoco1));
		pannelloCuoco2.aggiungiAscoltatori(creaControllerCuoco.apply(pannelloCuoco2));
		pannelloCuoco3.aggiungiAscoltatori(creaControllerCuoco.apply(pannelloCuoco3));
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
	 * Restituisce il pannello cuoco corrispondente al numero dato
	 * 
	 * @param num il numero del cuoco di cui restituire il pannello (da 1 a 3)
	 * @return il pannello cuoco corrispondente al numero dato
	 * @throws IllegalArgumentException se il numero del cuoco dato non è valido (non compreso tra 1 e 3)
	 */
	public PannelloCuoco getPannelloCuoco(int num) {
		switch (num) {
			case 1:
				return pannelloCuoco1;
			case 2:
				return pannelloCuoco2;
			case 3:
				return pannelloCuoco3;
			default:
				throw new IllegalArgumentException("Id cuoco non valido: " + num);
		}
	}
}
