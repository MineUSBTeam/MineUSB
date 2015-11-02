package fr.mineusb.launcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class ProcessLauncher {
	
	private JComboBox<String> comboBox;
	private File gameDir;
	
	public ProcessLauncher(JComboBox<String> comboBox) {
		this.comboBox = comboBox;
		gameDir = new File("game/");
		if(!gameDir.exists()) {
			gameDir.mkdir();
		}
	}
	
	public Process launch(String accessToken, String uuid, JTextArea area) throws IOException {
		ProcessBuilder pb = new ProcessBuilder();
		ArrayList<String> commands = new ArrayList<String>();
		commands.add(getJAvaPath());
		commands.add("-Djava.library.path=" + new File(gameDir, "natives/").getAbsolutePath());
		commands.add("-cp");
		commands.add(constructClasspath());
		commands.add("net.minecraft.client.main.Main");
		commands.addAll(getLauncherArgs(accessToken, uuid));
		pb.directory(gameDir);
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
		ArrayList<File> libs = list(new File(gameDir, "libs/"));
		String separator = System.getProperty("path.separator");
		for(File lib: libs) {
			classpath += lib.getAbsolutePath() + separator;
		}
		classpath += new File(gameDir, "minecraft.jar").getAbsolutePath();
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
		args.add(gameDir.getAbsolutePath());
		args.add("--assetsDir");
		File assetsDir = new File(gameDir, "assets/");
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
