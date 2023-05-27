import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;

public class GamePanel extends JPanel implements Runnable {
    private Background background;
    private Game game;
    private BossPanel bossPanel;
    private PlayerPanel playerPanel;
    private PlayerCardPanel playerCardPanel;
    private Clip backgroundMusic;
    private Clip welcomeMusic;
    private Clip buttonMusic;
    public GamePanel() {
        background = new Background();
        background.setImage("picture/startscreen.png");
        game = new Game();
        playerPanel = new PlayerPanel();
        bossPanel = new BossPanel();

        playerPanel.player = game.getPlayer();
        bossPanel.boss = game.getBoss();
        welcomeMusic();
        
        playerCardPanel = new PlayerCardPanel(game.getPlayer());
        //playerCardPanel.SetPanel();
        game.setPlayerCardPanel(playerCardPanel);
        game.setBossPanel(bossPanel);
        game.setPlayerPanel(playerPanel);
        
        bossPanel.setOpaque(false);
        playerPanel.setOpaque(false);
        playerCardPanel.setOpaque(false);

        bossPanel.setVisible(false);
        playerPanel.setVisible(false);
        playerCardPanel.setVisible(false);

        setLayout(new GridBagLayout());
        
        SetPanel();
        new Thread(this).start();
    }

    private void SetPanel() {
        JButton playButton = new JButton();
        ImageIcon buttonIcon = new ImageIcon("picture/button.png");
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setIcon(buttonIcon);

        JButton attack_button = new JButton();
        ImageIcon attack_buttonIcon = new ImageIcon("picture/Attack_button.png");
        attack_button.setContentAreaFilled(false);
        attack_button.setBorderPainted(false);
        attack_button.setIcon(attack_buttonIcon);
        attack_button.setVisible(false);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(playButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        GridBagConstraints constraints = new GridBagConstraints();

        // set the button position
        constraints.gridx = 0; // row index
        constraints.gridy = 0; // column index
        constraints.gridwidth = 1; //
        constraints.gridheight = 1; //
        constraints.anchor = GridBagConstraints.PAGE_END; //
        this.add(Box.createVerticalStrut(250), constraints);
        constraints.gridy = 1; // column index
        this.add(buttonPanel, constraints);

        /**/
        // set the bosspanel position
        constraints.gridx = 0; // row index
        constraints.gridy = 0; // column index
        constraints.gridwidth = 4; //
        constraints.gridheight = 3; //
        constraints.weightx = 1.0; //
        constraints.weighty = 1.0; //
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        // constraints.insets = new Insets(0, 0, 0, 10);
        this.add(bossPanel, constraints);

        // set the playerpanel position
        constraints.gridx = 4; // row index
        constraints.gridy = 0; // column index
        constraints.gridwidth = 1; //
        constraints.gridheight = 2; //
        constraints.weightx = 1; //
        constraints.weighty = 1; //
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTHEAST;
        // constraints.insets = new Insets(0, 10, 0, 10);
        this.add(playerPanel, constraints);

        // set the attack button position
        constraints.gridx = 4; // row index
        constraints.gridy = 2; // column index
        constraints.gridwidth = 1; //
        constraints.gridheight = 1; //
        constraints.weightx = 1; //
        constraints.weighty = 1; //
        constraints.anchor = GridBagConstraints.EAST; //
        this.add(attack_button, constraints);

        // set the handcardpanel position
        constraints.gridx = 0; // row index
        constraints.gridy = 3; // column index
        constraints.gridwidth = 5; //
        constraints.gridheight = 1; //
        constraints.weightx = 1; //
        constraints.weighty = 1; //
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.SOUTH; //
        this.add(playerCardPanel, constraints);

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonMusic();
                background.setImage("picture/background.png");
                playButton.setVisible(false);
                attack_button.setVisible(true);
                bossPanel.setVisible(true);
                playerPanel.setVisible(true);
                buttonPanel.setVisible(false);
                playerCardPanel.setVisible(true);
                showStartDialog();
                pauseWelcomeMusic();
                playBackgroundMusic();
                // playerCardPanel.update();
                // playerCardPanel.SetPanel();
            }
        });

        attack_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.setAttack(true);
                System.out.println("attack btn:" + game.getAttack());
                System.out.println("Selected card: " + game.getSelectCards());
                buttonMusic();
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background image
        g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

    }

    public void update() {
        game.generate_random_handcard();
        playerCardPanel.update();
        game.update();
        playerPanel.update();
        bossPanel.update();
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

    private void showStartDialog() {
        JOptionPane.showMessageDialog(null, "Welcome to Lucky Battle\nThe princess has been captured by an evil dragon, and you must defeat the dragon to rescue the princess.\nThe game rules are as follows:\nSelect three cards of the same attribute from your hand and press the attack button. Sometimes, you can gain a healing effect.\nBe careful, as the evil dragon can also heal and attack.\nGood luck!", "Rule", JOptionPane.INFORMATION_MESSAGE);
    }
    private void playBackgroundMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music/fight.wav"));
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            backgroundMusic = (Clip) AudioSystem.getLine(info);
            // open
            backgroundMusic.open(audioInputStream);
            // set volume
            FloatControl gainControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-1.0f);
            // play
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY); // repeat
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void welcomeMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music/welcome.wav"));
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            welcomeMusic = (Clip) AudioSystem.getLine(info);
            // open
            welcomeMusic.open(audioInputStream);
            // set volume
            FloatControl gainControl = (FloatControl) welcomeMusic.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(1.0f);
            // play
            welcomeMusic.loop(Clip.LOOP_CONTINUOUSLY); // repeat
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buttonMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music/button.wav"));
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            buttonMusic = (Clip) AudioSystem.getLine(info);
            // open
            buttonMusic.open(audioInputStream);
            // set volume
            FloatControl gainControl = (FloatControl) buttonMusic.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(1.0f);
            // play
            buttonMusic.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to pause the background music
    private void pauseBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }

    // Method to pause the welcome music
    private void pauseWelcomeMusic() {
        if (welcomeMusic != null && welcomeMusic.isRunning()) {
            welcomeMusic.stop();
        }
    }
}