import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import javax.swing.JOptionPane;

public class Game {
    private Player player;
    private Boss boss;
    private List<Card> cardList;
    private Boolean attack = false;
    private List<Card> selectCard = new ArrayList<Card>();
    private Boolean gameover;

    public Game() {
        player = new Player();
        boss = new Boss(10);
        cardList = new ArrayList<Card>();
        readCardData();
        // generate_random_handcard();
    }

    public boolean getAttack() {
        return attack;
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
                    player.increaseHealth(2);
                if (selectCard.get(2).hasSpecialEffect())
                    attack_value += 2;
                break;
            }
            attribute_idx--;
        }
        if (attack_possible) {
            System.out.println("Attack:" + attack_value);
            boss.decreaseHealth(attack_value);
        } else {
            System.out.println("No playable cards. Pass this turn.");
        }

        if (boss.getHealth() <= 0) {
            System.out.println("Player wins. Game over!");
        }

        // Boss attack turn
        boolean boss_attack = new Random().nextBoolean();
        if (boss_attack) {
            int damage = new Random().nextInt(2) + 1;
            player.decreaseHealth(damage);
            System.out.println("boss Attack:" + damage);
        } else {
            int heal_hp = new Random().nextInt(2) + 1;
            boss.increaseHealth(heal_hp);
            System.out.println("boss Heal:" + heal_hp);
        }

        // System.out.println("boss Attack:" );

        // check if game over
        if (player.getHealth() <= 0) {
            System.out.println("Player loses. Game over!");
            showGameOverDialog();
        } else if (boss.getHealth() <= 0) {
            System.out.println("Player wins. Game over!");
            showGameOverDialog();
        }
    }
    private void showGameOverDialog() {
        int choice = JOptionPane.showConfirmDialog(null, "Restart Game?", "Game over!", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.out.println("Restart Game");

        } else {
            System.out.println("End Game");

        }
    }
}
