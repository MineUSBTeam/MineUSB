package fr.mineusb.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.w67clement.core.logger.MineLogger;

public class MineUSBFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Console
	private static MineLogger console;

	public MineUSBFrame() {
		console = MineUSB.getConsole();
		console.info("Loading frame... ");
		this.setTitle("MineUSB - " + MineUSB.getVersion());
		this.setPreferredSize(new Dimension(500, 300));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						MineUSB.class
								.getResource(MineUSBConstants.FAVICON_PATH)));
		this.setContentPane(new MineMenuBar());
	}
}
