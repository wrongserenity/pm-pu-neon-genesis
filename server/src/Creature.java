import java.util.ArrayList;

class Creature extends Card {
    public int attack;
    public int health;
    public int avgHealth;
    public int cost;
    public int id;
    public int gameRule = 0;
    public boolean moved = false;
    private ArrayList<Effect> curEffects;

    public Creature(int idCard, int health, int attack, int mana, int gameRule) {
        super(idCard, health, attack, mana, gameRule);
        this.moved = true;
    }

    // метод для изменения хп, кэп
    public void changeHealth(int amount) {
        health += amount;
    }

    public void addEffect(Effect effect) {
        if (effect.singleAction) {
            effect.processEffect();
            return;
        }
        curEffects.add(effect);
    }


    public boolean isAlive() {
        return health >= 1;
    }

    public void movedUpdate(){
        moved = false;
    }
}
