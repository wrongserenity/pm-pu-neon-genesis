import java.util.ArrayList;

class Game {
    public int turn = 0;
    public Player player1;
    public Player player2;
    public int gameRule = 0;
    public ArrayList<Player> players = new ArrayList<>();

    public Player getEnemy(boolean first) {
        return (first) ? player2 : player1;
    }

    public Game(){
        players.add(player1);
        players.add(player2);
    }

    public void isWinner(){
        if (gameRule == 0){
            for (int i = 0; i < players.size(); i++){
                if(!players.get(i).isAlive()){
                    winner(players.get((i + 1) % 2));
                }
            }
        } else if (gameRule == 1) {
            for (int i = 0; i < players.size(); i++){
                if(!players.get(i).isAlive()){
                    winner(players.get(i));
                }
            }
        }
    }

    public void winner(Player player){
        System.out.println("Winner Nayden: " + player.name);
    }

    public void nextTurn(){
        turn++;
        for (Player player : players) {
            player.nextTurn();
        }
    }





}
