package fr.mineusb.system.lang;

public interface Lang
{

	/**
	 * Gets the name of the lang.
	 * 
	 * @return The name of the lang.
	 */
	String getName();

	String getId();

	String getDescriptionLabel();

	String getSelectedLauncherLabel();

	String getPlayButton();

	String getOptionsButton();

	String getQuitButton();

	String getAuthorsLabel();

	String getWebsitesOnline();

	String getViewLicenseButton();

	String getChangeLangLabel();

	String getReloadButton();

	String getSkinsServerLabel();

	String getMultiplayerSessionLabel();

	String getLoadingText();

	String getSpecialThanksToText();

	String getCreditsText();

	String getW67clementDescription();

	String getXoliderDescription();

	String getMentor6561Description();

	String getTheShark34Description();

	String getSupportersText();

	String getTechnologiesText();

	// MineUSB Integrated launcher section

	String getMineUSBLauncherTitleText();

    String getConnectButton();

    // MineUSB Integrated launcher errors section

    String getUsernameAndPasswordInvalid();
}
