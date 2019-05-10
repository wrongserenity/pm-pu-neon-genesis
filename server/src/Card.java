public interface Card {
    String name = null;
    String description = null;
    int id = 0,
            cost = 0,
            attack = 0,
            health = 0,
            avgHealth = 0;

    boolean isAlive();
}
