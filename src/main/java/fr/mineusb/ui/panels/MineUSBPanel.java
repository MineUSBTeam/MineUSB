package fr.mineusb.ui.panels;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.mineusb.exceptions.LauncherNotFoundException;
import fr.mineusb.system.launchers.Launcher;
import fr.mineusb.system.launchers.LauncherManager;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.logging.log4j.Logger;

public class MineUSBPanel extends JPanel
{

    /**
     *
     */
    private static final long serialVersionUID = -351175175284520251L;
    // Logger
    private static Logger logger;
    // Label
    private static JLabel logo;
    private static JLabel description;
    private static JLabel selectedLauncherLabel;
    private static JLabel authors;
    // Buttons
    private static JButton playButton;
    private static JButton optionsButton;
    private static JButton quitButton;
    private Image image;
    private Image bgImage;

    public MineUSBPanel()
    {
        logger = MineUSB.getConsole();
        this.setOpaque(true);
        // Add logo
        this.add(getLogo());
        // Add description label
        this.add(getDescriptionLabel());
        // Add selected launcher label
        this.add(getSelectedLauncherLabel());
        // Add panel
        ButtonsPanel buttonsPanel = new ButtonsPanel();
        // Buttons
        buttonsPanel.add(getPlayButton());
        buttonsPanel.add(getOptionsButton());
        buttonsPanel.add(getQuitButton());

        // Add buttons
        this.add(buttonsPanel);

        // Add authors label
        this.add(getAuthorsLabel());

        // Background
        try
        {
            this.bgImage = ImageIO.read(MineUSB.class.getResource(MineUSBConstants.DIRT_PATH)).getScaledInstance(32, 32, 16);
        }
        catch (IOException e)
        {
            logger.error("Unexpected exception initializing MineUSB's panel");
        }
        catch (IllegalArgumentException ex)
        {
            logger.error("Unexpected exception initializing MineUSB's panel");
            logger.error("Reason: Image is null?");
        }
    }

