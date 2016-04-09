package fr.mineusb.ui.panels.launcher;

import fr.mineusb.MineUSB;
import fr.mineusb.system.launcher.MineLauncher;
import fr.mineusb.system.launcher.MineLauncher.AuthResponseAdvanced;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Created by w67clement on 09/04/2016.
 * <p>
 * Class of project: MineUSB
 */
public class ConnectPanel extends JPanel
{
    private static MineLauncher mineLauncher = MineLauncher.getInstance();
    private static JLabel mcLogo;
    private static JPanel subPanel;
    private static JTextField usernameField;
    private static JLabel whatIsUsernameField;
    private static JPasswordField passwordField;
    private static JLabel forgetPassword;
    private static JButton loginButton;

    public ConnectPanel() {
        this.setLayout(new BoxLayout(this, 1));
        this.setBorder(new EmptyBorder(5, 15, 5, 15));
        // Minecraft logo
        JPanel imagePanel = new JPanel();
        imagePanel.add(getMcLogo());
        this.add(imagePanel);
        this.add(Box.createVerticalStrut(10));
        //
        this.add(getSubPanel());
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonPanel.add(getLoginButton());
        this.add(buttonPanel);
    }

    public static void setMineLauncher(MineLauncher launcher) {
        mineLauncher = launcher;
    }

    public static JLabel getMcLogo()
    {
        if (mcLogo == null) {
            mcLogo = new JLabel();
            mcLogo.setPreferredSize(new Dimension(275, 45));
            mcLogo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MineUSB.class.getResource("/fr/mineusb/res/launchers/default.png"))));
        }
        return mcLogo;
    }

    public static JPanel getSubPanel() {
        if (subPanel == null) {
            subPanel = new JPanel();
            subPanel.setLayout(new BoxLayout(subPanel, 1));
            subPanel.add(getUsernameField());
            //subPanel.add(getWhatIsUsernameField());
            subPanel.add(getPasswordField());
            //subPanel.add(getForgetPassword());
        }
        return subPanel;
    }

    public static JTextField getUsernameField()
    {
        if (usernameField == null) {
            usernameField = new JTextField();
            usernameField.addActionListener(e -> {
                if ((!getUsernameField().getText().isEmpty()) || (!(new String(getPasswordField().getPassword()).isEmpty()))) {
                    mineLauncher.setUsername(getUsernameField().getText());
                    mineLauncher.setPassword(new String(getPasswordField().getPassword()));
                    AuthResponseAdvanced authResponse = mineLauncher.authenticate();
                    if (!authResponse.isAuthenticate()) {
                        Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
                        getUsernameField().setBorder(border);
                        getPasswordField().setBorder(border);
                        if (authResponse.getException() != null) {
                            // Do something here.
                        }
                    } else {
                        LauncherPanel.changePanel("MAIN");
                    }
                } else {
                    Border border = BorderFactory.createLineBorder(Color.RED, 1);
                    getUsernameField().setBorder(border);
                    getPasswordField().setBorder(border);
                }
            });
        }
        return usernameField;
    }

    public static JLabel getWhatIsUsernameField()
    {
        return whatIsUsernameField;
    }

    public static JPasswordField getPasswordField()
    {
        if (passwordField == null) {
            passwordField = new JPasswordField();
            passwordField.addActionListener(e -> {
                if ((!getUsernameField().getText().isEmpty()) || (!(new String(getPasswordField().getPassword()).isEmpty()))) {
                    mineLauncher.setUsername(getUsernameField().getText());
                    mineLauncher.setPassword(new String(getPasswordField().getPassword()));
                    AuthResponseAdvanced authResponse = mineLauncher.authenticate();
                    if (!authResponse.isAuthenticate()) {
                        Border border = BorderFactory.createLineBorder(Color.RED, 1);
                        getUsernameField().setBorder(border);
                        getPasswordField().setBorder(border);
                        if (authResponse.getException() != null) {
                            // Do something here.
                        }
                    } else {
                        LauncherPanel.changePanel("MAIN");
                    }
                } else {
                    Border border = BorderFactory.createLineBorder(Color.RED, 1);
                    getUsernameField().setBorder(border);
                    getPasswordField().setBorder(border);
                }
            });
        }
        return passwordField;
    }

    public static JLabel getForgetPassword()
    {
        return forgetPassword;
    }

    public static JButton getLoginButton()
    {
        if (loginButton == null) {
            loginButton = new JButton();
            loginButton.setText(MineUSB.getLangUsed().getConnectButton());
            loginButton.addActionListener(e -> {
                if ((!getUsernameField().getText().isEmpty()) || (!(new String(getPasswordField().getPassword()).isEmpty()))) {
                    MineLauncher.getInstance().setUsername(getUsernameField().getText());
                    mineLauncher.setPassword(new String(getPasswordField().getPassword()));
                    AuthResponseAdvanced authResponse = mineLauncher.authenticate();
                    if (!authResponse.isAuthenticate()) {
                        Border border = BorderFactory.createLineBorder(Color.RED, 1);
                        getUsernameField().setBorder(border);
                        getPasswordField().setBorder(border);
                        if (authResponse.getException() != null) {
                            // Do something here.
                        }
                    } else {
                        LauncherPanel.changePanel("MAIN");
                    }
                } else {
                    Border border = BorderFactory.createLineBorder(Color.RED, 1);
                    getUsernameField().setBorder(border);
                    getPasswordField().setBorder(border);
                }
            });
        }
        return loginButton;
    }
}
