package fr.mineusb.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;

public class LicenseFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5181849295808467994L;

	private JTextArea licenseTextArea;
	private final JPopupMenu popupMenu = new JPopupMenu();
	private final JMenuItem selectAllButton = new JMenuItem("Select All Text");
	private final JMenuItem copyTextButton = new JMenuItem("Copy All Text");

	public LicenseFrame() {
		licenseTextArea = new JTextArea();
		InputStream stream = MineUSB.class
				.getResourceAsStream("/fr/mineusb/res/LICENSE.txt");
		InputStreamReader reader = new InputStreamReader(stream);
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(reader);
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String text = "";
		for (String line : lines) {
			text = text + line + "\n";
		}
		this.licenseTextArea.setText(text);
		this.licenseTextArea.setEditable(false);

		this.popupMenu.add(this.selectAllButton);
		this.popupMenu.add(this.copyTextButton);
		this.licenseTextArea.setComponentPopupMenu(this.popupMenu);

		this.copyTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					StringSelection ss = new StringSelection(
							LicenseFrame.this.licenseTextArea.getText());
					Toolkit.getDefaultToolkit().getSystemClipboard()
							.setContents(ss, null);
				} catch (Exception localException) {
				}
			}
		});

		this.selectAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LicenseFrame.this.licenseTextArea.setSelectionStart(0);
				LicenseFrame.this.licenseTextArea
						.setSelectionEnd(LicenseFrame.this.licenseTextArea
								.getText().length());
			}
		});

		this.setTitle("MineUSB - License");
		this.setPreferredSize(new Dimension(500, 300));
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				MineUSB.class.getResource(MineUSBConstants.FAVICON_PATH)));
		this.setContentPane(new JScrollPane(licenseTextArea));
	}

}
