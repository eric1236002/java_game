import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable {
    public Background background;

    public GamePanel() {
        background = new Background();
        background.setImage("picture/startscreen.png");

        setLayout(new BorderLayout());

        // Create a new transparent panel for the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridBagLayout());

        // create button
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
                repaint();
                changeBackgroundButton.setVisible(false);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        buttonPanel.add(changeBackgroundButton, gbc);
        gbc.fill = GridBagConstraints.BOTH;

        // Create a filler component and add it to the panel
        Component filler = Box.createVerticalGlue();
        gbc.gridy = 0;
        buttonPanel.add(filler, gbc);

        // Add the button to the panel
        gbc.gridy = 1;
        buttonPanel.add(changeBackgroundButton, gbc);

        // Add the button panel to the main panel
        add(buttonPanel, BorderLayout.CENTER);
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
}
