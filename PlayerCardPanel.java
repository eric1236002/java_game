import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;
import java.io.File;

public class PlayerCardPanel extends JPanel{
    public Player player;
    private List<JButton> cardbtns = new ArrayList<JButton>();
    private Clip buttonMusic;
    public PlayerCardPanel(Player player) {
        setLayout(new GridBagLayout());
        this.player = player;
        for(int i=0;i<8;++i){
            cardbtns.add(new JButton());
        }
        //setPreferredSize(new Dimension(1240, 250));
    }

    public void update(){
        /* 
        System.out.println("playercardpanel update");
        System.out.println("player hand's card number: " + player.getHand().size());
        System.out.println("player hand's card btn number: " + cardbtns.size());
        System.out.println(player.getHand());
        */
        
        // remove all card btns
        this.removeAll();
        // clear card btns and add new card btns
        if(cardbtns.size()>0){
            this.cardbtns.clear();
        }
        for(int i=0;i<8;++i){
            cardbtns.add(new JButton());
        }

        // setting the panel and add card btns to the panel
        // finish setting of the button
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        for (Card card : player.getHand()) {
            //System.out.println(card.getName());
            // set the button's size and image
            cardbtns.get(player.getHand().indexOf(card)).setPreferredSize(new Dimension(150, 250));
            cardbtns.get(player.getHand().indexOf(card)).setContentAreaFilled(false);
            ImageIcon buttonIcon = new ImageIcon(card.getImage());
            Image image = buttonIcon.getImage().getScaledInstance(150, 250, Image.SCALE_SMOOTH);
            buttonIcon = new ImageIcon(image);
            cardbtns.get(player.getHand().indexOf(card)).setIcon(buttonIcon);
            this.add(cardbtns.get(player.getHand().indexOf(card)), constraints);
            constraints.gridx++;
            // add action listener to the button
            cardbtns.get(player.getHand().indexOf(card)).addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music/button2.wav"));
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
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    System.out.println(card.getName() + " is clicked");
                    card.setSelected(!card.getSelected());
                    System.out.println(card.getName() + " is Select:" + card.getSelected());
                }
            });
        }
        revalidate();
        repaint();
    }
}
