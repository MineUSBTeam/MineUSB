package fr.mineusb;

import fr.mineusb.exceptions.LangNotFoundException;
import fr.mineusb.exceptions.LauncherNotFoundException;
import fr.mineusb.system.JsonConfig;
import fr.mineusb.system.lang.Lang;
import fr.mineusb.system.lang.LangManager;
import fr.mineusb.system.launchers.Launcher;
import fr.mineusb.system.launchers.LauncherManager;
import fr.mineusb.ui.LicenseFrame;
import fr.mineusb.ui.MineUSBFrame;
import fr.mineusb.ui.OptionsFrame;
import fr.mineusb.ui.panels.CreditsPanel;
import fr.mineusb.ui.panels.LaunchersPanel;
import fr.mineusb.ui.panels.MineUSBPanel;
import fr.mineusb.ui.panels.OptionsPanel;
import fr.mineusb.ui.panels.launcher.ConnectPanel;
import fr.mineusb.ui.panels.launcher.MainLauncherPanel;
import fr.mineusb.utils.LoggerOutputStream;
import fr.w67clement.core.system.MineSystem;
import fr.w67clement.core.system.OS;
import fr.w67clement.core.utils.Utils;
import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import javax.swing.UIManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

public class MineUSB
{

    public static final String LAUNCHER_VERSION = "0.1";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd-HH-mm-ss");
    private static final String version = "Beta 1.1.0";
    // Logger
    private static Logger console;
    // Configuration
    private static JsonConfig config;
    // Frame
    private static MineUSBFrame frame;
    private static OptionsFrame optionsFrame;
    private static LicenseFrame licenseFrame;
    // Computer's OS
    private static OS os;
    // Files
    private static File binFolder;
    private static File dataFolder;
    private static File mineusbFolder;
    private static File logsFolder;
    // Utils
    private static boolean java_design = false;

    public static void main(String[] args)
    {
        // Initialize MineUSBLogger
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        try
        {
            context.setConfigLocation(MineUSB.class.getResource("/log4j2.xml").toURI());
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        console = LogManager.getLogger("MineUSB");
        System.setOut(new PrintStream(new LoggerOutputStream(console, Level.INFO), true));
        System.setErr(new PrintStream(new LoggerOutputStream(console, Level.WARN), true));
        dataFolder = new File("data/");
        mineusbFolder = new File(dataFolder, "MineUSB/");
        logsFolder = new File(mineusbFolder, "logs/");

        if (!dataFolder.exists())
            dataFolder.mkdirs();

        if (!mineusbFolder.exists())
            mineusbFolder.mkdirs();

        if (!logsFolder.exists())
            logsFolder.mkdirs();

        // Starting MineUSB
        console.info("Starting MineUSB " + version);
        os = OS.getCurrentPlatform();
        console.info("OS used: " + MineSystem.getCurrentOS());

        System.setProperty("java.net.preferIPv4Stack", "true");

        for (String str : args)
        {
            if (str.equalsIgnoreCase("--java_design"))
            {
                java_design = true;
            }
        }

        System.out.println("Initializing of look and feel... ");
        // Initialize look and feel
        if (!java_design)
        {
            try
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                System.out.println("Done.");
            }
            catch (Throwable ignored)
            {
                try
                {
                    console.warn("Failed.");
                    console.warn("Your java failed to provide normal look and feel, trying the old fallback now.. ");
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    System.out.println("Done.");
                }
                catch (Throwable t)
                {
                    console.error("Failed.");
                    console.error("MineUSB has crashed");
                    console.error("Unexpected exception setting look and feel");
                    t.printStackTrace();
                    console.error("Force MineUSB to stop...");
                    return;
                }
            }

        }
        else
            console.info("Use Java look and feel.");
        // Files
        binFolder = new File("bin/");

        if (!binFolder.exists())
            binFolder.mkdirs();

        // Launcher manager
        console.info("Initializing launcher and lang system... ");
        new LauncherManager();
        new LangManager();
        console.info("Done.");

        // Configuration
        config = new JsonConfig(new File(mineusbFolder, "config.json"));
        try
        {
            LauncherManager.getLauncher(config.getLauncherType());
        }
        catch (LauncherNotFoundException e)
        {
            console.error("The launcher '" + config.getLauncherType() + "' was not exists! Reset the configuration...");
            config.setLauncherType("Minecraft");
        }

        // Initialize frame
        new Thread(() -> licenseFrame = new LicenseFrame()).start();
        frame = new MineUSBFrame();
        optionsFrame = new OptionsFrame();

        frame.setVisible(true);

        // Add shutdown thread
        Runtime.getRuntime().addShutdownHook(new Thread(() -> console.info("MineUSB has stopped!")));
    }

