import java.awt.Image;

public class StartScreen {
    private Image image;
    StartScreen(){

    }
    public Image getImage(){
        if (image == null){
            image = Util.loadImage("picture/startscreen.png");
        }

        return image;
    }
    
}
