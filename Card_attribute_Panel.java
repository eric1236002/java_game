import javax.swing.*;
import java.awt.*;

public class Card_attribute_Panel {
    private Card card;

    public Card_attribute_Panel(Card card) {
        this.card = card;
    }
    
    public void set_card(Card card){
        this.card = card;
    }

    public JPanel Attribute_panel() {
        JPanel attribute_panel = new JPanel();
        attribute_panel.setOpaque(false);
        attribute_panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 50, 50);

        JLabel buf = new JLabel(card.getName());
        buf.setFont(new Font("Serif", Font.BOLD, 30));
        buf.setForeground(Color.BLACK);
        buf.setBackground(Color.WHITE);
        buf.setOpaque(true);
        gbc.gridy = 0; // controls vertical position
        gbc.insets = new Insets(0, 0, 0, 0);
        attribute_panel.add(buf, gbc);

        for (int i = 0; i < card.getAttributes().size(); i++) {
            JLabel att = new JLabel(card.getAttributes().get(i));
            att.setFont(new Font("Serif", Font.BOLD, 30));
            att.setForeground(Color.BLACK);
            att.setBackground(Color.WHITE);
            att.setOpaque(true);
            gbc.gridy = i + 1; // controls vertical position
            gbc.insets = new Insets(0, 0, 0, 0);
            attribute_panel.add(att, gbc);
        }
        return attribute_panel;
    }
}
