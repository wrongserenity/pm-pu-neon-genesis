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


    public void addEffect(Effect effect) {
        if (effect.singleAction) {
            effect.processEffect();
            return;
        }
        curEffects.add(effect);
    }


}
