package com.aol.mineusb.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.aol.mineusb.MineUSB;

public class OptionsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OptionsFrame() {
		this.setTitle("MineUSB - Options");
		this.setPreferredSize(new Dimension(500, 300));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						MineUSB.class
								.getResource("/com/aol/mineusb/res/favicon.png")));
	}

}
