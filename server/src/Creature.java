import java.util.ArrayList;

class Creature implements Card {
    public int attack = 1;
    public int health = 1;
    public int avgHealth = 1;
    private ArrayList<Effect> curEffects;

    public Creature(int id){

    }


    public void changeHealth(int amount) {
        health += amount;
        if (health <= 0) {

        }
    }

    public void addEffect(Effect effect){
        if(effect.singleAction) {
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
