package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class ScaledImageIcon extends ImageIcon {
	private Image originalImage;
	private Dimension currentScale;
	
	public ScaledImageIcon(URL location) {
		super(location);
		
        originalImage = getImage();
        currentScale = new Dimension(0, 0);
    }

	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
		if (!currentScale.equals(c.getSize())) {
			scaleImage(c.getWidth(), c.getHeight());
		}

		super.paintIcon(c, g, 0, 0);
	}
	
	private void scaleImage(int width, int height) {
		Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		currentScale = new Dimension(width, height);
		
		setImage(scaledImage);
	}
}
