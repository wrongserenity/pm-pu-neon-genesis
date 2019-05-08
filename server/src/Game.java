class Game {
    public Player player1;
    public Player player2;

    public Player getEnemy(boolean first) {
        return (first) ? player2 : player1;
    }
}
