package fr.mineusb.launcher.auth;

public class AuthUserInfos {
	
	private String game;
	private String username;
	private String password;
	private String clientToken;
	private int version;
	
	public AuthUserInfos(String username, String password, String clientToken) {
		this.game = "Minecraft";
		this.username = username;
		this.password = password;
		this.clientToken = clientToken;
		this.version= 1;
	}

}
