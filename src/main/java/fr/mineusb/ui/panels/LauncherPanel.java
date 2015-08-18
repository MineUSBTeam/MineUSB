package fr.mineusb.ui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.w67clement.core.logger.MineLogger;

public class LauncherPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Image image;
	private Image bgImage;

	// Logger
	private static MineLogger logger = MineUSB.getConsole();

	public LauncherPanel() {
		JLabel comingSoonLabel = new JLabel();
		comingSoonLabel.setForeground(Color.WHITE);
		comingSoonLabel.setBounds(15, 66, 365, 44);
		comingSoonLabel.setPreferredSize(new Dimension(385, 50));
		comingSoonLabel.setHorizontalAlignment(0);
		comingSoonLabel
				.setText("<html><br /><br /><br /> Hello! <br /> This part was not finished! <br /> Please wait...</html>");
		
		this.add(comingSoonLabel);
		// Background
		try {
			this.bgImage = ImageIO.read(
					MineUSB.class.getResource(MineUSBConstants.DIRT_PATH))
					.getScaledInstance(32, 32, 16);
		} catch (IOException e) {
			logger.error("Unexpected exception initializing MineUSB's panel");
		} catch (IllegalArgumentException ex) {
			logger.error("Unexpected exception initializing MineUSB's panel");
			logger.error("Reason: Image is null?");
		}
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paintComponent(Graphics graphics) {
		int width = getWidth() / 2 + 1;
		int height = getHeight() / 2 + 1;
		if ((this.image == null) || (this.image.getWidth(null) != width)
				|| (this.image.getHeight(null) != height)) {
			this.image = createImage(width, height);
			copyImage(width, height);
		}
		graphics.drawImage(this.image, 0, 0, width * 2, height * 2, null);
	}

	protected void copyImage(int width, int height) {
		Graphics imageGraphics = this.image.getGraphics();
		for (int x = 0; x <= width / 32; x++) {
			for (int y = 0; y <= height / 32; y++) {
				imageGraphics.drawImage(this.bgImage, x * 32, y * 32, null);
			}
		}
		if ((imageGraphics instanceof Graphics2D)) {
			overlayGradient(width, height, (Graphics2D) imageGraphics);
		}
		imageGraphics.dispose();
	}

	protected void overlayGradient(int width, int height, Graphics2D graphics) {
		int gh = 1;
		graphics.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F),
				new Color(553648127, true), new Point2D.Float(0.0F, gh),
				new Color(0, true)));
		graphics.fillRect(0, 0, width, gh);

		gh = height;
		graphics.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F),
				new Color(0, true), new Point2D.Float(0.0F, gh), new Color(
						1610612736, true)));
		graphics.fillRect(0, 0, width, gh);
	}

}
