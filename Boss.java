class Boss {
    private int health;

    public Boss(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int num) {
        health = num;
    }

    public void decreaseHealth(int amount) {
        health -= amount;
    }

    public void increaseHealth(int amount) {
        health += amount;
    }
}
