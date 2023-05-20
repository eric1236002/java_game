import java.awt.Image;
import java.util.List;
import java.util.Random;

public class Card {
    private String name;
    private String type;
    private Boolean selected;
    private List<String> attributes;
    private boolean hasSpecialEffect;
    private Image image;


    public Card(String name, String type, List<String> attributes, boolean hasSpecialEffect) {
        this.name = name;
        this.type = type;
        this.attributes = attributes;
        this.hasSpecialEffect = hasSpecialEffect;
        this.selected = false;
    }
    
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Boolean getSelected() {
        return selected;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public boolean hasSpecialEffect() {
        return hasSpecialEffect;
    }

    public Image getImage() {
        if (image == null){
            image = Util.loadImage("Java card/" + this.name + ".png");
        }

        return image;
    }
    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", attributes=" + attributes +
                ", hasSpecialEffect=" + hasSpecialEffect +
                '}';
    }
}
