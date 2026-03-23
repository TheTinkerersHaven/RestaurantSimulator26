package view;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class Panel extends JPanel {
    private JPanel panelMenu;
    private JPanel panelPulsantiMenu;
    private JLabel lblIcona;
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

        lblIcona = new JLabel("");
        // Usiamo ComponentAdapter per scrivere meno codice (sovrascriviamo solo quello che serve)
        lblIcona.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                aggiornaIcona();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                aggiornaIcona();
            }
        });
        panelMenu.add(lblIcona, BorderLayout.CENTER);

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

    private void aggiornaIcona() {
        if (lblIcona.getWidth() > 0 && lblIcona.getHeight() > 0) {
            URL imgUrl = Panel.class.getResource("/images/Restaurant Simulator V2.png");
            if (imgUrl != null) {
                ImageIcon img = new ImageIcon(imgUrl);
                Image scaledImg = img.getImage().getScaledInstance(lblIcona.getWidth(), lblIcona.getHeight(), Image.SCALE_SMOOTH);
                lblIcona.setIcon(new ImageIcon(scaledImg));
            }
        }
    }

    public JLabel getLblIcona() {
        return lblIcona;
    }

    public JPanel[] getPannelliGriglia() {
        return pannelliGriglia;
    }
}