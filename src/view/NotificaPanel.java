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
	
	public NotificaPanel(String testo) {
		setBackground(Color.YELLOW);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setAlignmentX(RIGHT_ALIGNMENT);
		
		textAreaNotif = new JTextArea(testo);
		textAreaNotif.setName("textAreaNotif");
		add(textAreaNotif);
		
		textAreaNotif.setOpaque(false);
		textAreaNotif.setBorder(new EmptyBorder(5, 5, 5, 5));
		// Dato che non sappiamo che font sia in uso, usa quello di default ma con il bold
		textAreaNotif.setFont(textAreaNotif.getFont().deriveFont(textAreaNotif.getFont().getStyle() | Font.BOLD));
		textAreaNotif.setEnabled(false);
		textAreaNotif.setEditable(false);
		// Di default il testo disattivato ha un colore grigio, reimposta a nero
		textAreaNotif.setDisabledTextColor(Color.BLACK);
		textAreaNotif.setAlignmentX(Component.RIGHT_ALIGNMENT);
		textAreaNotif.setBackground(Color.YELLOW);
		// getPreferredSize è calcolato in base al testo, se impostiamo il max a preferred gli diamo lo spazio minimo
		textAreaNotif.setMaximumSize(textAreaNotif.getPreferredSize());
		
		lblCloseNotif = new JLabel("\u2715");
		lblCloseNotif.setName("lblCloseNotif");
		add(lblCloseNotif);

		lblCloseNotif.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblCloseNotif.setFont(new Font("Dialog", Font.BOLD, 18));
	}
	
	public void registraAscoltatori(ControllerNotifiche cn) {
		textAreaNotif.addMouseListener(cn);
		lblCloseNotif.addMouseListener(cn);
	}
}
