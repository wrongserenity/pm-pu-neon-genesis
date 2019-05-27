package pm.pu.neon.genesis.cards;
import com.google.gson.annotations.Expose;

public class Card {
    @Expose public int id,
            cost,
            attack,
            health,
            avgHealth,
            gameRule;
    @Expose public boolean moved = true;

    public Card(){

    }

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
}
