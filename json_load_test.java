import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
public class json_load_test {
    public static void main(String[] args) {
        try {
            Gson gson = new Gson();
            Card[] cards = gson.fromJson(new FileReader("./Java_project/data.json"), Card[].class);
            List<Card> cardList = Arrays.asList(cards);

            for (Card card : cardList) {
                System.out.println(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
