abstract class Card {
    protected String name;
    protected int attack;

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public abstract void use(Player player, Boss boss);
}

// 名人卡類別
class CelebrityCard extends Card {
    private String attribute;
    private boolean hasSpecialEffect;

    public CelebrityCard(String name, int attack, String attribute, boolean hasSpecialEffect) {
        this.name = name;
        this.attack = attack;
        this.attribute = attribute;
        this.hasSpecialEffect = hasSpecialEffect;
    }

    public String getAttribute() {
        return attribute;
    }

    public boolean hasSpecialEffect() {
        return hasSpecialEffect;
    }

    @Override
    public void use(Player player, Boss boss) {
        // 實作名人卡的使用效果
        // 根據特定規則進行攻擊等操作
    }
}

// 事件卡類別
class EventCard extends Card {
    private String effectTarget;

    public EventCard(String name, int attack, String effectTarget) {
        this.name = name;
        this.attack = attack;
        this.effectTarget = effectTarget;
    }

    public String getEffectTarget() {
        return effectTarget;
    }

    @Override
    public void use(Player player, Boss boss) {
        // 實作事件卡的使用效果
        // 對玩家或 boss 進行特殊操作
    }
}
