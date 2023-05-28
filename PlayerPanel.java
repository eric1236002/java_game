import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class PlayerPanel extends JPanel{
    public Player player;
    // --------------------------------------------
    private boolean isHealthDecreasing;
    private Timer healthTimer;
    private int healthAnimationDuration;
    private int initialHealth;
    private int targetHealth;
    private long healthAnimationStartTime;
    // -------------------------------------------

    public PlayerPanel() {
        player = new Player();
        setPreferredSize(new Dimension(248, 400)); // PlayerPanel size
        // -------------------------------------------
        isHealthDecreasing = false;
        healthTimer = new Timer();
        healthAnimationDuration = 1000; // Animation duration in milliseconds

        // -------------------------------------------
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
        /* 
        if (player.getHealth() > 10) {
            g2d.drawRect(201, 11, player.getHealth() * 20, 30);
        } else {
            g2d.drawRect(201, 11, 10 * 20, 30);
        }
        */
        g2d.drawRect(201, 11, player.getHealth() * 20, 30);
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

    public void decreasePlayerHealth(int amount){
        initialHealth = player.getHealth();
        if(player.getHealth() - amount <= 0)
            targetHealth = 0;
        else
            targetHealth = player.getHealth() - amount;
        isHealthDecreasing = true;
        healthAnimationStartTime = System.currentTimeMillis();
        startHealthAnimation();
    }

    public void increasePlayerHealth(int amount){
        initialHealth = player.getHealth();
        targetHealth = initialHealth + amount;
        isHealthDecreasing = false;
        healthAnimationStartTime = System.currentTimeMillis();
        startHealthAnimation();
    }

    public void startHealthAnimation(){
        healthTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                long elapsedTime = System.currentTimeMillis() - healthAnimationStartTime;
                double progress = (double) elapsedTime / healthAnimationDuration;

                if(progress >= 1.0){
                    if(isHealthDecreasing){
                        player.setHealth(targetHealth);
                    }
                    else{
                        player.setHealth(targetHealth);
                    }
                    cancel();
                }
                else{
                    int currentHealth = (int) (initialHealth + (targetHealth - initialHealth) * progress);
                    player.setHealth(currentHealth);
                }
                repaint();
            }
        }, 0, 10);
    }
}