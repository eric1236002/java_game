import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable {
    private Background background;
    private PlayerHandPanel playerHandPanel;
    private BossPanel bossPanel = new BossPanel();
    public GamePanel() {
        background = new Background();
        background.setImage("picture/startscreen.png");
        
        setLayout(new BorderLayout());

        // Create the GUI interface
        JPanel guiPanel = createGUIPanel();

        // Create the player hand panel
        playerHandPanel = new PlayerHandPanel();
        playerHandPanel.setVisible(false);
        bossPanel=new BossPanel();
        bossPanel.setVisible(false);
        bossPanel.setBounds(0, 0,700, 700); 
        bossPanel.setOpaque(false);
        // Add the GUI panel and player hand panel to the main panel
        add(guiPanel, BorderLayout.SOUTH);
        add(bossPanel);
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

        // Create the attack and pass button panel
        JPanel attack_pass_panel = new JPanel();
        attack_pass_panel.setLayout(new BoxLayout(attack_pass_panel, BoxLayout.Y_AXIS));
        attack_pass_panel.setOpaque(false);

        // Create the attack and pass button
        JButton attack_button = new JButton();
        ImageIcon attack_buttonIcon = new ImageIcon("picture/Attack_button.png");
        attack_button.setOpaque(false);
        attack_button.setContentAreaFilled(false);
        attack_button.setBorderPainted(false);
        attack_button.setBorder(null);
        attack_button.setIcon(attack_buttonIcon);
        attack_button.setVisible(false);
        
        JButton pass_button = new JButton();
        ImageIcon pass_buttonIcon = new ImageIcon("picture/Pass_button.png");
        pass_button.setOpaque(false);
        pass_button.setContentAreaFilled(false);
        pass_button.setBorderPainted(false);
        pass_button.setBorder(null);
        pass_button.setIcon(pass_buttonIcon);
        pass_button.setVisible(false);


        changeBackgroundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                background.setImage("picture/background.png");
                add(playerHandPanel.HandCard(), BorderLayout.SOUTH);
                playerHandPanel.setVisible(true);
                bossPanel.setVisible(true);
                changeBackgroundButton.setVisible(false);
                attack_button.setVisible(true);
                pass_button.setVisible(true);
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
        
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.anchor = GridBagConstraints.CENTER;
        gbc2.weighty = 1.0;
        // Set the bottom margin
        gbc2.insets = new Insets(10, 0, 50, 30); 
        attack_pass_panel.add(Box.createVerticalStrut(250));
        attack_pass_panel.add(attack_button, gbc2);
        attack_pass_panel.add(Box.createVerticalStrut(30));
        attack_pass_panel.add(pass_button, gbc2);

        // Add the button panel to the GUI panel
        JPanel guiPanel = new JPanel(new BorderLayout());
        guiPanel.setOpaque(false);
        guiPanel.add(buttonPanel, BorderLayout.CENTER);
        
        this.add(attack_pass_panel, BorderLayout.EAST);

        return guiPanel;
    }
}
