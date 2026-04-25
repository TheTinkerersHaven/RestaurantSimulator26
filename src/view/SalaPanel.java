package view;

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

import controller.ControllerNavigazione;
import model.Piatto;

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
		
		barraSuperiore = new BarraSuperiore("Vai a cucina", "vai_cucina_da_sala");
		add(barraSuperiore, BorderLayout.NORTH);
		
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

	private JLabel createPiattoLabel(URL link) {
		JLabel label = new JLabel(new ScaledImageIcon(link));
		label.setPreferredSize(new Dimension(DIMENSIONE_PIATTO, DIMENSIONE_PIATTO));
		return label;
	}

	public PannelloTavolo getTavolo(int tavolo) {
		switch(tavolo) {
			case 1: return tavolo1;
			case 2: return tavolo2;
			case 3: return tavolo3;
			case 4: return tavolo4;
		}
		return null;
	}

	public void registraAscoltatoriNavigazione(ControllerNavigazione c) {
		barraSuperiore.getBtnCentrale().addActionListener(c);
	}

	public void aggiornaBancone(List<Piatto> piatto) {
		scrollPaneViewportView.removeAll();
		for (Piatto p : piatto) {
			scrollPaneViewportView.add(createPiattoLabel(p.getImmaginePiatto()));
		}
	}
}
