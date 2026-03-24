package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ScaledImage extends JPanel {
	private Image originalImage;
	private Image scaledImage;
	private Dimension currentScale;
	
	public ScaledImage(URL location) {
        originalImage = Toolkit.getDefaultToolkit().getImage(location);
        currentScale = new Dimension(0, 0);
    }

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (!currentScale.equals(getSize())) {
			scaleImage(getWidth(), getHeight());
		}

		g.drawImage(scaledImage, 0, 0, this);
	}
	
	private void scaleImage(int width, int height) {
		scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		currentScale = new Dimension(width, height);
	}
}
