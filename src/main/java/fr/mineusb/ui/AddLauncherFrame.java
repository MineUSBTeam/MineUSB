package fr.mineusb.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JTextField;
import javax.swing.JButton;

import fr.mineusb.MineUSB;

public class AddLauncherFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	
	public AddLauncherFrame() {
		this.setSize(new Dimension(700, 150));
		this.setResizable(false);
		this.setTitle("Add the Launcher !");
		JPanel panel = new JPanel();
		this.setContentPane(panel);
		panel.setLayout(null);
		
		final JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setBounds(10, 11, 52, 20);
		panel.add(lblName);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(92, 11, 592, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		final JLabel lblNewLabel = new JLabel("URL:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 42, 46, 14);
		panel.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_1.setBounds(92, 39, 592, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(605, 97, 89, 23);
		
		JLabel lblImageUrl = new JLabel("Image URL");
		lblImageUrl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblImageUrl.setBounds(10, 67, 79, 21);
		panel.add(lblImageUrl);
		
		textField_2 = new JTextField();
		textField_2.setBounds(92, 69, 592, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		this.setVisible(true);
		
		JLabel lblNewLabel_1 = new JLabel("Description");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 99, 79, 14);
		panel.add(lblNewLabel_1);
		
		textField_3 = new JTextField();
		textField_3.setBounds(92, 99, 503, 20);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!new File("data/launchers").exists()) {
					new File("data/launchers").mkdirs();
				}
				String name = textField.getText();
				String URL = textField_1.getText();
				String ImageURL = textField_2.getText();
				File f = new File("data/launchers", name + ".launcher");
				if(!f.exists()) {
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(f));
						bw.write("");
						bw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					PrintWriter pr = null;
					try {
						pr = new PrintWriter(f);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pr.append("URL=" + URL + "\n");
					pr.append("Image=" + ImageURL + "\n");
					pr.append("Description=" + textField_3.getText() + "\n");
					pr.close();
					JOptionPane.showConfirmDialog(AddLauncherFrame.this, "Launcher added !", "Launcher Added", JOptionPane.INFORMATION_MESSAGE);
					MineUSB.loadLaunchers();
					AddLauncherFrame.this.setVisible(false);
				}
				else {
					JOptionPane.showConfirmDialog(AddLauncherFrame.this, "This Launcher already exist !", "Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		panel.add(btnAdd);
	}
}
