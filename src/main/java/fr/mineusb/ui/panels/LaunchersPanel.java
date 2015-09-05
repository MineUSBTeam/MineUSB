package fr.mineusb.ui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.mineusb.exceptions.LauncherNotFoundException;
import fr.mineusb.system.MineUSBConfig;
import fr.mineusb.system.launchers.Launcher;
import fr.mineusb.system.launchers.LauncherManager;
import fr.mineusb.utils.TexturedPanel;
import fr.w67clement.core.system.OS;

public class LaunchersPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1493496059457727783L;

	private Image image;
	private Image bgImage;

	private static JLabel launcherLogo;
	private static JLabel launcherInfos;
	private static JComboBox<String> launcherList = null;
	private static DefaultComboBoxModel<String> model;

	public LaunchersPanel() {
		this.setLayout(null);

		TexturedPanel stonePanel = new TexturedPanel(
				MineUSBConstants.STONE_PATH);

		stonePanel.setBounds(60, 20, 375, 200);
		
		stonePanel.add(getLauncherLogo());
		stonePanel.add(getLauncherInformations());
		stonePanel.add(getLauncherList());
		
		this.add(stonePanel);

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
	
	public static JComboBox<String> getLauncherList() {
		if (launcherList == null) {
			model = new DefaultComboBoxModel<String>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					for (Launcher launchers : LauncherManager.getLauncherList()) {
						addElement(launchers.getName());
					}
				}
			};
			launcherList = new JComboBox<String>(model);
			launcherList.setSelectedItem(MineUSB.getLauncherUsed().getName());
			launcherList.setOpaque(false);
			launcherList.setPreferredSize(new Dimension(128, 32));
			launcherList.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent paramActionEvent) {
					MineUSB.getConfig().setLauncherType(String.valueOf(launcherList.getSelectedItem()));
					MineUSBConfig.reloadConfiguration();
					MineUSB.applyLauncher(MineUSB.getLauncherUsed());
				}

			});
			return launcherList;
		}
		return launcherList;
	}

	public static JLabel getLauncherLogo() {
		if (launcherLogo == null) {
			launcherLogo = new JLabel();
			new Thread() {
				public void run() {
					try {
						launcherLogo.setIcon(LauncherManager.getLauncher(
								MineUSB.getConfig().getLauncherType())
								.getImageIcon());
					} catch (LauncherNotFoundException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
		return launcherLogo;
	}

	public static JLabel getLauncherInformations() {
		if (launcherInfos == null) {
			launcherInfos = new JLabel();
			Launcher launcher = MineUSB.getLauncherUsed();
			String os = "";
			for (int i = 0; i < launcher.getOSCompatibilityList().size(); i++) {
				OS os2 = launcher.getOSCompatibilityList().get(i);
				if (i == 0) {
					os = os + os2.getOSName();
				} else
					os = os + ", " + os2.getOSName();
			}
			launcherInfos
					.setText("<html><b>Launcher: </b><font color=\"#00CC00\">"
							+ launcher.getName() + "</font><br />"
							+ "<b>Description: </b><font color=\"#00CC00\">"
							+ launcher.getDescription() + "</font><br />"
							+ "<b>OS: </b><font color=\"#00CC00\">" + os
							+ "</font></html>");
			launcherInfos.setPreferredSize(new Dimension(375, 75));
			launcherInfos.setForeground(Color.WHITE);
		}
		return launcherInfos;
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
	
	public static DefaultComboBoxModel<String> getModel() {
		return model;
	}

}
