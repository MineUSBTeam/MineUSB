package com.aol.mineusb.system.launchers.defaults;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.aol.mineusb.MineUSB;
import com.aol.mineusb.system.launchers.Launcher;
import com.aol.mineusb.utils.FileUtils;
import com.aol.w67clement.mineapi.enums.OSType;
import com.aol.w67clement.mineapi.frame.dialogs.DownloadDialog;
import com.aol.w67clement.mineapi.utils.Utils;

public class DefaultLauncher extends Launcher {

	public DefaultLauncher() {
		this.setName("Minecraft");
		this.setDescription("Default launcher, make by Mojang.");
		// OS compatibility
		List<OSType> osCompatibility = new ArrayList<OSType>();
		osCompatibility.add(OSType.WINDOWS);
		osCompatibility.add(OSType.MAC_OSX);
		osCompatibility.add(OSType.LINUX);
		this.setOSCompatibilityList(osCompatibility);
		if (OSType.getCurrentPlatform() == OSType.WINDOWS) {
			// this.setDownloadURL("https://launcher.mojang.com/download/Minecraft.exe");
			this.setDownloadURL("https://s3.amazonaws.com/Minecraft.Download/launcher/Minecraft.jar");
			this.setFile(new File("bin/Minecraft.jar"));
		} else if (OSType.getCurrentPlatform() == OSType.MAC_OSX) {
			this.setDownloadURL("https://s3.amazonaws.com/Minecraft.Download/launcher/Minecraft.jar");
			this.setFile(new File("bin/Minecraft.jar"));
		} else if (OSType.getCurrentPlatform() == OSType.LINUX) {
			this.setDownloadURL("https://s3.amazonaws.com/Minecraft.Download/launcher/Minecraft.jar");
			this.setFile(new File("bin/Minecraft.jar"));
		}
		this.setImageIconPath("/com/aol/mineusb/res/launchers/default.png");
	}

	@SuppressWarnings("resource")
	@Override
	public boolean runLauncher() {
		boolean runSuccess = true;
		DownloadDialog dialog = new DownloadDialog(
				"Running launcher...",
				"Running the default launcher...",
				Toolkit.getDefaultToolkit()
						.getImage(
								MineUSB.class
										.getResource("/com/aol/mineusb/res/favicon.png")));
		dialog.setVisible(true);
		dialog.getProgressBar().setIndeterminate(true);

		if (!this.getFile().exists()) {
			dialog.setTitle("Downloading launcher...");
			dialog.setText("Downloading the launcher, please wait...");
			runSuccess = Utils.download(this.getDownloadURL_inString(),
					this.getFile(), dialog);
			dialog.setTitle("Running launcher...");
			dialog.setText("Running the default launcher");
			dialog.getProgressBar().setIndeterminate(true);
		}

		if (runSuccess) {
			File workingDirectory = new File(MineUSB.getDataFolder(),
					".minecraft/");
			if (!workingDirectory.exists())
				workingDirectory.mkdirs();
			if (OSType.getCurrentPlatform() == OSType.WINDOWS) {
				File scriptFile = new File("Minecraft.bat");
				if (!scriptFile.exists()) {
					FileUtils.generateRunLauncherScriptWindows(scriptFile,
							"Launching Minecraft Bootstrap", this.getFile());
				}
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec("cmd /c start Minecraft.bat");
				} catch (IOException e) {
					runSuccess = false;
				}
			} else if (OSType.getCurrentPlatform() == OSType.MAC_OSX) {
				dialog.setText("Using ClassLoader... Loading Minecraft Bootstrap, please wait...");
				MineUSB.getConsole().info(
						"Loading Minecraft Bootstrap with ClassLoader...");
				try {
					Class<?> mcBootstrap = new URLClassLoader(new URL[] { this
							.getFile().toURI().toURL() })
							.loadClass("net.minecraft.bootstrap.Bootstrap");
					Constructor<?> constructor = mcBootstrap
							.getConstructor(new Class<?>[] {});
					Method mainMethod = mcBootstrap.getMethod("main",
							new Class<?>[] { String[].class });
					dialog.dispose();
					MineUSB.shutdown();
					mainMethod
							.invoke(constructor.newInstance(new Object[] {}),
									new Object[] { new String[] { "--workdir data/" } });
				} catch (ClassNotFoundException e) {
					runSuccess = false;
				} catch (MalformedURLException e) {
					e.printStackTrace();
					runSuccess = false;
				} catch (NoSuchMethodException e) {
					runSuccess = false;
				} catch (SecurityException e) {
					e.printStackTrace();
					runSuccess = false;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					runSuccess = false;
				} catch (IllegalArgumentException e) {
					runSuccess = false;
				} catch (InvocationTargetException e) {
					runSuccess = false;
				} catch (InstantiationException e) {
					runSuccess = false;
				}
			} else if (OSType.getCurrentPlatform() == OSType.LINUX) {
				dialog.setText("Using ClassLoader... Loading Minecraft Bootstrap, please wait...");
				MineUSB.getConsole().info(
						"Loading Minecraft Bootstrap with ClassLoader...");
				try {
					Class<?> mcBootstrap = new URLClassLoader(new URL[] { this
							.getFile().toURI().toURL() })
							.loadClass("net.minecraft.bootstrap.Bootstrap");
					Constructor<?> constructor = mcBootstrap
							.getConstructor(new Class<?>[] {});
					Method mainMethod = mcBootstrap.getMethod("main",
							new Class<?>[] { String[].class });
					dialog.dispose();
					MineUSB.shutdown();
					mainMethod
							.invoke(constructor.newInstance(new Object[] {}),
									new Object[] { new String[] { "--workdir data/" } });
				} catch (ClassNotFoundException e) {
					runSuccess = false;
				} catch (MalformedURLException e) {
					e.printStackTrace();
					runSuccess = false;
				} catch (NoSuchMethodException e) {
					runSuccess = false;
				} catch (SecurityException e) {
					e.printStackTrace();
					runSuccess = false;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					runSuccess = false;
				} catch (IllegalArgumentException e) {
					runSuccess = false;
				} catch (InvocationTargetException e) {
					runSuccess = false;
				} catch (InstantiationException e) {
					runSuccess = false;
				}
			}
		}

		dialog.dispose();
		return runSuccess;
	}
}