    public static JLabel getLogo()
    {
        if (logo == null)
        {
            logo = new JLabel();
            logo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MineUSB.class.getResource("/fr/mineusb/res/mineusb.png"))));
            logo.setSize(new Dimension(200, 50));
        }
        return logo;
    }

    public static JLabel getDescriptionLabel()
    {
        if (description == null)
        {
            description = new JLabel();
            description.setForeground(Color.WHITE);
            description.setBounds(15, 66, 365, 44);
            description.setPreferredSize(new Dimension(385, 40));
            description.setHorizontalAlignment(0);
            description.setText(MineUSB.getLangUsed().getDescriptionLabel());
        }
        return description;
    }

    public static JLabel getSelectedLauncherLabel()
    {
        if (selectedLauncherLabel == null)
        {
            selectedLauncherLabel = new JLabel();
            selectedLauncherLabel.setForeground(Color.WHITE);
            selectedLauncherLabel.setPreferredSize(new Dimension(385, 25));
            selectedLauncherLabel.setHorizontalAlignment(0);
            selectedLauncherLabel.setText("<html>" + MineUSB.getLangUsed().getSelectedLauncherLabel() + "<b><font color=\"#00CC00\">" + MineUSB.getLauncherUsed().getName() + "</b></font></html>");
        }
        return selectedLauncherLabel;
    }

    public static JLabel getAuthorsLabel()
    {
        if (authors == null)
        {
            authors = new JLabel();
            authors.setForeground(Color.WHITE);
            authors.setBounds(15, 66, 365, 44);
            authors.setPreferredSize(new Dimension(385, 50));
            authors.setHorizontalAlignment(0);
            authors.setText("<html>" + MineUSB.getLangUsed().getAuthorsLabel().replace("<author>", "<font color=\"#00CC00\">w67clement</font>").replace("<contributors>", "<font color=\"#00CC00\">mentor6561</font>, <font color=\"#00CC00\">xolider</font>") + "</html>");
        }
        return authors;
    }

    public static JButton getPlayButton()
    {
        if (playButton == null)
        {
            playButton = new JButton();
            playButton.setText(MineUSB.getLangUsed().getPlayButton());
            playButton.setBorderPainted(false);
            playButton.setOpaque(false);
            //playButton.setPreferredSize(new Dimension(75, 25));
            playButton.addActionListener(e -> new Thread(() -> {
                try
                {
                    Launcher launcher = LauncherManager.getLauncher(MineUSB.getConfig().getLauncherType());
                    if (launcher.runLauncher() && (launcher.getName() != "Minecraft"))
                    {
                        MineUSB.getConsole().info("Launcher '" + launcher.getName() + "' was launched successfully! Enjoy with Minecraft!");
                    }
                }
                catch (LauncherNotFoundException e1)
                {
                    e1.printStackTrace();
                }
            }).start());
        }
        return playButton;
    }

    public static JButton getOptionsButton()
    {
        if (optionsButton == null)
        {
            optionsButton = new JButton();
            optionsButton.setText(MineUSB.getLangUsed().getOptionsButton());
            optionsButton.setBorderPainted(false);
            optionsButton.setOpaque(false);
            optionsButton.addActionListener(e -> MineUSB.getOptionsFrame().setVisible(true));
        }
        return optionsButton;
    }

    public static JButton getQuitButton()
    {
        if (quitButton == null)
        {
            quitButton = new JButton();
            quitButton.setText(MineUSB.getLangUsed().getQuitButton());
            quitButton.setBorderPainted(false);
            quitButton.setOpaque(false);
            quitButton.addActionListener(e -> MineUSB.shutdown());
        }
        return quitButton;
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void paintComponent(Graphics graphics)
    {
        int width = getWidth() / 2 + 1;
        int height = getHeight() / 2 + 1;
        if ((this.image == null) || (this.image.getWidth(null) != width) || (this.image.getHeight(null) != height))
        {
            this.image = createImage(width, height);
            copyImage(width, height);
        }
        graphics.drawImage(this.image, 0, 0, width * 2, height * 2, null);
    }

    protected void copyImage(int width, int height)
    {
        Graphics imageGraphics = this.image.getGraphics();
        for (int x = 0; x <= width / 32; x++)
        {
            for (int y = 0; y <= height / 32; y++)
            {
                imageGraphics.drawImage(this.bgImage, x * 32, y * 32, null);
            }
        }
        if ((imageGraphics instanceof Graphics2D))
        {
            overlayGradient(width, height, (Graphics2D) imageGraphics);
        }
        imageGraphics.dispose();
    }

    protected void overlayGradient(int width, int height, Graphics2D graphics)
    {
        int gh = 1;
        graphics.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(553648127, true), new Point2D.Float(0.0F, gh), new Color(0, true)));
        graphics.fillRect(0, 0, width, gh);

        gh = height;
        graphics.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(0, true), new Point2D.Float(0.0F, gh), new Color(1610612736, true)));
        graphics.fillRect(0, 0, width, gh);
    }

    private static class ButtonsPanel extends JPanel
    {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        private Image image;
        private Image bgImage;

        public ButtonsPanel()
        {
            this.setPreferredSize(new Dimension(260, 40));
            this.setSize(260, 40);
            // Add layout
            // BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
            // this.setLayout(layout);
            // Background
            try
            {
                this.bgImage = ImageIO.read(MineUSB.class.getResource(MineUSBConstants.STONE_PATH)).getScaledInstance(32, 32, 16);
            }
            catch (IOException e)
            {
                logger.error("Unexpected exception initializing MineUSB's panel");
            }
            catch (IllegalArgumentException ex)
            {
                logger.error("Unexpected exception initializing MineUSB's panel");
                logger.error("Reason: Image is null?");
            }
        }

        public void update(Graphics g)
        {
            paint(g);
        }

        public void paintComponent(Graphics graphics)
        {
            int width = getWidth() / 2 + 1;
            int height = getHeight() / 2 + 1;
            if ((this.image == null) || (this.image.getWidth(null) != width) || (this.image.getHeight(null) != height))
            {
                this.image = createImage(width, height);
                copyImage(width, height);
            }
            graphics.drawImage(this.image, 0, 0, width * 2, height * 2, null);
        }

        protected void copyImage(int width, int height)
        {
            Graphics imageGraphics = this.image.getGraphics();
            for (int x = 0; x <= width / 32; x++)
            {
                for (int y = 0; y <= height / 32; y++)
                {
                    imageGraphics.drawImage(this.bgImage, x * 32, y * 32, null);
                }
            }
            if ((imageGraphics instanceof Graphics2D))
            {
                overlayGradient(width, height, (Graphics2D) imageGraphics);
            }
            imageGraphics.dispose();
        }

        protected void overlayGradient(int width, int height, Graphics2D graphics)
        {
            int gh = 1;
            graphics.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(553648127, true), new Point2D.Float(0.0F, gh), new Color(0, true)));
            graphics.fillRect(0, 0, width, gh);

            gh = height;
            graphics.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(0, true), new Point2D.Float(0.0F, gh), new Color(1610612736, true)));
            graphics.fillRect(0, 0, width, gh);
        }
    }
}
