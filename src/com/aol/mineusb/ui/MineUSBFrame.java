package com.aol.mineusb.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.aol.mineusb.MineUSB;
import com.aol.w67clement.mineapi.logger.MineLogger;

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
								.getResource("/com/aol/mineusb/res/favicon.png")));
		this.setContentPane(new MineMenuBar());
	}
}
