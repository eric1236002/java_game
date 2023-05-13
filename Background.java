import java.awt.Image;

public class Background {
    private Image image;
    Background(){

    }   
    public void setImage(String image_path){
        System.out.println("Setting background image to " + image_path);
        this.image = Util.loadImage(image_path);
    }

    public Image getImage(){
        if (image == null){
            image = Util.loadImage("picture/background.png");
        }

        return image;
    }
}
