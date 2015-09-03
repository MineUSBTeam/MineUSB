package fr.mineusb.ui;

import javax.swing.JTabbedPane;

import fr.mineusb.ui.panels.CreditsPanel;
import fr.mineusb.ui.panels.LauncherPanel;
import fr.mineusb.ui.panels.LaunchersPanel;
import fr.mineusb.ui.panels.MineUSBPanel;

public class MineMenuBar extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -481339771548861608L;

	private static MineMenuBar instance;

	private static MineUSBPanel mainPanel;
	private static LauncherPanel launcherPanel;
	private static LaunchersPanel launchersPanel;
	private static CreditsPanel creditsPanel;

	public MineMenuBar() {
		instance = this;

		mainPanel = new MineUSBPanel();
		launcherPanel = new LauncherPanel();
		launchersPanel = new LaunchersPanel();
		creditsPanel = new CreditsPanel();

		this.addTab("Main", mainPanel);
		this.addTab("Game launcher", launcherPanel);
		this.addTab("Launchers", launchersPanel);
		this.addTab("Credits", creditsPanel);

		this.setSelectedComponent(mainPanel);
	}

	public static MineMenuBar getInstance() {
		return instance;
	}
}
