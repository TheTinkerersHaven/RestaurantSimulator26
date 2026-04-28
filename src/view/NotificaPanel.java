package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.ControllerNotifiche;

@SuppressWarnings("serial")
public class NotificaPanel extends JPanel {
	JTextArea textAreaNotif;
	JLabel lblCloseNotif;
	private JPanel panel;
	
	public NotificaPanel(String testo) {
		setBorder(new EmptyBorder(0, 0, 10, 0));
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(RIGHT_ALIGNMENT);
		
		panel = new JPanel();
		panel.setBackground(Color.YELLOW);
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		textAreaNotif = new JTextArea(testo);
		panel.add(textAreaNotif);
		// Inserisco dei border direttamente sugli elementi per rendere anche gli spazi vuoti cliccabili
		textAreaNotif.setBorder(new EmptyBorder(5, 5, 5, 2));
		textAreaNotif.setName("textAreaNotif");
		
		textAreaNotif.setOpaque(false);
		textAreaNotif.setFont(new Font("Dialog", Font.BOLD, 12));
		textAreaNotif.setEnabled(false);
		textAreaNotif.setEditable(false);
		// Di default il testo disattivato ha un colore grigio, reimposta a nero
		textAreaNotif.setDisabledTextColor(Color.BLACK);
		textAreaNotif.setAlignmentX(Component.RIGHT_ALIGNMENT);
		textAreaNotif.setBackground(Color.YELLOW);
		// getPreferredSize è calcolato in base al testo, se impostiamo il max a preferred gli diamo lo spazio minimo
		textAreaNotif.setMaximumSize(textAreaNotif.getPreferredSize());
		
		// Simbolo unicode per la "x" di chiusura
		lblCloseNotif = new JLabel("\u2715");
		panel.add(lblCloseNotif);
		lblCloseNotif.setBorder(new EmptyBorder(3, 5, 5, 5));
		lblCloseNotif.setName("lblCloseNotif");
		
		lblCloseNotif.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblCloseNotif.setFont(new Font("Dialog", Font.BOLD, 18));
	}
	
	public void registraAscoltatori(ControllerNotifiche cn) {
		textAreaNotif.addMouseListener(cn);
		lblCloseNotif.addMouseListener(cn);
	}
}
