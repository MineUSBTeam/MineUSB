package fr.mineusb.ui;

import fr.mineusb.MineUSB;
import fr.mineusb.MineUSBConstants;
import fr.mineusb.utils.TexturedPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LicenseFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -5181849295808467994L;

    private JTextArea licenseTextArea;
    private TexturedPanel panel;
    private JButton okButton;
    private final JPopupMenu popupMenu = new JPopupMenu();
    private final JMenuItem selectAllButton = new JMenuItem("Select All Text");
    private final JMenuItem copyTextButton = new JMenuItem("Copy All Text");

    public LicenseFrame() {
        JPanel parent = new JPanel();
        licenseTextArea = new JTextArea();
        panel = new TexturedPanel("/fr/mineusb/res/stone.png");
        okButton = new JButton();
        okButton.setText("Ok !");
        okButton.addActionListener(e -> this.dispose());
        okButton.setBorderPainted(false);
        okButton.setOpaque(false);
        panel.add(okButton);
        InputStream stream = MineUSB.class
                .getResourceAsStream("/fr/mineusb/res/LICENSE.txt");
        InputStreamReader reader = new InputStreamReader(stream);
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String text = "";
        for (String line : lines) {
            text = text + line + "\n";
        }
        this.licenseTextArea.setText(text);
        this.licenseTextArea.setEditable(false);

        this.popupMenu.add(this.selectAllButton);
        this.popupMenu.add(this.copyTextButton);
        this.licenseTextArea.setComponentPopupMenu(this.popupMenu);

        this.copyTextButton.addActionListener(e -> {
            try {
                StringSelection ss = new StringSelection(
                        LicenseFrame.this.licenseTextArea.getText());
                Toolkit.getDefaultToolkit().getSystemClipboard()
                        .setContents(ss, null);
            } catch (Exception ignored) {
            }
        });

        this.selectAllButton.addActionListener(e -> {
            LicenseFrame.this.licenseTextArea.setSelectionStart(0);
            LicenseFrame.this.licenseTextArea
                    .setSelectionEnd(LicenseFrame.this.licenseTextArea
                            .getText().length());
        });

        this.setTitle("MineUSB - License");
        this.setPreferredSize(new Dimension(500, 300));
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                MineUSB.class.getResource(MineUSBConstants.FAVICON_PATH)));
        parent.setLayout(new BorderLayout());
        parent.add(new JScrollPane(licenseTextArea), BorderLayout.PAGE_START);
        parent.add(panel, BorderLayout.PAGE_END);
        this.setContentPane(parent);
    }

}
