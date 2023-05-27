import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.*;
import javax.swing.JOptionPane;

public class Game {
    private Player player;
    private Boss boss;
    private List<Card> cardList;
    private Boolean attack = false;
    private List<Card> selectCard;
    private Boolean gameover;
    private PlayerCardPanel playerCardPanel;
    private PlayerPanel playerPanel;
    private BossPanel bossPanel;
    private Clip winMusic;
    private Clip failMusic;
    public Game() {
        player = new Player();
        boss = new Boss(10);
        cardList = new ArrayList<Card>();
        selectCard = new ArrayList<Card>();
        readCardData();
        // generate_random_handcard();
    }

    public boolean getAttack() {
        return attack;
    }

    public void setPlayerCardPanel(PlayerCardPanel playerCardPanel) {
        this.playerCardPanel = playerCardPanel;
    }

    public void setBossPanel(BossPanel bossPanel) {
        this.bossPanel = bossPanel;
    }

    public void setPlayerPanel(PlayerPanel playerPanel) {
        this.playerPanel = playerPanel;
    }
    
    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public Boolean getGameover() {
        return gameover;
    }

    public Player getPlayer() {
        return player;
    }

    public Boss getBoss() {
        return boss;
    }

    public List<Card> getSelectCards() {
        return selectCard;
    }

    public int selectCardsize() {
        return selectCard.size();
    }

    public void generate_random_handcard() {
        // System.out.println("Generate random handcard");
        // clear handcard
        Random random = new Random();

        
        if (this.player.getHand().size() > 0) {
            System.out.println("clear handcard");
            for (int i = 0; i < 8; i++) {
                int randomNumber = random.nextInt(cardList.size());
                // System.out.println(cardList.get(randomNumber).getName()+cardList.get(randomNumber).getType()+cardList.get(randomNumber).getAttributes()+cardList.get(randomNumber).hasSpecialEffect());
                Card card = new Card(cardList.get(randomNumber).getName(), cardList.get(randomNumber).getType(),
                        cardList.get(randomNumber).getAttributes(), cardList.get(randomNumber).hasSpecialEffect());
                this.player.getHand().set(i, card);
            }
        } else {
            // Generate 8 cards to handcard
            for (int i = 0; i < 8; i++) {
                int randomNumber = random.nextInt(cardList.size());
                // System.out.println(cardList.get(randomNumber).getName()+cardList.get(randomNumber).getType()+cardList.get(randomNumber).getAttributes()+cardList.get(randomNumber).hasSpecialEffect());
                Card card = new Card(cardList.get(randomNumber).getName(), cardList.get(randomNumber).getType(),
                        cardList.get(randomNumber).getAttributes(), cardList.get(randomNumber).hasSpecialEffect());
                this.player.addCardToHand(card);
            }
        }
    }

    public void readCardData() {
        String csvFile = "./Java card/card.csv";
        String line;
        String csvSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                List<String> attributes = Arrays.asList(data).subList(2, data.length - 1);
                Card card = new Card(data[0], data[1], attributes, Boolean.parseBoolean(data[data.length - 1]));
                this.cardList.add(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void choose_attack_card() {
        while (selectCard.size() <= 3 && !attack) {
            for (Card card : player.getHand()) {
                // System.out.println(player.getHand().get(0).getSelected());
                if (card.getSelected() && !selectCard.contains(card)) {
                    selectCard.add(card);
                }
                if (!card.getSelected() && selectCard.contains(card)) {
                    selectCard.remove(card);
                }
            }
        }
    }

    public void update() {

        System.out.println("New Turn");
        attack = false;
        selectCard = new ArrayList<Card>();
        // player turn
        // selecting card
        while (true) {
            choose_attack_card();
            if (attack && selectCard.size() == 3) {
                break;
            }
            if (attack && selectCard.size() != 3) {
                attack = false;
            }
            if (selectCard.size() > 3) {
                for (Card card : selectCard) {
                    card.setSelected(false);
                    this.playerCardPanel.repaintCardBorder();
                }
                selectCard.removeAll(selectCard);
                selectCard = new ArrayList<Card>();
                attack = false;
            }
            // System.out.println("selected card: " + selectCard);
        }
        // attack turn
        // check if attack is possible
        boolean attack_possible = false;
        int attack_value = 0;
        int attribute_idx = selectCard.get(0).getAttributes().size() - 1;
        while (attribute_idx >= 0) {
            if (!selectCard.get(0).getAttributes().get(attribute_idx).equals("None")
                    && selectCard.get(0).getAttributes().get(attribute_idx)
                            .equals(selectCard.get(1).getAttributes().get(attribute_idx))
                    && selectCard.get(0).getAttributes().get(attribute_idx)
                            .equals(selectCard.get(2).getAttributes().get(attribute_idx))) {
                attack_possible = true;
                attack_value = attribute_idx + 1;

                if (selectCard.get(0).hasSpecialEffect())
                    attack_value *= 2;
                if (selectCard.get(1).hasSpecialEffect())
                    //player.increaseHealth(2);
                    playerPanel.increasePlayerHealth(2);
                if (selectCard.get(2).hasSpecialEffect())
                    attack_value += 2;
                break;
            }
            attribute_idx--;
        }
        if (attack_possible) {
            System.out.println("Attack:" + attack_value);
            //boss.decreaseHealth(attack_value);
            bossPanel.decreaseBossHealth(attack_value);
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No playable cards. Pass this turn.");
        }

        if (boss.getHealth() <= 0) {
            System.out.println("Player wins. Game over!");
        }

        // Boss attack turn
        boolean boss_attack = new Random().nextBoolean();
        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (boss_attack) {
            int damage = new Random().nextInt(2) + 1;
            //player.decreaseHealth(damage);
            playerPanel.decreasePlayerHealth(damage);
            System.out.println("boss Attack:" + damage);
        } else {
            int heal_hp = new Random().nextInt(2) + 1;
            //boss.increaseHealth(heal_hp);
            bossPanel.increaseBossHealth(heal_hp);
            System.out.println("boss Heal:" + heal_hp);
        }

        // System.out.println("boss Attack:" );

        // check if game over
        if (player.getHealth() <= 0) {
            System.out.println("Player loses. Game over!");
            showGameOverDialog();
        } else if (boss.getHealth() <= 0) {
            System.out.println("Player wins. Game over!");
            showWinDialog();
        }
    }
    private void showGameOverDialog() {
        failMusic();
        int choice = JOptionPane.showConfirmDialog(null, "Restart Game?", "Game over!", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.out.println("Restart Game");
            player.setHealth(10);
            boss.setHealth(10);
        } else {
            System.out.println("End Game");
            System.exit(0);
        }
    }
    private void showWinDialog() {
        winMusic();
        int choice = JOptionPane.showConfirmDialog(null, "Restart Game?", "Win!", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.out.println("Restart Game");
            player.setHealth(10);
            boss.setHealth(10);
        } else {
            System.out.println("End Game");
            System.exit(0);
        }
    }
    private void winMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music/win.wav"));
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            winMusic = (Clip) AudioSystem.getLine(info);
            // open
            winMusic.open(audioInputStream);
            // set volume
            FloatControl gainControl = (FloatControl) winMusic.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(1.0f);
            // play
            winMusic.start();
            //one time
            winMusic.loop(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void failMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music/fail.wav"));
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            failMusic = (Clip) AudioSystem.getLine(info);
            // open
            failMusic.open(audioInputStream);
            // set volume
            FloatControl gainControl = (FloatControl) failMusic.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(1.0f);
            // play
            failMusic.start();
            //one time
            failMusic.loop(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
