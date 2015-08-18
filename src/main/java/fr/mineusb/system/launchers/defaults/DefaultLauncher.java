package fr.mineusb.system.launchers.defaults;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.mineusb.system.launchers.Launcher;
import fr.mineusb.utils.FileUtils;
import fr.w67clement.core.system.OS;
import fr.w67clement.core.ui.dialogs.DownloadDialog;
import fr.w67clement.core.utils.Utils;

public class DefaultLauncher extends Launcher {

	public DefaultLauncher() {
		this.setName("Minecraft");
		this.setDescription("Default launcher, make by Mojang.");
		// OS compatibility
		List<OS> osCompatibility = new ArrayList<OS>();
		osCompatibility.add(OS.WINDOWS);
		osCompatibility.add(OS.MAC_OSX);
		osCompatibility.add(OS.LINUX);
		this.setOSCompatibilityList(osCompatibility);
		if (OS.getCurrentPlatform() == OS.WINDOWS) {
			this.setDownloadURL("https://launcher.mojang.com/download/Minecraft.exe");
			this.setFile(new File("bin/Minecraft.exe"));
		} else if (OS.getCurrentPlatform() == OS.MAC_OSX) {
			this.setDownloadURL("https://s3.amazonaws.com/Minecraft.Download/launcher/Minecraft.jar");
			this.setFile(new File("bin/Minecraft.jar"));
		} else if (OS.getCurrentPlatform() == OS.LINUX) {
			this.setDownloadURL("https://s3.amazonaws.com/Minecraft.Download/launcher/Minecraft.jar");
			this.setFile(new File("bin/Minecraft.jar"));
		} else if (OS.getCurrentPlatform() == OS.SOLARIS) {
			this.setDownloadURL("https://s3.amazonaws.com/Minecraft.Download/launcher/Minecraft.jar");
			this.setFile(new File("bin/Minecraft.jar"));
		}
		this.setImageIconPath(MineUSBConstants.DEFAULT_LAUNCHER_ICON_PATH);
	}

	@SuppressWarnings("resource")
	@Override
	public boolean runLauncher() {
		boolean runSuccess = true;
		DownloadDialog dialog = new DownloadDialog("Running launcher...",
				"Running the default launcher...",
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
			dialog.setText("Running the default launcher");
			dialog.getProgressBar().setIndeterminate(true);
		}

		if (runSuccess) {
			File workingDirectory = new File(MineUSB.getDataFolder(),
					".minecraft/");
			if (!workingDirectory.exists())
				workingDirectory.mkdirs();
			if (OS.getCurrentPlatform() == OS.WINDOWS) {
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
			} else if (OS.getCurrentPlatform() == OS.MAC_OSX) {
				dialog.setText("Using ClassLoader... Loading Minecraft Bootstrap, please wait...");
				MineUSB.getConsole().info(
						"Loading Minecraft Bootstrap with ClassLoader...");
				try {
					Class<?> mcBootstrap = new URLClassLoader(new URL[] { this
							.getFile().toURI().toURL() })
							.loadClass("net.minecraft.bootstrap.Bootstrap");
					Constructor<?> constructor = mcBootstrap
							.getConstructor(new Class<?>[] { File.class,
									Proxy.class, PasswordAuthentication.class,
									String[].class });
					Proxy proxy = Proxy.NO_PROXY;
					Method mainMethod = mcBootstrap.getMethod("execute",
							new Class<?>[] { Boolean.class });
					dialog.dispose();
					MineUSB.shutdown();
					mainMethod.invoke(constructor.newInstance(workingDirectory,
							proxy, null, new String[] {}), false);
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
			} else if (OS.getCurrentPlatform() == OS.LINUX) {
				dialog.setText("Using ClassLoader... Loading Minecraft Bootstrap, please wait...");
				MineUSB.getConsole().info(
						"Loading Minecraft Bootstrap with ClassLoader...");
				try {
					Class<?> mcBootstrap = new URLClassLoader(new URL[] { this
							.getFile().toURI().toURL() })
							.loadClass("net.minecraft.bootstrap.Bootstrap");
					Constructor<?> constructor = mcBootstrap
							.getConstructor(new Class<?>[] { File.class,
									Proxy.class, PasswordAuthentication.class,
									String[].class });
					Proxy proxy = Proxy.NO_PROXY;
					Method mainMethod = mcBootstrap.getMethod("execute",
							new Class<?>[] { Boolean.class });
					dialog.dispose();
					MineUSB.shutdown();
					mainMethod.invoke(constructor.newInstance(workingDirectory,
							proxy, null, new String[] {}), false);
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
			} else if (OS.getCurrentPlatform() == OS.SOLARIS) {
				dialog.setText("Using ClassLoader... Loading Minecraft Bootstrap, please wait...");
				MineUSB.getConsole().info(
						"Loading Minecraft Bootstrap with ClassLoader...");
				try {
					Class<?> mcBootstrap = new URLClassLoader(new URL[] { this
							.getFile().toURI().toURL() })
							.loadClass("net.minecraft.bootstrap.Bootstrap");
					Constructor<?> constructor = mcBootstrap
							.getConstructor(new Class<?>[] { File.class,
									Proxy.class, PasswordAuthentication.class,
									String[].class });
					Proxy proxy = Proxy.NO_PROXY;
					Method mainMethod = mcBootstrap.getMethod("execute",
							new Class<?>[] { Boolean.class });
					dialog.dispose();
					MineUSB.shutdown();
					mainMethod.invoke(constructor.newInstance(workingDirectory,
							proxy, null, new String[] {}), false);
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
					e.printStackTrace();
					runSuccess = false;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					runSuccess = false;
				} catch (InstantiationException e) {
					e.printStackTrace();
					runSuccess = false;
				}
			}
		}

		dialog.dispose();
		return runSuccess;
	}
}
