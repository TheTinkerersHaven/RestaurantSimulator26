package restaurantsim.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * ImageIcon that scaled the image to fill the entire component it is painted on.
 */
@SuppressWarnings("serial")
public class ScaledImageIcon extends ImageIcon {
	/**
	 * Original image loaded from the toolkit.
	 * 
	 * Used to rescale the image when the component size changes, to avoid scaling an already scaled image multiple times which would cause quality loss.
	 */
	private Image originalImage;
	/**
	 * Current scale of the image, used to check if the component size has changed and the image needs to be rescaled before painting it.
	 */
	private Dimension currentScale;

	/**
	 * Inizialize the ScaledImageIcon with the image loaded from the given URL, and initialize the originalImage and currentScale fields
	 * 
	 * @param location the URL of the image to be loaded and scaled
	 */
	public ScaledImageIcon(URL location) {
		super(location);

		originalImage = getImage();
		currentScale = new Dimension(0, 0);
	}

	/**
	 * Override the paintIcon method to scale the image to fill the entire component before painting it, checking if the component size has changed since the last time the image was scaled to avoid unnecessary rescaling and quality loss.
	 */
	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
		if (!currentScale.equals(c.getSize())) {
			scaleImage(c.getWidth(), c.getHeight());
		}

		super.paintIcon(c, g, 0, 0);
	}

	/**
	 * Scale the original image to the given width and height, update the currentScale field and set the scaled image as the image of this ImageIcon to be painted in the paintIcon method.
	 */
	private void scaleImage(int width, int height) {
		Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		currentScale = new Dimension(width, height);

		setImage(scaledImage);
	}
}
