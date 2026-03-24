package view;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.net.URL;

@SuppressWarnings("serial")
public class Panel extends JPanel {
    private JPanel panelMenu;
    private JPanel panelPulsantiMenu;
    private JLabel lblMenuImage;
    private JButton btnClassifica;
    private JButton btnNuovaPartita;
    private JPanel panelSala;
    private JPanel[] pannelliGriglia = new JPanel[25];

    public Panel() {
        setLayout(new CardLayout(0, 0));

        // --- SEZIONE MENU ---
        panelMenu = new JPanel();
        add(panelMenu, "menu");
        panelMenu.setLayout(new BorderLayout(0, 0));

        lblMenuImage = new JLabel(new ScaledImageIcon(Panel.class.getResource("/images/Restaurant Simulator V2.png")));
        lblMenuImage.setOpaque(true);
        panelMenu.add(lblMenuImage, BorderLayout.CENTER);

        panelPulsantiMenu = new JPanel();
        panelMenu.add(panelPulsantiMenu, BorderLayout.SOUTH);

        btnClassifica = new JButton("Classifica");
        panelPulsantiMenu.add(btnClassifica);

        btnNuovaPartita = new JButton("Nuova partita");
        panelPulsantiMenu.add(btnNuovaPartita);
        
        panelSala = new JPanel();
        add(panelSala, "sala");
        panelSala.setLayout(new GridLayout(5, 5, 0, 0));

        for (int i = 0; i < pannelliGriglia.length; i++) {
            pannelliGriglia[i] = new JPanel();
            panelSala.add(pannelliGriglia[i]);
        }
    }

    public JPanel[] getPannelliGriglia() {
        return pannelliGriglia;
    }
}
