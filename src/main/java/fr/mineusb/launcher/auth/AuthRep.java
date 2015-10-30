package fr.mineusb.launcher.auth;

public class AuthRep {
	
	public String accessToken;
	public String ClientToken;
	public AuthProfile[] profiles;
	public AuthProfile selectedProfile;
	
	public AuthRep(String accessToken, String clientToken, AuthProfile[] profiles, AuthProfile selectedProfile) {
		this.accessToken = accessToken;
		this.ClientToken = clientToken;
		this.profiles = profiles;
		this.selectedProfile = selectedProfile;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getClientToken() {
		return ClientToken;
	}

	public void setClientToken(String clientToken) {
		ClientToken = clientToken;
	}

	public AuthProfile[] getProfiles() {
		return profiles;
	}

	public void setProfiles(AuthProfile[] profiles) {
		this.profiles = profiles;
	}

	public AuthProfile getSelectedProfile() {
		return selectedProfile;
	}

	public void setSelectedProfile(AuthProfile selectedProfile) {
		this.selectedProfile = selectedProfile;
	}
}
