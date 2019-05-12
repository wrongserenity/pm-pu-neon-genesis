public class GameRuleCard implements Card {
    public int attack;
    public int health;
    public int avgHealth;
    public int cost;
    public int id;
    public int gameRule;

    public GameRuleCard(int idCard, int health, int attack, int mana, int gameRule){
        this.id = idCard;
        this.attack = attack;
        this.avgHealth = health;
        this.health = health;
        this.cost = mana;
        this.gameRule = gameRule;
    }

    @Override
    public boolean isAlive() {
        return false;
    }
}
