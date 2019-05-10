/// класс заклинания
class Instant implements Card {
    public Effect effect;

    @Override
    public boolean isAlive() {
        return health>=1;
    }
}
