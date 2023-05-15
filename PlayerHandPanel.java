import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;

public class PlayerHandPanel extends JPanel {
    private ArrayList<Card> hand;
    private ArrayList<Card_attribute_Panel> card_attribute;

    public PlayerHandPanel() {
        List<String> temp = new ArrayList<String>();
        temp.add("west");
        temp.add("athlete");
        List<String> temp2 = temp;

        Card buf = new Card("Curry", "people", temp, false);
        Card buf2 = new Card("C row", "people", temp2, false);
        hand = new ArrayList<>();
        hand.add(buf);
        hand.add(buf2);

        card_attribute = new ArrayList<>();
        for (Card card : hand) {
            card_attribute.add(new Card_attribute_Panel(card));
        }
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getHandSize() {
        return hand.size();
    }

    public JPanel HandCard() {
        // Add the button to the card
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weighty = 1.0;

        //List<JButton> cardButtons = new ArrayList<>();

        JPanel cardPanel = new JPanel();
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new GridBagLayout());

        JPanel guiPanel = new JPanel(new BorderLayout());
        guiPanel.setOpaque(false);

        // JPanel attribute = card_attribute.Attribute_panel();
        int width = 1;
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            JButton cardButton = new JButton();
            ImageIcon buttonIcon = new ImageIcon("picture/" + card.getName() + ".png");
            Image image = buttonIcon.getImage().getScaledInstance(150, 250, Image.SCALE_SMOOTH);
            
            JPanel attribute = card_attribute.get(i).Attribute_panel();
        
            attribute.setVisible(false);

            buttonIcon = new ImageIcon(image);
            cardButton.setBounds(20 + 20 * width, 50, 100, 150);
            width++;
            cardButton.setOpaque(false);
            cardButton.setContentAreaFilled(false);
            cardButton.setBorderPainted(false);
            cardButton.setBorder(null);
            cardButton.setIcon(buttonIcon);

            cardButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    // when mouse entered the button, show the card attribute panel
                    guiPanel.add(attribute, BorderLayout.NORTH); // add the card attribute panel
                    attribute.setVisible(true);
                }

                public void mouseExited(MouseEvent e) {
                    // when mouse exited the button, hide the card attribute panel
                    attribute.setVisible(false);
                }
            });
            gbc.gridx++; // make the card buttons in a row
            cardPanel.add(cardButton, gbc);
            
             
        }
        // Add the button panel to the GUI panel
        guiPanel.add(cardPanel, BorderLayout.WEST);

        return guiPanel;
    }
}
