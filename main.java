import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Arrays;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class main {
    public static void main(String[] args) {
        // 建立玩家和卡片列表
        Player player = new Player(); // 初始血量為 10
        Boss boss = new Boss(10); // 初始血量為 10
        List<Card> cardList = createCardList();
        Map<String, Integer> attributeCondition = loadAttributeCondition();
        // 遊戲主要進行階段
        while (true) {
            // 玩家攻擊階段
            String selectedAttribute = getRandomAttribute(attributeCondition);
            int requiredCount = attributeCondition.get(selectedAttribute);
            // 在遊戲中使用選擇的屬性條件
            System.out.println("要求屬性:" + selectedAttribute);
            System.out.println("攻擊力" + requiredCount);
            List<Card> playableCards = getPlayableCards(cardList, selectedAttribute);
            System.out.println("Selected Attribute: " + selectedAttribute);
            System.out.println("Playable Cards: " + playableCards);

            // TODO: 玩家進行攻擊


            // Boss攻擊階段
            performBossAction(boss);

            // 結算階段
            if (player.getHealth() <= 0) {
                System.out.println("Player loses. Game over!");
                break;
            } else if (boss.getHealth() <= 0) {
                System.out.println("Player wins. Game over!");
                break;
            }
        }
    }

    private static List<Card> createCardList() {
        // 解析卡片 JSON
        List<Card> cardList = new ArrayList<>();
        try (FileReader reader = new FileReader("./Java_project/card.json")) {
            Gson gson = new Gson();
            Card[] cardsArray = gson.fromJson(reader, Card[].class);

            for (Card card : cardsArray) {
                cardList.add(card);
                System.out.println(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cardList;
    }
    private static Map<String, Integer> loadAttributeCondition() {
        try {
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Integer>>() {}.getType();
            Map<String, Integer> attributeCondition = gson.fromJson(new FileReader("./Java_project/question.json"), mapType);
            return attributeCondition;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    } 

    private static String getRandomAttribute(Map<String, Integer> attributeConditions) {
        Object[] attributes = attributeConditions.keySet().toArray();
        int randomIndex = (int) (Math.random() * attributes.length);
        return attributes[randomIndex].toString();
    }

    private static List<Card> getPlayableCards(List<Card> cardList, String selectedAttribute) {
        List<Card> playableCards = new ArrayList<>();
        for (Card card : cardList) {
            List<String> attributes = card.getAttributes();
            if (attributes.contains(selectedAttribute)) {
                playableCards.add(card);
            }
        }
        return playableCards;
    }

    private static void performBossAction(Boss boss) {
        boolean healthorattack = new Random().nextBoolean();
        if (healthorattack) {
            boss.increaseHealth(1);
            System.out.println("Boss回血");
        } else {
            boss.decreaseHealth(1);
            System.out.println("Boss攻擊");
        }
    }

}