    public static boolean hasJavaDesign()
    {
        return java_design;
    }

    public static OptionsFrame getOptionsFrame()
    {
        return optionsFrame;
    }

    public static LicenseFrame getLicenseFrame()
    {
        return licenseFrame;
    }

    public static JsonConfig getConfig()
    {
        return config;
    }

    public static String getVersion()
    {
        return version;
    }

    public static Logger getConsole()
    {
        return console;
    }

    public static OS getOS()
    {
        return os;
    }

    public static File getBinFolder()
    {
        return binFolder;
    }

    public static File getDataFolder()
    {
        return dataFolder;
    }

    public static File getMineUSBFolder()
    {
        return mineusbFolder;
    }

    public static Lang getLangUsed()
    {
        try
        {
            return LangManager.getLang(config.getLang());
        }
        catch (LangNotFoundException e)
        {
            e.printStackTrace();
        }
        try
        {
            return LangManager.getLang("ENGLISH");
        }
        catch (LangNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Launcher getLauncherUsed()
    {
        try
        {
            return LauncherManager.getLauncher(config.getLauncherType());
        }
        catch (LauncherNotFoundException e)
        {
            e.printStackTrace();
        }
        try
        {
            return LauncherManager.getLauncher("Minecraft");
        }
        catch (LauncherNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void applyLang(final Lang lang)
    {
        // Description
        MineUSBPanel.getDescriptionLabel().setText(lang.getDescriptionLabel());
        // Selected launcher
        MineUSBPanel.getSelectedLauncherLabel().setText("<html>" + lang.getSelectedLauncherLabel() + "<b><font color=\"#00CC00\">" + MineUSB.getLauncherUsed().getName() + "</font></b></html>");
        // Play Button
        MineUSBPanel.getPlayButton().setText(lang.getPlayButton());
        // Options Button
        MineUSBPanel.getOptionsButton().setText(lang.getOptionsButton());
        // Quit Button
        MineUSBPanel.getQuitButton().setText(lang.getQuitButton());
        // Authors label
        MineUSBPanel.getAuthorsLabel().setText("<html>" + lang.getAuthorsLabel().replace("<author>", "<font color=\"#00CC00\">67clement</font>").replace("<contributors>", "<font color=\"#00CC00\">xolider</font>," + " <font color=\"#00CC00\">mentor6561</font>") + "</html>");
        // View License
        OptionsPanel.getViewLicenseButton().setText(lang.getViewLicenseButton());
        // Change lang label
        OptionsPanel.getChangLangLabel().setText("<html><br /><b>" + lang.getChangeLangLabel() + "</b></html>");
        OptionsPanel.getLangList().setSelectedItem(lang.getName());
        // Reload Button
        OptionsPanel.getReloadButton().setText(lang.getReloadButton());
        // Quit Button
        OptionsPanel.getQuitButton().setText(lang.getQuitButton());
        // Websites online label
        OptionsPanel.getWebsitesOnlineLabel().setText("<html><b>" + lang.getWebsitesOnline() + "</b></html>");
        // Integrated launcher
        MainLauncherPanel.getTitle().setText("<html>" + lang.getMineUSBLauncherTitleText() + "(v " + LAUNCHER_VERSION + ")<html>");
        ConnectPanel.getLoginButton().setText(lang.getConnectButton());
        // Credits
        CreditsPanel.getCreditsLabel().setText("<html><b>" + lang.getCreditsText() + "</b></html>");
        CreditsPanel.getW67clementLabel().setText("<html>- <b><font color=\"#00CC00\">w67clement</font></b>: " + lang.getW67clementDescription() + ".<html>");
        CreditsPanel.getXoliderLabel().setText("<html>- <b><font color=\"#00CC00\">xolider</font></b>: " + lang.getXoliderDescription() + ".<html>");
        CreditsPanel.getMentor6561Label().setText("<html>- <b><font color=\"#00CC00\">mentor6561</font></b>: " + lang.getMentor6561Description() + ".</html>");
        // Special thanks to
        CreditsPanel.getSpecialThanksLabel().setText("<html><b>" + lang.getSpecialThanksToText() + "</b></html>");
        CreditsPanel.getLitarvanLabel().setText("<html>- <b><font color=\"#00CC00\">TheShark34</font></b>: " + lang.getTheShark34Description() + ".</html>");
        CreditsPanel.getSupportersLabel().setText("<html><b>" + lang.getSupportersText() + ".</b></html>");
        // Technologies
        CreditsPanel.getTechnologies().setText("<html><b>" + lang.getTechnologiesText() + ":</b></html>");
        // Skins server
        OptionsPanel.getSkinsServerOnlineLabel().setText("<html>&nbsp;" + lang.getLoadingText() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>");
        OptionsPanel.getMultiplayerSessionOnlineLabel().setText("<html>&nbsp;" + lang.getLoadingText() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>");
        new Thread()
        {
            public void run()
            {
                try
                {
                    if (Utils.isOnline("https://skins.minecraft.net"))
                    {
                        OptionsPanel.getSkinsServerOnlineLabel().setText("<html>" + MineUSB.getLangUsed().getSkinsServerLabel() + " <font color=\"#00CC00\">\u2714</font</html>");
                    }
                    else
                    {
                        OptionsPanel.getSkinsServerOnlineLabel().setText("<html>" + MineUSB.getLangUsed().getSkinsServerLabel() + " <font color=\"#FF0000\">✖</font</html>");
                    }
                }
                catch (MalformedURLException e)
                {
                    OptionsPanel.getSkinsServerOnlineLabel().setText("<html>" + MineUSB.getLangUsed().getSkinsServerLabel() + " <font color=\"#FF0000\">✖</font</html>");
                }
            }
        }.start();
        new Thread()
        {
            public void run()
            {
                // Multiplayer session
                try
                {
                    if (Utils.isOnline("https://sessionserver.mojang.com"))
                    {
                        OptionsPanel.getMultiplayerSessionOnlineLabel().setText("<html>" + lang.getMultiplayerSessionLabel() + " <font color=\"#00CC00\">\u2714</font</html>");
                    }
                    else
                    {
                        OptionsPanel.getMultiplayerSessionOnlineLabel().setText("<html>" + lang.getMultiplayerSessionLabel() + " <font color=\"#FF0000\">✖</font</html>");
                    }
                }
                catch (MalformedURLException e)
                {
                    OptionsPanel.getMultiplayerSessionOnlineLabel().setText("<html>" + lang.getMultiplayerSessionLabel() + " <font color=\"#FF0000\">✖</font</html>");
                }
            }
        }.start();
    }

    public static void applyLauncher(Launcher launcher)
    {
        LaunchersPanel.getLauncherLogo().setIcon(launcher.getImageIcon());
        String os = "";
        for (int i = 0; i < launcher.getOSCompatibilityList().size(); i++)
        {
            OS os2 = launcher.getOSCompatibilityList().get(i);
            if (i == 0)
            {
                os = os + os2.getOSName();
            }
            else
                os = os + ", " + os2.getOSName();
        }
        // Selected launcher
        MineUSBPanel.getSelectedLauncherLabel().setText("<html>" + MineUSB.getLangUsed().getSelectedLauncherLabel() + "<b><font color=\"#00CC00\">" + launcher.getName() + "</font></b></html>");
        LaunchersPanel.getLauncherInformations().setText("<html><b>Launcher: </b><font color=\"#00CC00\">" + launcher.getName() + "</font><br />" + "<b>Description: </b><font color=\"#00CC00\">" + launcher.getDescription() + "</font><br />" + "<b>OS: </b><font color=\"#00CC00\">" + os + "</font></html>");
    }

    public static void shutdown()
    {
        if (frame != null)
        {
            frame.dispose();
        }
        if (optionsFrame != null)
        {
            optionsFrame.dispose();
        }
        if (licenseFrame != null)
        {
            licenseFrame.dispose();
        }
    }
}
