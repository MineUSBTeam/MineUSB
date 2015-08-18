package fr.mineusb.ui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.mineusb.exceptions.LangNotFoundException;
import fr.mineusb.system.MineUSBConfig;
import fr.mineusb.system.lang.LangManager;
import fr.mineusb.utils.TexturedPanel;
import fr.w67clement.core.utils.Utils;

public class OptionsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2899486185649359809L;

	private Image image;
	private Image bgImage;

	// Labels
	private static JLabel changeLangLabel;
	private static JLabel websitesOnlines;
	private static JLabel minecraftWebsite;
	private static JLabel skinsServerOnline;
	private static JLabel sessionServerOnline;
	// Buttons
	private static JButton viewLicenseButton;
	private static JButton reloadButton;
	private static JButton quitButton;
	// ComboBox
	private static JComboBox<String> langList;

	public OptionsPanel() {

		TexturedPanel websites = new TexturedPanel(MineUSBConstants.STONE_PATH);

		websites.setPreferredSize(new Dimension(150, 125));

		websites.add(getWebsitesOnlineLabel());
		websites.add(getMinecraftWebSiteOnlineLabel());
		websites.add(getSkinsServerOnlineLabel());
		websites.add(getMultiplayerSessionOnlineLabel());

		this.add(websites);

		TexturedPanel buttons = new TexturedPanel(MineUSBConstants.STONE_PATH);

		buttons.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = 2;
		constraints.gridx = 0;
		constraints.gridy = -1;

		buttons.setSize(150, 200);
		buttons.setPreferredSize(new Dimension(150, 150));

		buttons.add(getViewLicenseButton(), constraints);
		buttons.add(getReloadButton(), constraints);
		buttons.add(getChangLangLabel(), constraints);
		buttons.add(getLangList(), constraints);
		buttons.add(getQuitButton(), constraints);

		this.add(buttons);

		try {
			this.bgImage = ImageIO.read(
					MineUSB.class.getResource(MineUSBConstants.DIRT_PATH))
					.getScaledInstance(32, 32, 16);
		} catch (IOException e) {
			MineUSB.getConsole().error(
					"Unexpected exception initializing MineUSB's panel");
		} catch (IllegalArgumentException ex) {
			MineUSB.getConsole().error(
					"Unexpected exception initializing MineUSB's panel");
			MineUSB.getConsole().error("Reason: Image is null?");
		}
	}

	public static JButton getViewLicenseButton() {
		if (viewLicenseButton == null) {
			viewLicenseButton = new JButton();
			viewLicenseButton.setText(MineUSB.getLangUsed()
					.getViewLicenseButton());
			viewLicenseButton.setBorderPainted(false);
			viewLicenseButton.setOpaque(false);
			viewLicenseButton.setPreferredSize(new Dimension(135, 25));
			viewLicenseButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					MineUSB.getLicenseFrame().setVisible(true);
				}

			});
		}
		return viewLicenseButton;
	}

	public static JButton getReloadButton() {
		if (reloadButton == null) {
			reloadButton = new JButton();
			reloadButton.setText(MineUSB.getLangUsed().getReloadButton());
			reloadButton.setBorderPainted(false);
			reloadButton.setOpaque(false);
			reloadButton.setPreferredSize(new Dimension(135, 25));
			reloadButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					MineUSBConfig.reloadConfiguration();
					MineUSB.applyLang(MineUSB.getLangUsed());
					MineUSB.applyLauncher(MineUSB.getLauncherUsed());
				}

			});
		}
		return reloadButton;
	}

	public static JButton getQuitButton() {
		if (quitButton == null) {
			quitButton = new JButton();
			quitButton.setText(MineUSB.getLangUsed().getQuitButton());
			quitButton.setBorderPainted(false);
			quitButton.setOpaque(false);
			quitButton.setPreferredSize(new Dimension(135, 25));
			quitButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					MineUSB.getOptionsFrame().setVisible(false);
				}

			});
		}
		return quitButton;
	}

	public static JLabel getChangLangLabel() {
		if (changeLangLabel == null) {
			changeLangLabel = new JLabel();
			changeLangLabel.setText("<html><br /><b>"
					+ MineUSB.getLangUsed().getChangeLangLabel()
					+ "</b></html>");
			changeLangLabel.setForeground(Color.WHITE);
		}
		return changeLangLabel;
	}

	public static JComboBox<String> getLangList() {
		if (langList == null) {
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					for (fr.mineusb.system.lang.Lang lang : LangManager
							.getLangList()) {
						addElement(lang.getName());
					}
				}
			};
			langList = new JComboBox<String>(model);
			langList.setSelectedItem(MineUSB.getLangUsed().getName());
			langList.setOpaque(false);
			langList.setPreferredSize(new Dimension(135, 25));
			langList.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					try {
						MineUSB.getConfig().setLang(
								LangManager.getLangByName(
										(String) langList.getSelectedItem())
										.getId());
					} catch (LangNotFoundException e1) {
						e1.printStackTrace();
					}
					MineUSBConfig.reloadConfiguration();
					MineUSB.applyLang(MineUSB.getLangUsed());
				}
			});
		}
		return langList;
	}

	public static JLabel getWebsitesOnlineLabel() {
		if (websitesOnlines == null) {
			websitesOnlines = new JLabel();
			websitesOnlines
					.setText("<html><b>"
							+ MineUSB.getLangUsed().getWebsitesOnline()
							+ "</b></html>");
			websitesOnlines.setForeground(Color.WHITE);
		}
		return websitesOnlines;
	}

	public static JLabel getMinecraftWebSiteOnlineLabel() {
		if (minecraftWebsite == null) {
			minecraftWebsite = new JLabel();
			minecraftWebsite.setText("<html>&nbsp;"
					+ MineUSB.getLangUsed().getLoadingText()
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>");
			new Thread() {
				public void run() {
					try {
						if (Utils.isOnline("https://minecraft.net")) {
							minecraftWebsite
									.setText("<html>https://minecraft.net <font color=\"#00CC00\">\u2714</font</html>");
						} else {
							minecraftWebsite
									.setText("<html>https://minecraft.net <font color=\"#FF0000\">✖</font</html>");
						}
					} catch (MalformedURLException e) {
						minecraftWebsite
								.setText("<html>https://minecraft.net <font color=\"#FF0000\">✖</font</html>");
					}
				}
			}.start();
			minecraftWebsite.setForeground(Color.WHITE);
		}
		return minecraftWebsite;
	}

	public static JLabel getSkinsServerOnlineLabel() {
		if (skinsServerOnline == null) {
			skinsServerOnline = new JLabel();
			skinsServerOnline.setText("<html>&nbsp;"
					+ MineUSB.getLangUsed().getLoadingText()
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>");
			new Thread() {
				public void run() {
					try {
						if (Utils.isOnline("https://skins.minecraft.net")) {
							skinsServerOnline
									.setText("<html>"
											+ MineUSB.getLangUsed()
													.getSkinsServerLabel()
											+ " <font color=\"#00CC00\">\u2714</font</html>");
						} else {
							skinsServerOnline
									.setText("<html>"
											+ MineUSB.getLangUsed()
													.getSkinsServerLabel()
											+ " <font color=\"#FF0000\">✖</font</html>");
						}
					} catch (MalformedURLException e) {
						skinsServerOnline.setText("<html>"
								+ MineUSB.getLangUsed().getSkinsServerLabel()
								+ " <font color=\"#FF0000\">✖</font</html>");
					}
				}
			}.start();
			skinsServerOnline.setForeground(Color.WHITE);
		}
		return skinsServerOnline;
	}

	public static JLabel getMultiplayerSessionOnlineLabel() {
		if (sessionServerOnline == null) {
			sessionServerOnline = new JLabel();
			sessionServerOnline.setText("<html>&nbsp;"
					+ MineUSB.getLangUsed().getLoadingText()
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>");
			new Thread() {

				public void run() {
					try {
						if (Utils.isOnline("https://sessionserver.mojang.com")) {
							sessionServerOnline
									.setText("<html>"
											+ MineUSB
													.getLangUsed()
													.getMultiplayerSessionLabel()
											+ " <font color=\"#00CC00\">\u2714</font</html>");
						} else {
							sessionServerOnline
									.setText("<html>"
											+ MineUSB
													.getLangUsed()
													.getMultiplayerSessionLabel()
											+ " <font color=\"#FF0000\">✖</font</html>");
						}
					} catch (MalformedURLException e) {
						sessionServerOnline.setText("<html>"
								+ MineUSB.getLangUsed()
										.getMultiplayerSessionLabel()
								+ " <font color=\"#FF0000\">✖</font</html>");
					}
				}
			}.start();
			sessionServerOnline.setForeground(Color.WHITE);
		}
		return sessionServerOnline;
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
