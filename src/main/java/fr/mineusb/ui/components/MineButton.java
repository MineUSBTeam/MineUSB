package fr.mineusb.ui.components;

import fr.mineusb.MineUSB;
import fr.theshark34.swinger.textured.STexturedButton;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Created by w67clement on 10/02/2016. <br><br/>
 * <p/>
 * Class of project: MineUSB
 */
public class MineButton extends STexturedButton
{
    private static BufferedImage buttonImg;
    private static BufferedImage buttonHoverImg;
    private static BufferedImage buttonDisabledImg;

    public MineButton()
    {
        super(buttonImg, buttonHoverImg, buttonDisabledImg);
    }

    public static void loadImages() throws IOException
    {
        buttonImg = ImageIO.read(MineUSB.class.getResource("/fr/mineusb/res/button.png"));
        buttonHoverImg = ImageIO.read(MineUSB.class.getResource("/fr/mineusb/res/button_hover.png"));
        buttonDisabledImg = ImageIO.read(MineUSB.class.getResource("/fr/mineusb/res/button_disabled.png"));
    }
}
