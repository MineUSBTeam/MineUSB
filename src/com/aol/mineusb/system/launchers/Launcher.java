package com.aol.mineusb.system.launchers;

import java.awt.Toolkit;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;

import com.aol.mineusb.MineUSB;
import com.aol.w67clement.mineapi.enums.OSType;

/**
 *  A launcher class
 * @author 67clement
 *
 */
public class Launcher {

	private String name;
	private String description;
	private String download_url;
	private List<OSType> osCompatibility;
	private String imageIconPath;
	private File file;
	
	/**
	 *  Gets the name of the launcher.
	 * @return Name
	 */
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 *  Gets the description of the launcher.
	 * @return Description.
	 */
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setDescription(List<String> description) {
		//A local description in a String
		String newDescription = "";
		//Convert List<String> in String
		for (String local : description) {
			newDescription = newDescription + local + "\n";
		}
		//Set
		this.description = newDescription;
	}
	/**
	 *  Gets the url to download the launcher.
	 * @return An URL
	 */
	public String getDownloadURL_inString() {
		return this.download_url;
	}
	
	/**
	 *  Gets the url to download the launcher.
	 * @return Return a simple URL Object.
	 * @throws MalformedURLException Prevent malformed URL.
	 */
	public URL getDownloadURL() throws MalformedURLException {
		return new URL(this.download_url);
	}
	
	/**
	 *  Gets the uri to download the launcher.
	 * @return Return a simple URI Object.
	 * @throws MalformedURLException Prevent malformed URL
	 * @throws URISyntaxException Prevent URI syntax error.
	 */
	public URI getDownloadURI() throws MalformedURLException, URISyntaxException {
		return this.getDownloadURL().toURI();
	}
	
	public void setDownloadURL(String url) {
		this.download_url = url;
	}
	
	public void setDownloadURL(URL url) {
		this.setDownloadURL(url.toString());
	}
	
	public void setDownloadURI(URI uri) throws MalformedURLException {
		this.setDownloadURL(uri.toURL());
	}
	
	public List<OSType> getOSCompatibilityList() {
		return this.osCompatibility;
	}
	
	public void setOSCompatibilityList(List<OSType> osCompatibility) {
		this.osCompatibility = osCompatibility;
	}
	
	public String getImageIconPath() {
		return this.imageIconPath;
	}
	
	public void setImageIconPath(String path) {
		this.imageIconPath = path;
	}
	
	public ImageIcon getImageIcon() {
		if (this.imageIconPath == null) {
			return  new ImageIcon(Toolkit.getDefaultToolkit().getImage(
					MineUSB.class.getResource("/com/aol/mineusb/res/launchers/default.png")));
		} else {
			return new ImageIcon(Toolkit.getDefaultToolkit().getImage(
					MineUSB.class.getResource(this.imageIconPath)));
		}
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return this.file;
	}
	
	public boolean runLauncher() {
		return false;
	}
}
