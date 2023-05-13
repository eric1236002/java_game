import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class json_question_load_test {
    public static void main(String[] args) {
        try {
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Integer>>() {}.getType();
            Map<String, Integer> attributeCondition = gson.fromJson(new FileReader("./Java_project/question.json"), mapType);
            // 隨機選擇一個屬性
            String selectedAttribute = getRandomAttribute(attributeCondition);
            int requiredCount = attributeCondition.get(selectedAttribute);

            // 在遊戲中使用選擇的屬性條件
            System.out.println("要求屬性:" + selectedAttribute);
            System.out.println("攻擊力" + requiredCount);

            // 在這裡你可以根據選擇的屬性條件進行相應的遊戲邏輯處理
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String getRandomAttribute(Map<String, Integer> attributeConditions) {
        Object[] attributes = attributeConditions.keySet().toArray();
        int randomIndex = (int) (Math.random() * attributes.length);
        return attributes[randomIndex].toString();
    }
}
