import java.util.List;

public class Card {
    private String name;
    private String type;
    private List<String> attributes;
    private boolean hasSpecialEffect;

    public Card(String name, String type, List<String> attributes, boolean hasSpecialEffect) {
        this.name = name;
        this.type = type;
        this.attributes = attributes;
        this.hasSpecialEffect = hasSpecialEffect;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public boolean hasSpecialEffect() {
        return hasSpecialEffect;
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
