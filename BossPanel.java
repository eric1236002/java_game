import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BossPanel extends JPanel {
    private BufferedImage backgroundImage;
    public Boss boss;

    public BossPanel() {
        boss = new Boss(10);
        setPreferredSize(new Dimension(996, 600)); // BossPanel size
        //loadImage
        try {
            backgroundImage = ImageIO.read(new File("picture/boss.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public void setBossHealth(int health) {
        boss.setHealth(health);
        repaint(); // Update Boss health
    }
    */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // Draw Boss health bar
        g.setColor(Color.RED);
        g.fillRect(10, 10,boss.getHealth() * 20, 30);
        g.setColor(Color.BLACK);
        // Set the stroke to bold
        Graphics2D g2d = (Graphics2D) g;
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(3)); // Set the desired thickness
        g2d.drawRect(10, 10, boss.getHealth()* 20, 30);
        g2d.setStroke(oldStroke); // Restore the old stroke
        Font font = new Font("Arial", Font.BOLD, 26);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Boss health: " + boss.getHealth(), 10, 70);

    }

    public void update() {
        System.out.println("Boss Panel update!");
        repaint();
    }
}
