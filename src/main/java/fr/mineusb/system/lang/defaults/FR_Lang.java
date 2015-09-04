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
		return "Jouez à Minecraft sur votre clef USB!";
	}
	
	@Override
	public String getSelectedLauncherLabel() {
	return "Launcher sélectionné: ";
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
		return "Auteur: <author> <br />Contributeur: <contributors>";
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

	@Override
	public String getSpecialThanksToText() {
		return "Remerciement spécial à: ";
	}

	@Override
	public String getCreditsText() {
		return "Crédits";
	}

	@Override
	public String getW67clementDescription() {
		return "Auteur de MineUSB";
	}

	@Override
	public String getXoliderDescription() {
		return "Un d�veloppeur de MineUSB";
	}

	@Override
	public String getMentor6561Description() {
		return "Auteur du logo et de l'icône de MineUSB";
	}

	@Override
	public String getTheShark34Description() {
		return "Il m'a aidé pour <font color=\"#00CC00\">Gradle</font>";
	}

	@Override
	public String getSupportersText() {
		return "Et tous les supporteurs de MineUSB";
	}

	@Override
	public String getAddLauncherText() {
		// TODO Auto-generated method stub
		return "Ajouter un launcher";
	}
}
