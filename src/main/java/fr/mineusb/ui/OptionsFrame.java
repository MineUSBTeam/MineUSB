package fr.mineusb.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.mineusb.ui.panels.OptionsPanel;

public class OptionsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OptionsFrame() {
		this.setTitle("MineUSB - Options");
		this.setPreferredSize(new Dimension(450, 200));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						MineUSB.class
								.getResource(MineUSBConstants.FAVICON_PATH)));
		this.setContentPane(new JScrollPane(new OptionsPanel()));
	}

}
