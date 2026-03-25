package view;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
    private CardLayout cardLayout;
    private MenuPanel menuPanel;
    private SalaPanel salaPanel;
    private ClassificaPanel classificaPanel;
    private CucinaPanel cucinaPanel;

    public MainPanel() {
    	cardLayout = new CardLayout(0, 0);
        setLayout(cardLayout);
        
        menuPanel = new MenuPanel();
        add(menuPanel, "menu");

        salaPanel = new SalaPanel();
        add(salaPanel, "sala");

        classificaPanel = new ClassificaPanel();
        add(classificaPanel, "classifica");
        
        cucinaPanel = new CucinaPanel();
        add(cucinaPanel, "cucina");
    }
    
    public void setStatoTavolo(int tavolo, int stato) {
    	switch(stato) {
    		case 1:
    			salaPanel.getTavolo(tavolo).setBackground(Color.green);
    			break;
    		case 2:
    			salaPanel.getTavolo(tavolo).setBackground(Color.yellow);
    			break;
    		case 3:
    			salaPanel.getTavolo(tavolo).setBackground(Color.red);
    			break;
    	}
    }
}
