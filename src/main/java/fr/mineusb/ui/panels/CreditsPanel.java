package fr.mineusb.ui.panels;

import java.awt.Color;

import javax.swing.JLabel;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.mineusb.utils.TexturedPanel;

public class CreditsPanel extends TexturedPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8304163889194714767L;

	private static JLabel credits;
	private static JLabel w67clement;
	private static JLabel xolider;
	private static JLabel mentor6561;
	private static JLabel specialThanks;
	private static JLabel litarvan;
	private static JLabel supporters;
	private static JLabel technologies;
	private static JLabel technologiesValues;

	public CreditsPanel() {
		super(MineUSBConstants.DIRT_PATH);
		this.setLayout(null);
		this.add(getCreditsLabel());
		this.add(getW67clementLabel());
		this.add(getXoliderLabel());
		this.add(getMentor6561Label());
		this.add(getSpecialThanksLabel());
		this.add(getLitarvanLabel());
		this.add(getSupportersLabel());
		this.add(getTechnologies());
		this.add(getTechnologiesValues());
	}

	public static JLabel getCreditsLabel()
	{
		if (credits == null)
		{
			credits = new JLabel();
			credits.setText("<html><b>" + MineUSB.getLangUsed().getCreditsText()
					+ "</b></html>");
			credits.setForeground(Color.WHITE);
			credits.setFont(credits.getFont().deriveFont(16F));
			credits.setBounds(210, 10, 200, 30);
		}
		return credits;
	}

	public static JLabel getW67clementLabel()
	{
		if (w67clement == null)
		{
			w67clement = new JLabel();
			w67clement.setText(
					"<html>- <b><font color=\"#00CC00\">w67clement</font></b>: "
							+ MineUSB.getLangUsed().getW67clementDescription()
							+ ".<html>");
			w67clement.setForeground(Color.WHITE);
			w67clement.setBounds(90, 50, 450, 20);
		}
		return w67clement;
	}

	public static JLabel getXoliderLabel()
	{
		if (xolider == null)
		{
			xolider = new JLabel();
			xolider.setText(
					"<html>- <b><font color=\"#00CC00\">xolider</font></b>: "
							+ MineUSB.getLangUsed().getXoliderDescription()
							+ ".<html>");
			xolider.setForeground(Color.WHITE);
			xolider.setBounds(90, 65, 450, 20);
		}
		return xolider;
	}

	public static JLabel getMentor6561Label()
	{
		if (mentor6561 == null)
		{
			mentor6561 = new JLabel();
			mentor6561.setText(
					"<html>- <b><font color=\"#00CC00\">mentor6561</font></b>: "
							+ MineUSB.getLangUsed().getMentor6561Description()
							+ ".</html>");
			mentor6561.setForeground(Color.WHITE);
			mentor6561.setBounds(90, 80, 450, 20);
		}
		return mentor6561;
	}

	public static JLabel getSpecialThanksLabel()
	{
		if (specialThanks == null)
		{
			specialThanks = new JLabel();
			specialThanks.setText(
					"<html><b>" + MineUSB.getLangUsed().getSpecialThanksToText()
							+ "</b></html>");
			specialThanks.setForeground(Color.WHITE);
			specialThanks.setFont(credits.getFont().deriveFont(14F));
			specialThanks.setBounds(180, 100, 300, 30);
		}
		return specialThanks;
	}

	public static JLabel getLitarvanLabel()
	{
		if (litarvan == null)
		{
			litarvan = new JLabel();
			litarvan.setText(
					"<html>- <b><font color=\"#00CC00\">Litarvan</font></b>: "
							+ MineUSB.getLangUsed().getTheShark34Description()
							+ ".</html>");
			litarvan.setForeground(Color.WHITE);
			litarvan.setBounds(90, 130, 450, 20);
		}
		return litarvan;
	}

	public static JLabel getSupportersLabel()
	{
		if (supporters == null)
		{
			supporters = new JLabel();
			supporters.setText(
					"<html><b>" + MineUSB.getLangUsed().getSupportersText()
							+ ".</b></html>");
			supporters.setForeground(Color.WHITE);
			supporters.setBounds(90, 150, 450, 20);
		}
		return supporters;
	}

	public static JLabel getTechnologies()
	{
		if (technologies == null)
		{
			technologies = new JLabel();
			technologies.setText(
					"<html><b>" + MineUSB.getLangUsed().getTechnologiesText()
							+ ":</b></html>");
			technologies.setForeground(Color.WHITE);
			technologies.setFont(credits.getFont().deriveFont(14F));
			technologies.setBounds(180, 170, 300, 30);
		}
		return technologies;
	}

	public static JLabel getTechnologiesValues()
	{
		if (technologiesValues == null)
		{
			technologiesValues = new JLabel();
			technologiesValues.setText(
					"<html><b><font color=\"#00CC00\">ClemCore (1.0.4)</font></b>,"
							+ " <b><font color=\"#00CC00\">Gson (2.4)</font></b></html>");
			technologiesValues.setForeground(Color.WHITE);
			technologiesValues.setBounds(90, 197, 450, 20);
		}
		return technologiesValues;
	}
}
