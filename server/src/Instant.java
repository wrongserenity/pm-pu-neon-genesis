/// класс заклинания
class Instant extends Card {
    public Effect effect;
    public int id;
    public int attack;
    public int health;
    public int avgHealth;
    public int cost;
    public int gameRule = 0;

    public Instant(int idCard, int health, int attack, int mana, int gameRule){
        super(idCard, health, attack, mana, gameRule);
    }


    public boolean isAlive() {
        return health>=1;
    }
}
