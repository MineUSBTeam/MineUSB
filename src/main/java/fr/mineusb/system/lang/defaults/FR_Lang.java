package fr.mineusb.system.lang.defaults;

import fr.mineusb.system.lang.Lang;

public class FR_Lang implements Lang {

	public String getName() {
		return "Français";
	}
	
	public String getId() {
		return "FRENCH";
	}

	public String getDescriptionLabel() {
		return "Utilisez Minecraft sur votre clef USB!";
	}

	public String getPlayButton() {
		return "Jouer";
	}

	public String getOptionsButton() {
		return "Options";
	}

	public String getQuitButton() {
		return "Quitter";
	}

	public String getAuthorsLabel() {
		return "Auteur: <author> <br />Contributeurs: <contributors>";
	}

	public String getWebsitesOnline() {
		return "Sites en lignes:";
	}

	public String getViewLicenseButton() {
		return "Voir la licence";
	}

	public String getChangeLangLabel() {
		return "Changer de langue:";
	}

	public String getReloadButton() {
		return "Recharger la config";
	}

	public String getSkinsServerLabel() {
		return "Serveur de skins";
	}
	
	public String getMultiplayerSessionLabel() {
		return "Session multijoueur";
	}
	
	public String getLoadingText() {
		return "Chargement...";
	}
}
