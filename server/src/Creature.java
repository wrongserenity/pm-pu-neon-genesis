import java.util.ArrayList;

class Creature implements Card {
    public int attack;
    public int health;
    public int avgHealth;
    public int cost;
    public int id;
    private ArrayList<Effect> curEffects;

    public Creature(int idCard, int health, int attack, int mana) {
        this.id = idCard;
        this.attack = attack;
        this.avgHealth = health;
        this.health = health;
        this.cost = mana;
    }

    // метод для изменения хп, кэп
    public void changeHealth(int amount) {
        health += amount;
        if (health <= 0) {

        }
    }

    public void addEffect(Effect effect) {
        if (effect.singleAction) {
            effect.processEffect();
            return;
        }
        curEffects.add(effect);
    }

    @Override
    public boolean isAlive() {
        return health >= 1;
    }
}
