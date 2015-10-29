package fr.mineusb.launcher;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JTextArea;

import fr.mineusb.MineUSB;
import javax.swing.JButton;
import java.io.File;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class LauncherFrame extends JFrame {
	
	private static JTextArea textArea;
	private File data;
	private JTextField textField;
	private JPasswordField passwordField;
	
	public LauncherFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		data = new File("data/");
		if(!data.exists()) {
			data.mkdir();
		}
		this.setTitle("Launcher MineUSB");
		this.setSize(800, 500);
		JPanel panel = new JPanel();
		this.setContentPane(panel);
		panel.setLayout(null);
		
		JPanel panelLog = new JPanel();
		JPanel panelProfile = new JPanel();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 784, 375);
		tabbedPane.addTab("Game logs", panelLog);
		tabbedPane.addTab("Profiles", panelProfile);
		panelProfile.setLayout(null);
		
		JLabel lblRegisteredProfiles = new JLabel(MineUSB.getLangUsed().getRegisteredProfileText());
		lblRegisteredProfiles.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRegisteredProfiles.setBounds(10, 11, 230, 25);
		panelProfile.add(lblRegisteredProfiles);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem(MineUSB.getLangUsed().getSelectProfileText());
		comboBox.setBounds(250, 17, 519, 20);
		panelProfile.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("OR");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 100, 759, 25);
		lblNewLabel.setBorder(new EmptyBorder(1, 0, 0, 0));
		panelProfile.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(101, 166, 668, 20);
		panelProfile.add(textField);
		textField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblUsername.setBounds(10, 161, 81, 25);
		panelProfile.add(lblUsername);
		
		JLabel lblPass = new JLabel("Pass");
		lblPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPass.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPass.setBounds(10, 197, 81, 25);
		panelProfile.add(lblPass);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(800/2 - 50, 280, 89, 23);
		panelProfile.add(btnConnect);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(101, 202, 668, 20);
		panelProfile.add(passwordField);
		
		panelLog.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		panelLog.add(textArea, BorderLayout.CENTER);
		panel.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 375, 784, 86);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton btnPlay = new JButton("Play");
		panel_1.add(btnPlay, BorderLayout.CENTER);
		
		JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		if(((String)comboBox.getSelectedItem()).equalsIgnoreCase(MineUSB.getLangUsed().getSelectProfileText())) {
			label.setText(MineUSB.getLangUsed().getSelectedProfileText());
		}
		else {
			label.setText(MineUSB.getLangUsed().getSelectedProfileText() + (String)comboBox.getSelectedItem());
		}
		panel_1.add(label, BorderLayout.NORTH);
		this.setVisible(true);
	}
}
