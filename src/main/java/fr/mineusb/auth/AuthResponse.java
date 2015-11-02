package fr.mineusb.auth;

public class AuthResponse {
	
	private String accessToken;
	private String clientToken;
	private AuthProfile selectedProfile;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getClientToken() {
		return clientToken;
	}
	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
	}
	public AuthProfile getSelectedProfile() {
		return selectedProfile;
	}
	public void setSelectedProfile(AuthProfile selectedProfile) {
		this.selectedProfile = selectedProfile;
	}
}
