package fr.mineusb.system.lang.defaults;

import fr.mineusb.system.lang.Lang;

public class EN_Lang implements Lang {

	public String getName() {
		return "English";
	}

	public String getId() {
		return "ENGLISH";
	}

	public String getDescriptionLabel() {
		return "Play Minecraft on your USB key!";
	}

	public String getPlayButton() {
		return "Play";
	}

	public String getOptionsButton() {
		return "Options";
	}

	public String getQuitButton() {
		return "Quit";
	}

	public String getAuthorsLabel() {
		return "Author: <author> <br />Contributor: <contributors>";
	}

	public String getWebsitesOnline() {
		return "Websites online:";
	}

	public String getViewLicenseButton() {
		return "View license";
	}

	public String getChangeLangLabel() {
		return "Change the lang:";
	}

	public String getReloadButton() {
		return "Reload config";
	}

	public String getSkinsServerLabel() {
		return "Skins server";
	}
	
	public String getMultiplayerSessionLabel() {
		return "Multiplayer session";
	}
	
	public String getLoadingText() {
		return "Loading...";
	}

	@Override
	public String getSpecialThanksToText() {
		return "Special thanks to: ";
	}

	@Override
	public String getCreditsText() {
		return "Credits";
	}

	@Override
	public String getW67clementDescription() {
		return "Author of MineUSB";
	}

	@Override
	public String getMentor6561Description() {
		return "Author of MineUSB's logo and icon";
	}

	@Override
	public String getTheShark34Description() {
		return "He help me for <font color=\"#00CC00\">Gradle</font>";
	}

	@Override
	public String getSupportersText() {
		return "And all supporters of MineUSB";
	}

	@Override
	public String getSelectedLauncherLabel() {
		return "Selected Launcher: ";
	}
}
