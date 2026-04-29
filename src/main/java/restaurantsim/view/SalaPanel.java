package restaurantsim.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.net.URL;
import java.util.List;
import java.util.function.Function;

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

@SuppressWarnings("serial")
public class SalaPanel extends JPanel {
	private PannelloTavolo tavolo1;
	private PannelloTavolo tavolo2;
	private PannelloTavolo tavolo3;
	private PannelloTavolo tavolo4;
	private JPanel panelTavoli;
	private JScrollPane scrollPaneBancone;
	private JPanel scrollPaneViewportView;
	private BarraSuperiore barraSuperiore;

	public SalaPanel() {
		setLayout(new BorderLayout());

		pannelloSuperiore = new JPanel();
		add(pannelloSuperiore, BorderLayout.NORTH);
		pannelloSuperiore.setLayout(new BoxLayout(pannelloSuperiore, BoxLayout.Y_AXIS));

		barraSuperiore = new BarraSuperiore("Vai a cucina", "vai_cucina_da_sala");
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

	// 72 è quello che sembra essere la dimensione corretta per essere centrata in verticale
	private final int DIMENSIONE_PIATTO = 72;
	private JPanel pannelloSuperiore;
	private JLabel lblPunteggioTesto;
	private JPanel pannelloPunteggio;
	private JLabel lblPunteggio;

	private JLabel createPiattoLabel(URL link, PiattoTransferHandle transferHandle) {
		JLabel label = new JLabel(new ScaledImageIcon(link));
		label.setPreferredSize(new Dimension(DIMENSIONE_PIATTO, DIMENSIONE_PIATTO));

		// Assegna il transfer handle e aggiungi il MouseListener per il drag
		label.setTransferHandler(transferHandle);
		label.addMouseListener(new DragAndDropMouseController());

		return label;
	}

	public void registraAscoltatoriNavigazione(ControllerNavigazione c) {
		barraSuperiore.getBtnCentrale().addActionListener(c);
	}

	public void registraTransferHandlerPiatto(Function<PannelloTavolo, PiattoTransferHandle> creaTransferHandle) {
		tavolo1.setTransferHandler(creaTransferHandle.apply(tavolo1));
		tavolo2.setTransferHandler(creaTransferHandle.apply(tavolo2));
		tavolo3.setTransferHandler(creaTransferHandle.apply(tavolo3));
		tavolo4.setTransferHandler(creaTransferHandle.apply(tavolo4));
	}

	public void aggiornaBancone(List<Piatto> piatto) {
		scrollPaneViewportView.removeAll();
		for (int idx = 0; idx < piatto.size(); idx++) {
			Piatto p = piatto.get(idx);
			scrollPaneViewportView.add(createPiattoLabel(p.getImmaginePiatto(), new PiattoTransferHandle(new TransferPiatto(p, idx))));
		}
	}

	public void aggiornaNotifiche(List<Notifica> list, ControllerNotifiche cn) {
		barraSuperiore.aggiornaMenuNotifiche(list, cn);
	}

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
				return null;
		}
	}

	public void aggiornaPunteggio(int punteggio) {
		lblPunteggio.setText(String.valueOf(punteggio));
	}
}
