package fr.mineusb.ui.panels;

import java.awt.Color;

import javax.swing.JLabel;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.mineusb.utils.TexturedPanel;

public class CreditsPanel extends TexturedPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8304163889194714767L;

	private static JLabel credits;
	private static JLabel w67clement;
	private static JLabel mentor6561;
	private static JLabel specialThanks;
	private static JLabel theshark34;
	private static JLabel supporters;
	private static JLabel xolider;

	public CreditsPanel() {
		super(MineUSBConstants.DIRT_PATH);
		this.setLayout(null);
		this.add(getCreditsLabel());
		this.add(getW67clementLabel());
		this.add(getMentor6561Label());
		this.add(getXoliderLabel());
		this.add(getSpecialThanksLabel());
		this.add(getTheShark34Label());
		this.add(getSupportersLabel());
	}

	public static JLabel getCreditsLabel() {
		if (credits == null) {
			credits = new JLabel();
			credits.setText("<html><b>"
					+ MineUSB.getLangUsed().getCreditsText() + "</b></html>");
			credits.setForeground(Color.WHITE);
			credits.setFont(credits.getFont().deriveFont(16F));
			credits.setBounds(210, 10, 200, 30);
		}
		return credits;
	}

	public static JLabel getW67clementLabel() {
		if (w67clement == null) {
			w67clement = new JLabel();
			w67clement
					.setText("<html>- <b><font color=\"#00CC00\">w67clement</font></b>: "
							+ MineUSB.getLangUsed().getW67clementDescription()
							+ ".<html>");
			w67clement.setForeground(Color.WHITE);
			w67clement.setBounds(90, 50, 450, 20);
		}
		return w67clement;
	}
	
	public static JLabel getXoliderLabel() {
		if(xolider == null) {
			xolider = new JLabel();
			xolider.setText("<html>- <b><font color=\"#00CC00\">Xolider</font></b>: Developper Java. </html>");
			xolider.setForeground(Color.WHITE);
			xolider.setBounds(90, 90, 450, 20);
		}
		return xolider;
	}

	public static JLabel getMentor6561Label() {
		if (mentor6561 == null) {
			mentor6561 = new JLabel();
			mentor6561
					.setText("<html>- <b><font color=\"#00CC00\">mentor6561</font></b>: "
							+ MineUSB.getLangUsed().getMentor6561Description()
							+ ".</html>");
			mentor6561.setForeground(Color.WHITE);
			mentor6561.setBounds(90, 70, 450, 20);
		}
		return mentor6561;
	}

	public static JLabel getSpecialThanksLabel() {
		if (specialThanks == null) {
			specialThanks = new JLabel();
			specialThanks.setText("<html><b>"
					+ MineUSB.getLangUsed().getSpecialThanksToText()
					+ "</b></html>");
			specialThanks.setForeground(Color.WHITE);
			specialThanks.setFont(credits.getFont().deriveFont(14F));
			specialThanks.setBounds(180, 100, 300, 30);
		}
		return specialThanks;
	}

	public static JLabel getTheShark34Label() {
		if (theshark34 == null) {
			theshark34 = new JLabel();
			theshark34
					.setText("<html>- <b><font color=\"#00CC00\">TheShark34</font></b>: "
							+ MineUSB.getLangUsed().getTheShark34Description()
							+ ".</html>");
			theshark34.setForeground(Color.WHITE);
			theshark34.setBounds(90, 130, 450, 20);
		}
		return theshark34;
	}

	public static JLabel getSupportersLabel() {
		if (supporters == null) {
			supporters = new JLabel();
			supporters
					.setText("<html><b>"
							+ MineUSB.getLangUsed().getSupportersText()
							+ ".</b></html>");
			supporters.setForeground(Color.WHITE);
			supporters.setBounds(90, 150, 450, 20);
		}
		return supporters;
	}
}
