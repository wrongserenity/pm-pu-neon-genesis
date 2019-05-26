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
        System.out.println(idCard+ "/" +  health+ "/" +  attack+ "/" +  mana);
            System.out.println(idCard + " /idCard");
            this.id = idCard;
            System.out.println(id + " id");
            this.attack = attack;
            this.avgHealth = health;
            this.health = health;
            this.cost = mana;
            System.out.println(id + "/" + attack + "/" + health + "/");
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
