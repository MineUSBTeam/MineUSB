package fr.mineusb.ui.panels.launcher;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.mineusb.utils.TexturedPanel;
import java.awt.Color;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Created by w67clement on 08/04/2016.
 * <p>
 * Class of project: MineUSB
 */
public class MainLauncherPanel extends TexturedPanel
{

    /**
     *
     */
    private static final long serialVersionUID = -9085978411315617518L;

    private static JLabel logo;
    private static JLabel title;

    public MainLauncherPanel()
    {
        super(MineUSBConstants.DIRT_PATH);
        this.setLayout(null);

        this.add(getLogo());
        this.add(getTitle());
    }

    public static JLabel getLogo()
    {
        if (logo == null)
        {
            logo = new JLabel();
            try
            {
                logo.setIcon(new ImageIcon(ImageIO.read(MineUSB.class.getResource("/fr/mineusb/res/favicon.png")).getScaledInstance(32, 32, 16)));
            }
            catch (IOException e)
            {
                MineUSB.getConsole().error(e);
            }
            logo.setBounds(5, 5, 32, 32);
        }
        return logo;
    }

    public static JLabel getTitle()
    {
        if (title == null)
        {
            title = new JLabel();
            title.setText("<html>" + MineUSB.getLangUsed().getMineUSBLauncherTitleText() + "(v" + MineUSB.LAUNCHER_VERSION + ")<html>");
            title.setForeground(Color.WHITE);
            title.setBounds(42, 5, 256, 16);
        }
        return title;
    }
}
