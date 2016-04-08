package fr.mineusb.ui.panels;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.mineusb.exceptions.LangNotFoundException;
import fr.mineusb.system.lang.LangManager;
import fr.mineusb.utils.TexturedPanel;
import fr.w67clement.core.utils.Utils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.net.MalformedURLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class OptionsPanel extends TexturedPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2899486185649359809L;

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
		super(MineUSBConstants.DIRT_PATH);

		TexturedPanel websites = new TexturedPanel(MineUSBConstants.STONE_PATH);

		websites.setPreferredSize(new Dimension(175, 125));

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
	}

	public static JButton getViewLicenseButton()
	{
		if (viewLicenseButton == null)
		{
			viewLicenseButton = new JButton();
			viewLicenseButton
					.setText(MineUSB.getLangUsed().getViewLicenseButton());
			if (!MineUSB.hasJavaDesign())
			{
				viewLicenseButton.setBorderPainted(false);
				viewLicenseButton.setOpaque(false);
			}
			viewLicenseButton.setPreferredSize(new Dimension(135, 25));
			viewLicenseButton.addActionListener(e -> MineUSB.getLicenseFrame().setVisible(true));
		}
		return viewLicenseButton;
	}

	public static JButton getReloadButton()
	{
		if (reloadButton == null)
		{
			reloadButton = new JButton();
			reloadButton.setText(MineUSB.getLangUsed().getReloadButton());
			if (!MineUSB.hasJavaDesign())
			{
				reloadButton.setBorderPainted(false);
				reloadButton.setOpaque(false);
			}
			reloadButton.setPreferredSize(new Dimension(135, 25));
			reloadButton.addActionListener(e -> {
                MineUSB.getConfig().reloadConfiguration();
                MineUSB.applyLang(MineUSB.getLangUsed());
                MineUSB.applyLauncher(MineUSB.getLauncherUsed());
            });
		}
		return reloadButton;
	}

	public static JButton getQuitButton()
	{
		if (quitButton == null)
		{
			quitButton = new JButton();
			quitButton.setText(MineUSB.getLangUsed().getQuitButton());
			if (!MineUSB.hasJavaDesign())
			{
				quitButton.setBorderPainted(false);
				quitButton.setOpaque(false);
			}
			quitButton.setPreferredSize(new Dimension(135, 25));
			quitButton.addActionListener(e -> MineUSB.getOptionsFrame().setVisible(false));
		}
		return quitButton;
	}

	public static JLabel getChangLangLabel()
	{
		if (changeLangLabel == null)
		{
			changeLangLabel = new JLabel();
			changeLangLabel.setText("<html><br /><b>"
					+ MineUSB.getLangUsed().getChangeLangLabel()
					+ "</b></html>");
			changeLangLabel.setForeground(Color.WHITE);
		}
		return changeLangLabel;
	}

	public static JComboBox<String> getLangList()
	{
		if (langList == null)
		{
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					for (fr.mineusb.system.lang.Lang lang : LangManager
							.getLangList())
					{
						addElement(lang.getName());
					}
				}
			};
			langList = new JComboBox<>(model);
			langList.setSelectedItem(MineUSB.getLangUsed().getName());
			langList.setOpaque(false);
			langList.setPreferredSize(new Dimension(135, 25));
			langList.addActionListener(e -> {
                try
                {
                    MineUSB.getConfig()
                            .setLang(
                                    LangManager
                                            .getLangByName((String) langList
                                                    .getSelectedItem())
                                    .getId());
                }
                catch (LangNotFoundException e1)
                {
                    e1.printStackTrace();
                }
                MineUSB.applyLang(MineUSB.getLangUsed());
            });
		}
		return langList;
	}

	public static JLabel getWebsitesOnlineLabel()
	{
		if (websitesOnlines == null)
		{
			websitesOnlines = new JLabel();
			websitesOnlines.setText(
					"<html><b>" + MineUSB.getLangUsed().getWebsitesOnline()
							+ "</b></html>");
			websitesOnlines.setForeground(Color.WHITE);
		}
		return websitesOnlines;
	}

	public static JLabel getMinecraftWebSiteOnlineLabel()
	{
		if (minecraftWebsite == null)
		{
			minecraftWebsite = new JLabel();
			minecraftWebsite.setText("<html>&nbsp;"
					+ MineUSB.getLangUsed().getLoadingText()
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>");
			new Thread() {
				public void run()
				{
					try
					{
						if (Utils.isOnline("https://minecraft.net"))
						{
							minecraftWebsite.setText(
									"<html>https://minecraft.net <font color=\"#00CC00\">\u2714</font</html>");
						}
						else
						{
							minecraftWebsite.setText(
									"<html>https://minecraft.net <font color=\"#FF0000\">✖</font</html>");
						}
					}
					catch (MalformedURLException e)
					{
						minecraftWebsite.setText(
								"<html>https://minecraft.net <font color=\"#FF0000\">✖</font</html>");
					}
				}
			}.start();
			minecraftWebsite.setForeground(Color.WHITE);
		}
		return minecraftWebsite;
	}

	public static JLabel getSkinsServerOnlineLabel()
	{
		if (skinsServerOnline == null)
		{
			skinsServerOnline = new JLabel();
			skinsServerOnline.setText("<html>&nbsp;"
					+ MineUSB.getLangUsed().getLoadingText()
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>");
			new Thread() {
				public void run()
				{
					try
					{
						if (Utils.isOnline("https://skins.minecraft.net"))
						{
							skinsServerOnline.setText("<html>"
									+ MineUSB.getLangUsed()
											.getSkinsServerLabel()
									+ " <font color=\"#00CC00\">\u2714</font</html>");
						}
						else
						{
							skinsServerOnline.setText("<html>"
									+ MineUSB.getLangUsed()
											.getSkinsServerLabel()
									+ " <font color=\"#FF0000\">✖</font</html>");
						}
					}
					catch (MalformedURLException e)
					{
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

	public static JLabel getMultiplayerSessionOnlineLabel()
	{
		if (sessionServerOnline == null)
		{
			sessionServerOnline = new JLabel();
			sessionServerOnline.setText(
					"<html>&nbsp;" + MineUSB.getLangUsed().getLoadingText()
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>");
			new Thread() {

				public void run()
				{
					try
					{
						if (Utils.isOnline("https://sessionserver.mojang.com"))
						{
							sessionServerOnline.setText("<html>"
									+ MineUSB.getLangUsed()
											.getMultiplayerSessionLabel()
									+ " <font color=\"#00CC00\">\u2714</font</html>");
						}
						else
						{
							sessionServerOnline.setText("<html>"
									+ MineUSB.getLangUsed()
											.getMultiplayerSessionLabel()
									+ " <font color=\"#FF0000\">✖</font</html>");
						}
					}
					catch (MalformedURLException e)
					{
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
}
