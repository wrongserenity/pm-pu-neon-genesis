import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

class Player implements Runnable {
    Socket clientSocket;
    // немного поясни про сокеты
    // тип что это и зачем нужно

    /// инструмент для общения между серваком и клиентом, через них передаются данные. Сокет на клиенте открывает порт для общения с серваком
    // если мы здесь объявляем first как true, то значит следующая строчка срабатывает раньше чем конструктор?

    /// жа, всё именно так

    public boolean first = true;
    public Game curGame;
    // имя здесь просто как затычка?

    ///мне было скучно

    public String name = "Pash0k";
    public int health = 18;
    public int mana = 1;
    public int maxMana = 1;
    public boolean isWizard = false;
    public Deck myDeck;
    public ArrayList<Card> hand = new ArrayList<>();
    public ArrayList<Effect> globalEffects = new ArrayList<>();
    public ArrayList<Creature> battleground = new ArrayList<>();

    public Player(Socket socket, Game game, boolean first) {
        clientSocket = socket;
        curGame = game;
        if (first) {
            game.player1 = this;
        } else {
            this.first = false;
            game.player2 = this;
        }
    }

    public void addEffect(Effect effect){
        if(effect.singleAction) {
            effect.processEffect();
            return;
        }
        globalEffects.add(effect);
    }

    public void processEffects(){

    }

    public void changeMana(int amount) {
        mana += amount;
    }

    public void changeHealth(int amount) {
        health += amount;
    }

    public void nextTurn() {
        maxMana++;
        mana = maxMana;
        addCard(myDeck.nextCard());
    }

    public void addCard(Card newCard) {
        hand.add(newCard);
    }

    public void playCard(Card newCard, boolean fromHand) {
        battleground.add((Creature) newCard);
        if (fromHand) hand.remove(newCard);
    }

    public boolean isAlive(){
        return health >= 1;
    }

    public void getAvailableCards() {

    }

    @Override
    public void run() {
        try {
            commands();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void commands() throws IOException, InterruptedException {
        var input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        var output = new PrintWriter(clientSocket.getOutputStream(), true);
        String line;
        while ((line = input.readLine()) != null) {
            String[] lexemes = line.trim().split(" ");
            if (lexemes[0].equals("quit")) {
                break;
            } else if (lexemes[0].equals("attack")) {
                int attacker = Integer.parseInt(lexemes[1]);
                int enemy = Integer.parseInt(lexemes[2]);
                if (enemy == 0) {
                    curGame.getEnemy(first).changeHealth(-battleground.get(attacker).attack);
                } else {
                    // как я понял, при первой лексеме "attack" enemy может быть либо 1 либо 0, но  тогда зачем писать
                    // (enemy - 1), если можно просто 0, мб здесь сложнее чем мне показалось

                    /// нет, 0 - противник, а (enemy-1) - карта на поле, сюда может передаваться любое число, если бы было 0 или 1, то я бы boolean использовал бы
                    curGame.getEnemy(first).battleground.get(enemy - 1)
                            .changeHealth(-battleground.get(attacker).attack);
                    battleground.get(attacker)
                            .changeHealth(-curGame.getEnemy(first).battleground.get(enemy - 1).attack);
                }
            } else if (lexemes[0].equals("play")) {
                int numb = Integer.parseInt(lexemes[1]);
                if (mana > hand.get(numb).cost) {
                    changeMana(-hand.get(numb).cost);
                    playCard(hand.get(numb), true);
                }
             // что делает use

             /// использование заклинания
            } else if (lexemes[0].equals("use")) {
                int numb = Integer.parseInt(lexemes[1]);
                int target = Integer.parseInt(lexemes[2]);
                // что такое team? - сразу несколько enemy?

                /// определяет в какой команде находится целевое существо, true - второй игрок, false - первый
                boolean team = Boolean.parseBoolean(lexemes[3]);
                if(target == 0){
                    curGame.getEnemy(team).addEffect(((Instant)hand.get(numb)).effect);
                } else {
                    curGame.getEnemy(team).battleground.get(target - 1)
                            .addEffect(((Instant)hand.get(numb)).effect);
                }
            } else if (lexemes[0].equals("getCard")) {
            }
        }
        System.out.println("Disconnected");
        output.println("Disconnected");
        Thread.sleep(500);
    }
}
