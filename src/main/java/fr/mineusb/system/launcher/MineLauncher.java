package fr.mineusb.system.launcher;

import fr.mineusb.MineUSB;
import fr.mineusb.system.launcher.auth.AuthResponse;
import fr.mineusb.system.launcher.auth.Authentication;
import javax.xml.bind.DatatypeConverter;

public class MineLauncher
{
    private static MineLauncher instance;
    private String username;
    private String password;
    private AuthResponse authResponse;

    private MineLauncher()
    {
    }

    public static boolean initialize()
    {
        if (instance != null)
        {
            throw new UnsupportedOperationException("MineLauncher was already initialized.");
        }
        instance = new MineLauncher();
        String username = MineUSB.getConfig().getUsername();
        String pwd = new String(DatatypeConverter.parseBase64Binary(MineUSB.getConfig().getPassword()));
        if ((!username.equals("404@null.com")) && (!pwd.equals("non-encoded-response")))
        {
            instance.username = username;
            instance.password = pwd;
            AuthResponseAdvanced response = Authentication.getInstance().authenticate(username, pwd);
            if (response.responseCode != 200)
            {
                return false;
            }
        }
        else
        {
            return false;
        }
        return true;
    }

    public static MineLauncher getInstance()
    {
        return instance;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public AuthResponse getAuthResponse()
    {
        return authResponse;
    }

    public AuthResponseAdvanced authenticate()
    {
        AuthResponseAdvanced response = Authentication.getInstance().authenticate(username, password);
        if (response.isAuthenticate())
        {
            if (response.authResponse != null)
            {
                authResponse = response.authResponse;
            }
            MineUSB.getConfig().setUserAndPWD(username, DatatypeConverter.printBase64Binary(password.getBytes()));
        }
        return response;
    }

    public static class AuthResponseAdvanced
    {

        private AuthResponse authResponse;
        private String errorResponse;
        private boolean isAuthenticate;
        private int responseCode;
        private Exception exception;

        public AuthResponseAdvanced(AuthResponse authResponse, String errorResponse, boolean isAuthenticate, int responseCode, Exception exception)
        {
            this.authResponse = authResponse;
            this.errorResponse = errorResponse;
            this.isAuthenticate = isAuthenticate;
            this.responseCode = responseCode;
            this.exception = exception;
        }

        public AuthResponse getAuthResponse()
        {
            return authResponse;
        }

        public String getErrorResponse()
        {
            return errorResponse;
        }

        public boolean isAuthenticate()
        {
            return isAuthenticate;
        }

        public int getResponseCode()
        {
            return responseCode;
        }

        public Exception getException()
        {
            return exception;
        }
    }
}
