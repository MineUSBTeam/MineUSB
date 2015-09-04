package fr.mineusb;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import fr.mineusb.system.launchers.Launcher;
import fr.mineusb.utils.FileUtils;
import fr.w67clement.core.system.OS;
import fr.w67clement.core.ui.dialogs.DownloadDialog;
import fr.w67clement.core.utils.Utils;

public class LauncherAddedByUser extends Launcher {
	
	private String ImageURL;
	private String name;
	
	public LauncherAddedByUser(File f) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(f));
		name = f.getName().replaceAll(".launcher", "");
		String URL = null;
		ImageURL = null;
		String descrip = null;
		String line = null;
		while((line = bf.readLine()) != null) {
			if(line.contains("URL")) {
				URL = line.replaceAll("URL=", "");
			}
			else if(line.contains("Image")) {
				ImageURL = line.replaceAll("Image=", "");
			}
			else if(line.contains("Description")) {
				descrip = line.replaceAll("Description=", "");
			}
		}
		this.setName(name);
		this.setDescription(descrip);
		this.setImageIconPath(ImageURL);
		// OS compatibility
		List<OS> osCompatibility = new ArrayList<OS>();
		osCompatibility.add(OS.WINDOWS);
		osCompatibility.add(OS.MAC_OSX);
		this.setOSCompatibilityList(osCompatibility);
		this.setDownloadURL(URL);
		this.setFile(new File("bin/" + name + ".jar"));
		bf.close();
	}

	@Override
	public ImageIcon getImageIcon() {
		try {
			if (Utils
					.isOnline(new URL(ImageURL))) {
				// return new ImageIcon(
				// Toolkit.getDefaultToolkit()
				// .getImage(
				// new URL(
				// "http://www.skyost.eu/skyolauncher/res/skyolauncher.png")));
				try {
					return new ImageIcon(
							ImageIO.read(
									new URL(
											ImageURL)
											.openStream()).getScaledInstance(
									300, 75, 16));
				} catch (IOException e) {
					MineUSB.getConsole()
							.warning(
									"MineUSB can't load logo, you running in offline mode?");
					return super.getImageIcon();
				}
			} else {
				MineUSB.getConsole()
						.warning(
								"MineUSB can't load logo, you running in offline mode?");
				return super.getImageIcon();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return super.getImageIcon();
	}

	public boolean runLauncher() {
		boolean runSuccess = true;
		DownloadDialog dialog = new DownloadDialog("Running launcher...",
				"Running Launcher...",
				Toolkit.getDefaultToolkit().getImage(
						MineUSB.class
								.getResource(MineUSBConstants.FAVICON_PATH)));
		dialog.setVisible(true);
		dialog.getProgressBar().setIndeterminate(true);

		if (!this.getFile().exists()) {
			dialog.setTitle("Downloading launcher...");
			dialog.setText("Downloading the launcher, please wait...");
			runSuccess = Utils.download(this.getDownloadURL_inString(),
					this.getFile(), dialog);
			dialog.setTitle("Running launcher...");
			dialog.setText("Running Launcher...");
			dialog.getProgressBar().setIndeterminate(true);
		}

		if (runSuccess) {
			File workingDirectory = new File(MineUSB.getDataFolder(),
					".minecraft/");
			if (!workingDirectory.exists())
				workingDirectory.mkdirs();
			if (OS.getCurrentPlatform() == OS.WINDOWS) {
				File scriptFile = new File(name + ".bat");
				if (!scriptFile.exists()) {
					FileUtils.generateRunJavaLauncherScriptWindows(scriptFile,
							"Launching Launcher", this.getFile());
				}
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec("cmd /c start " + name + ".bat");
				} catch (IOException e) {
					runSuccess = false;
				}
			} else if (OS.getCurrentPlatform() == OS.MAC_OSX) {
				File scriptFile = new File(name + "_mac.sh");
				if (!scriptFile.exists()) {
					FileUtils.generateRunLauncherScriptMac(scriptFile,
							"Launching Launcher", this.getFile());
				}
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec(new String[] { "sh", scriptFile.getPath() });
				} catch (IOException e) {
					runSuccess = false;
				}
			}
		}

		dialog.dispose();
		return runSuccess;
	}

}
