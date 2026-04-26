package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;

import controller.ControllerNavigazione;
import controller.ControllerNotifiche;
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

	private static final DataFlavor PIATTO_DATA_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=model.Piatto", "Piatto");

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

		tavolo4.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
				// Check that it supports only the PIATTO_DATA_FLAVOR
				if (support.getDataFlavors().length != 1) return false;
				return support.isDataFlavorSupported(PIATTO_DATA_FLAVOR); 
            }

            @Override
            public boolean importData(TransferSupport support) {
				Transferable transferable = support.getTransferable();
				Piatto dropped;

				try {
					dropped = (Piatto) transferable.getTransferData(PIATTO_DATA_FLAVOR);
				} catch (UnsupportedFlavorException | IOException ex) {
					// Se ci sono problemi a ottenere i dati, non importare nulla
					JOptionPane.showMessageDialog(SalaPanel.this, "Errore durante il trasferimento del piatto.", "Errore", JOptionPane.ERROR_MESSAGE);
					return false;
				}

				JOptionPane.showMessageDialog(SalaPanel.this, "Piatto " + dropped + " servivo al tavolo 4!", "Successo", JOptionPane.INFORMATION_MESSAGE);
				return true;
            }
        });

		scrollPaneViewportView = new JPanel();
		scrollPaneViewportView.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 5));

		scrollPaneBancone = new JScrollPane(scrollPaneViewportView, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneBancone.setPreferredSize(new Dimension(0, 100));

		add(scrollPaneBancone, BorderLayout.SOUTH);
	}

	// 72 è quello che sembra essere la dimensione corretta per essere centrata in verticale
	private final int DIMENSIONE_PIATTO = 72;

	private JLabel createPiattoLabel(URL link, Piatto piatto) {
		ImageIcon icon = new ScaledImageIcon(link);
		JLabel label = new JLabel(icon);
		label.setPreferredSize(new Dimension(DIMENSIONE_PIATTO, DIMENSIONE_PIATTO));

		TransferHandler th = new TransferHandler() {
			@Override
			protected Transferable createTransferable(JComponent c) {
				return new Transferable() {
					@Override
					public DataFlavor[] getTransferDataFlavors() {
						return new DataFlavor[] { PIATTO_DATA_FLAVOR };
					}

					@Override
					public boolean isDataFlavorSupported(DataFlavor flavor) {
						return PIATTO_DATA_FLAVOR.equals(flavor);
					}

					@Override
					public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
						return piatto;
					}
				};
			}

			@Override
			public int getSourceActions(JComponent c) {
				return COPY;
			}
		};

		label.setTransferHandler(th);

		// Registra un mouse listener per iniziallizare il drag
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TransferHandler handler = label.getTransferHandler();
				handler.exportAsDrag(label, e, TransferHandler.COPY);
            }
        });

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
			scrollPaneViewportView.add(createPiattoLabel(p.getImmaginePiatto(), p));
		}
	}
	
	public void aggiornaNotifiche(List<String> list, ControllerNotifiche cn) {
		barraSuperiore.aggiornaMenuNotifiche(list, cn);
	}
}
