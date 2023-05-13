import java.util.ArrayList;
public class Player {
    private int health;
    private ArrayList<Card> hand;
    
    public Player() {
        health = 10;
        hand = new ArrayList<Card>();
    }
    
    public int getHealth() {
        return health;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }
    
    public void increaseHealth(int amount) {
        health += amount;
    }
    
    public void decreaseHealth(int amount) {
        health -= amount;
    }
    
    public ArrayList<Card> getHand() {
        return hand;
    }
    
    public void addCardToHand(Card card) {
        hand.add(card);
    }
    
    public void removeCardFromHand(Card card) {
        hand.remove(card);
    }
}
