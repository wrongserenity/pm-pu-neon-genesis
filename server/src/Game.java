import java.util.ArrayList;

class Game {
    public int turn = 0;
    public Player player1;
    public Player player2;
    public ArrayList<Player> players = new ArrayList<>();

    public Player getEnemy(boolean first) {
        return (first) ? player2 : player1;
    }

    public Game(){
        players.add(player1);
        players.add(player2);
    }

    public void nextTurn(){
        turn++;
        for (Player player : players) {
            player.nextTurn();
        }
    }



}
