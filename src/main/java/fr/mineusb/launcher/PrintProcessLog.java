package fr.mineusb.launcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JTextArea;

public class PrintProcessLog implements Runnable {
	
	private Process p;
	private JTextArea area;

	public PrintProcessLog(Process p, JTextArea area) {
		this.p = p;
		this.area = area;
	}

	@Override
	public void run() {
		BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";
		try {
			while((line = bf.readLine()) != null) {
				area.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
