import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable {
    private Background background;
    private PlayerHandPanel playerHandPanel;

    public GamePanel() {
        background = new Background();
        background.setImage("picture/startscreen.png");

        setLayout(new BorderLayout());

        // Create the GUI interface
        JPanel guiPanel = createGUIPanel();

        // Create the player hand panel
        playerHandPanel = new PlayerHandPanel();
        playerHandPanel.setVisible(false);

        // Add the GUI panel and player hand panel to the main panel
        add(guiPanel, BorderLayout.SOUTH);

        new Thread(this).start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
        
    }

    public void update() {
        repaint();
    }

    public void run() {
        try {
            while (true) {
                update();
                Thread.sleep(23);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel createGUIPanel() {
        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridBagLayout());

        // Create the button
        JButton changeBackgroundButton = new JButton();
        ImageIcon buttonIcon = new ImageIcon("picture/button.png");
        changeBackgroundButton.setOpaque(false);
        changeBackgroundButton.setContentAreaFilled(false);
        changeBackgroundButton.setBorderPainted(false);
        changeBackgroundButton.setBorder(null);
        changeBackgroundButton.setIcon(buttonIcon);

        changeBackgroundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                background.setImage("picture/background.png");
                add(playerHandPanel.HandCard(), BorderLayout.SOUTH);
                playerHandPanel.setVisible(true);
                changeBackgroundButton.setVisible(false);
                repaint();
            }
        });

        // Add the button to the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weighty = 1.0;
        // Set the bottom margin
        gbc.insets = new Insets(10, 0, 50, 30); 
        buttonPanel.add(changeBackgroundButton, gbc);

        // Add the button panel to the GUI panel
        JPanel guiPanel = new JPanel(new BorderLayout());
        guiPanel.setOpaque(false);
        guiPanel.add(buttonPanel, BorderLayout.CENTER);

        return guiPanel;
    }
}
