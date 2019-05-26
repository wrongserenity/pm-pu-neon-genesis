import com.google.gson.annotations.Expose;

public class Card {
    @Expose int id,
            cost,
            attack,
            health,
            avgHealth,
            gameRule;
    @Expose boolean moved = true;

    public Card(int idCard, int health, int attack, int mana, int gameRule) {
            this.id = idCard;
            this.attack = attack;
            this.avgHealth = health;
            this.health = health;
            this.cost = mana;
            this.gameRule = gameRule;
    }

    public Card(){

    }

    boolean isAlive(){
        System.out.println("CARD HEALTH: " + health);
        return health>=1;}

    public void movedUpdate(){
        moved = false;
    }
    public void movedNow(){moved = true;}

    public void changeHealth(int amount) {
        health -= amount;
    }

    public void addEffect(Effect effect) {
        if (effect.singleAction) {
            effect.processEffect();
            return;
        }
        //curEffects.add(effect);
    }



}
