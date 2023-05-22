import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerPanel extends JPanel{
    public Player player;

    public PlayerPanel() {
        player = new Player();
        setPreferredSize(new Dimension(248, 400)); // PlayerPanel size
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        // Draw player health bar
        g.setColor(Color.RED);
        g.fillRect(200, 10, player.getHealth() * 20, 30);
        g.setColor(Color.BLACK);
        // Set the stroke to bold
        Graphics2D g2d = (Graphics2D) g;
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(3)); // Set the desired thickness
        g2d.drawRect(200, 10, player.getHealth() * 20, 30);
        if (player.getHealth() > 10) {
            g2d.drawRect(201, 11, player.getHealth() * 20, 30);
        } else {
            g2d.drawRect(201, 11, 10 * 20, 30);
        }
        g2d.setStroke(oldStroke); // Restore the old stroke
        Font font = new Font("Arial", Font.BOLD, 26);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Player health: " + player.getHealth(), 200, 70);

    }

    public void update(){
        System.out.println("PlayerPanel update!");
        repaint();
    }
}