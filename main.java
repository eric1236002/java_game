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

public class Main {
    public static void main(String[] args) {
        // 建立玩家和卡片列表
        Player player = new Player(); // 初始血量為 10
        Boss boss = new Boss(10); // 初始血量為 10
        List<Card> cardList = createCardList();
        Map<String, Integer> attributeCondition = loadAttributeCondition();
        // 添加初始手牌
        for (int i = 0; i < 5; i++) {
            Card card = drawCard(cardList);
            player.addCardToHand(card);
        }
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

            // 玩家選3張相同屬性牌
            if (playableCards.size() >= 3) {
                List<Card> selectedCards = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    Card card = drawCard(playableCards);
                    selectedCards.add(card);
                }
                System.out.println("Selected Cards: " + selectedCards);
                // 從手牌中移除選擇的卡片
                for (Card card : selectedCards) {
                    player.removeCardFromHand(card);
                }
                boss.decreaseHealth(requiredCount);
            } else {
                System.out.println("No playable cards. Pass this turn.");
            }
            // Boss攻擊階段
            performBossAction(player,boss);

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
    public static Card drawCard(List<Card> cardList) {
        if (cardList.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(cardList.size());
        Card card = cardList.get(index);
        return card;
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

    private static void performBossAction(Player player,Boss boss) {
        boolean healthorattack = new Random().nextBoolean();
        if (healthorattack) {
            boss.increaseHealth(1);
            System.out.println("Boss回血");
            System.out.println("Boss血量:" + boss.getHealth());
        } else {
            player.decreaseHealth(1);
            System.out.println("Boss攻擊");
            System.out.println("玩家血量:" + player.getHealth());
        }
    }

}
