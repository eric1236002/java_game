import javax.swing.*;

public class Lucky_Battle{
    public static void main(String[] args){
        JFrame frame = new JFrame("Lucky Battle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 800);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
        GamePanel panel = new GamePanel();
        frame.add(panel);
    }
}