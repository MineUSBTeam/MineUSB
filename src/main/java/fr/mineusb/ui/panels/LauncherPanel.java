package fr.mineusb.ui.panels;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.mineusb.launcher.LauncherFrame;
import fr.mineusb.ui.MineUSBFrame;
import fr.w67clement.core.logger.MineLogger;

public class LauncherPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Image image;
	private Image bgImage;
	
	private static JLabel desc;
	private static JButton play;

	// Logger
	private static MineLogger logger = MineUSB.getConsole();

	public LauncherPanel() {
		this.setLayout(null);
		JLabel icon = new JLabel();
		icon.setIcon(new ImageIcon(getClass().getResource(MineUSBConstants.FAVICON_PATH)));
		icon.setBounds(200, 0, 100, 100);
		this.add(icon);
		
		desc = new JLabel();
		desc.setForeground(Color.white);
		desc.setText("<html><br/><br/>" + MineUSB.getLangUsed().getDescIntegratedLauncher() + "</html>");
		desc.setBounds(155, 80, 200, 40);
		this.add(desc);
		
		play = new JButton("Play !");
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new LauncherFrame();
			}
		});
		play.setBounds(185, 140, 100, 40);
		this.add(play);
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
	
	public static JLabel getDesriptionLabel() {
		return desc;
	}
}
