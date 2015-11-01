package fr.mineusb.launcher;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JTextArea;

import fr.mineusb.MineUSB;
import fr.mineusb.auth.Auth;

import javax.swing.JButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;

import javax.swing.JTextField;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class LauncherFrame extends JFrame {
	
	private static JTextArea textArea;
	private File data;
	private JTextField textField;
	private JPasswordField passwordField;
	private final static JComboBox<String> comboBox = new JComboBox<String>();
	
	public LauncherFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		data = new File("dataLauncher/");
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
		
		comboBox.addItem(MineUSB.getLangUsed().getSelectProfileText());
		comboBox.setBounds(250, 17, 519, 20);
		load(comboBox);
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
		
		final JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblUsername.setBounds(10, 161, 81, 25);
		panelProfile.add(lblUsername);
		
		final JLabel lblPass = new JLabel("Pass");
		lblPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPass.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPass.setBounds(10, 197, 81, 25);
		panelProfile.add(lblPass);
		
		final JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(800/2 - 50, 280, 89, 23);
		panelProfile.add(btnConnect);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(101, 202, 668, 20);
		panelProfile.add(passwordField);
		
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Auth a = new Auth(textField.getText(), new String(passwordField.getPassword()));
				try {
					a.authenticate();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		panelLog.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		panelLog.add(textArea, BorderLayout.CENTER);
		panel.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 375, 784, 86);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		final JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(((String)comboBox.getSelectedItem()).equalsIgnoreCase(MineUSB.getLangUsed().getSelectProfileText())) {
					JOptionPane.showMessageDialog(btnPlay, "Veuillez séléctionner un profile", "Warning", JOptionPane.ERROR_MESSAGE);
				}
				else {
					//connection test
					String line = "";
					BufferedReader bf = null;
					try {
						bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(data, (String)comboBox.getSelectedItem() + ".dat"))));
					} catch (FileNotFoundException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					ArrayList<String> lines = new ArrayList<String>();
					try {
						while((line = bf.readLine()) != null) {
							lines.add(line);
						}
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						bf.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//connection
				}
			}
		});
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
	
	private void load(JComboBox<String> comboBox) {
		for(File f: data.listFiles()) {
			comboBox.addItem(f.getName().replaceAll(".dat", ""));
		}
	}
	
	public String encrypt(String mdp) {
		String r = null;
		for(int i = 0; i < mdp.length(); i++) {
			r += mdp.charAt(i)^48;
		}
		return r;
	}
	
	public String decrypt(String crypt) {
		String r = null;
		for(int i = 0; i < crypt.length(); i++) {
			r += crypt.charAt(i)^48;
		}
		return r;
	}
	
	private Process launch(String accessToken, String uuid, JTextArea area) throws IOException {
		ProcessBuilder pb = new ProcessBuilder();
		ArrayList<String> commands = new ArrayList<String>();
		commands.add(getJAvaPath());
		commands.add("-Djava.library.path=" + new File("game/natives/").getAbsolutePath());
		commands.add("-cp");
		commands.add(constructClasspath());
		commands.add("net.minecraft.client.main.Main");
		commands.addAll(getLauncherArgs(accessToken, uuid));
		pb.directory(new File("game/"));
		pb.command(commands);
		Process p = pb.start();
		Thread t = new Thread(new PrintProcessLog(p, area));
		t.start();
		return p;
	}
	
	private String getJAvaPath() {
		String returnString = null;
		if(System.getProperty("os.name").toLowerCase().contains("win")) {
			returnString = "\"" + System.getProperty("java.home") + "/bin/java" + "\"";
		}
		else {
			returnString = System.getProperty("java.home") + "/bin/java";
		}
		return returnString;
	}
	
	private String constructClasspath() {
		String classpath = "";
		ArrayList<File> libs = list(new File("game/libs/"));
		String separator = System.getProperty("path.separator");
		for(File lib: libs) {
			classpath += lib.getAbsolutePath() + separator;
		}
		classpath += new File("game/minecraft.jar").getAbsolutePath();
		return classpath;
	}
	
	private ArrayList<File> list(File folder) {
		ArrayList<File> files = new ArrayList<File>();
		if(!folder.isDirectory()) {
			return files;
		}
		File[] folderFiles = folder.listFiles();
		if(folderFiles != null) {
			for(File f: folderFiles) {
				if(f.isDirectory()) {
					files.addAll(list(f));
				}
				else {
					files.add(f);
				}
			}
		}
		return files;
	}
	
	private ArrayList<String> getLauncherArgs(String accessToken, String uuid) {
		ArrayList<String> args = new ArrayList<String>();
		args.add("--username=" + (String)comboBox.getSelectedItem());
		args.add("--accessToken");
		args.add(accessToken);
		args.add("--version");
		args.add("1.7.10");
		args.add("--gameDir");
		args.add(new File("game/").getAbsolutePath());
		args.add("--assetsDir");
		File assetsDir = new File("game/assets/");
		args.add(assetsDir.getAbsolutePath() + "/virtual/legacy/");
		args.add("--userProperties");
		args.add("{}");
		args.add("--uuid");
		args.add(uuid);
		args.add("--userType");
		args.add("legacy");
		return args;
	}
}