package restaurantsim.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

import restaurantsim.controller.ControllerNavigazione;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ClassificaPanel extends JPanel {
	private JList<String> listClassifica;
	private DefaultListModel<String> dlm;
	private JPanel panelPulsantiClassifica;
	private JButton btnIndietro;
	
	public ClassificaPanel() {
		setBorder(new EmptyBorder(10, 10, 0, 10));
		setLayout(new BorderLayout(0, 0));
		
		listClassifica = new JList<String>();
		add(listClassifica, BorderLayout.CENTER);
		
		dlm = new DefaultListModel<String>();
		listClassifica.setModel(dlm);
		
		panelPulsantiClassifica = new JPanel();
		add(panelPulsantiClassifica, BorderLayout.SOUTH);
		
		btnIndietro = new JButton("Indietro");
		btnIndietro.setActionCommand("indietro_classifica");
		panelPulsantiClassifica.add(btnIndietro);
	}

	public void registraAscoltatoriNavigaione(ControllerNavigazione c) {
		btnIndietro.addActionListener(c);
	}
	
}
