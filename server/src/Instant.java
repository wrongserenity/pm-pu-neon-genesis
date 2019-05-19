/// класс заклинания
class Instant implements Card {
    public Effect effect;
    public int id;
    public int attack;
    public int health;
    public int avgHealth;
    public int cost;
    public int gameRule = 0;

    public Instant(int idCard, int health, int attack, int mana){
        this.id = idCard;
        this.attack = attack;
        this.avgHealth = health;
        this.health = health;
        this.cost = mana;
    }

    @Override
    public boolean isAlive() {
        return health>=1;
    }
}
