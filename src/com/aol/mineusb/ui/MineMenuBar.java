package com.aol.mineusb.ui;

import javax.swing.JTabbedPane;

import com.aol.mineusb.ui.panels.LauncherPanel;
import com.aol.mineusb.ui.panels.MineUSBPanel;

public class MineMenuBar extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -481339771548861608L;
	
	private static MineMenuBar instance;
	
	private static MineUSBPanel mainPanel;
	private static LauncherPanel launcherPanel;

	public MineMenuBar() {
		instance = this;
		
		mainPanel = new MineUSBPanel();
		launcherPanel = new LauncherPanel();
		
		this.addTab("Main", mainPanel);
		this.addTab("Launcher", launcherPanel);
		this.addTab("Launchers", null);
		
		this.setSelectedComponent(mainPanel);
	}
	
	public static MineMenuBar getInstance() {
		return instance;
	}
}
