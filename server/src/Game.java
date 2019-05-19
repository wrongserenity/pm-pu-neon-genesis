import java.util.ArrayList;
import java.util.Random;

class Game {
    private Random rand;
    public int turn = 0;
    public Player player1;
    public Player player2;
    public int gameRule = 0;
    public ArrayList<Player> players = new ArrayList<>();
    public boolean[] isHappend;

    public Player getEnemy(boolean first) {
        return (first) ? player2 : player1;
    }

    public Game(){
        players.add(player1);
        players.add(player2);
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                player.myDeck.nextCard();
            }
        }
        /// массив, где оба элемента становятся true, когда что-то сделает
        /// и обратно на false, если у игрока обновится информация и проверится на умерших
        isHappend = new boolean[2];
        happened();
    }

    public void happened(){
        isHappend[0] = true;
        isHappend[1] = true;
    }

    // проверка наличия победителя, вызывается после каждой команды на сервере
    // зависит от нынешнего игрового правила gameRule
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

    // метод для изменения правила игры (на противоположный или рандом)
    public void gameRuleChange(int rule){
        if (rule == 1) {
            gameRule = (gameRule + 1) % 2;
        } else if (rule == 3) {
            gameRule = rand.nextInt(2);
        }
    }

    // метод, который вызывается из isWinner, когда есть победитель, принимает объект выигрывашего игрока
    // тут надо вставить блок отправки на клиент
    public void winner(Player player){
        System.out.println("Winner Nayden: " + player.name);
    }

    // метод для следующего хода
    public void nextTurn(){
        turn++;
        for (Player player : players) {
            player.nextTurn();
        }
    }





}
