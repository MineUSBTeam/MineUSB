package fr.mineusb.ui.panels.launcher;

import fr.mineusb.system.launcher.MineLauncher;
import fr.mineusb.utils.TexturedPanel;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

public class LauncherPanel extends JPanel
{

    private static LauncherPanel instance;
    private static MainLauncherPanel mainLauncherPanel;
    private static ConnectPanel connectPanel;
    private CardLayout layout;

    public LauncherPanel()
    {
        instance = this;
        layout = new CardLayout();
        this.setLayout(layout);
        TexturedPanel connectPanel = new TexturedPanel("/fr/mineusb/res/dirt.png");
        connectPanel.setLayout(new GridBagLayout());
        connectPanel.add(getConnectPanel());
        this.add(getMainLauncherPanel(), "MAIN");
        this.add(connectPanel, "CONNECT");
        layout.show(this, "MAIN");
        if (!MineLauncher.initialize()) {
            changePanel("CONNECT");
        }
        ConnectPanel.setMineLauncher(MineLauncher.getInstance());
    }

    public static MainLauncherPanel getMainLauncherPanel()
    {
        if (mainLauncherPanel == null)
        {
            mainLauncherPanel = new MainLauncherPanel();
        }
        return mainLauncherPanel;
    }

    public static ConnectPanel getConnectPanel()
    {
        if (connectPanel == null) {
            connectPanel = new ConnectPanel();
        }
        return connectPanel;
    }

    public static boolean changePanel(String panel) {
        switch (panel) {
            case "MAIN":
                instance.layout.show(instance, panel);
                break;
            case "CONNECT":
                instance.layout.show(instance, panel);
                break;
            default:
                return false;
        }
        return true;
    }
}
