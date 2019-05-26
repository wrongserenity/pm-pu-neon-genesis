public class GameRuleCard extends Card {
    public int attack;
    public int health;
    public int avgHealth;
    public int cost;
    public int id;
    public int gameRule;

    public GameRuleCard(int idCard, int health, int attack, int mana, int gameRule){
        super(idCard, health, attack, mana, gameRule);
        this.gameRule = gameRule;
    }

}
