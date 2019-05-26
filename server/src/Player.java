import com.google.gson.annotations.Expose;

import java.awt.desktop.SystemSleepEvent;
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

    @Expose public boolean first = true;
    public Game curGame;

    @Expose public String name = "Pash0k";
    @Expose public int health = 18;
    @Expose public int mana = 1;
    @Expose public int maxMana = 1;
    @Expose public int indexHappend = -1;
    @Expose public Deck myDeck = new Deck();
    @Expose public boolean winner = false;
    @Expose public ArrayList<Card> hand = new ArrayList<>();
    public ArrayList<Effect> globalEffects = new ArrayList<>();
    @Expose public ArrayList<Card> battleground = new ArrayList<>();

    public Player(Socket socket, Game game, boolean first) {
        clientSocket = socket;
        System.out.println("socket too");

        curGame = game;
        System.out.println("object game in player");

        if (first) {
            game.player1 = this;
        } else {
            this.first = false;
            game.player2 = this;
            game.initiate();
        }
        System.out.println("param first");



        if (!first){
            indexHappend = 1;
        }else{
            indexHappend = 0;
        }
        System.out.println("enemy got");
    }

    public void addEffect(Effect effect){
        if(effect.singleAction) {
            effect.processEffect();
            return;
        }
        globalEffects.add(effect);
    }

    public void processEffects(){ }

    // мана, кэп
    public void changeMana(int amount) {
        mana -= amount;
    }

    // хп, кэп
    public void changeHealth(int amount) {
        health -= amount;
    }

    // следующий ход, вызывается из одноименного метода в классе Game
    public void nextTurn() {
        mana = maxMana;
        if(!myDeck.isEmpty()) {
            if (maxMana < 10) {
                maxMana++;
            }
            addCard(myDeck.nextCard());
        }else{
            changeHealth(2);
        }
        for (Card card : battleground) {
            card.movedUpdate();
        }
        cardALive();
    }

    public void cardALive(){
        for (int i = 0; i < battleground.size(); i++) {
            if (!battleground.get(i).isAlive()){
                battleground.remove(i);
                i--;
                System.out.println("CARD DELETED");
                /// отправить на клиент удаление этих карт
            }
        }
    }

    public int findCard(boolean isHand, int iden){
        if (isHand){
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).id == iden){
                    return i;
                }
            }
        }else{
            for (int i = 0; i < battleground.size(); i++) {
                if (battleground.get(i).id == iden){
                    return i;
                }
            }
        }
        System.out.println("CARD NOT FOUND");
        return -1;
    }

    // метод, принимающий id карты и добавляющий в руку объект класса, к которому эта карта принадлежит
    public void addCard(int idCard) {
        String data = (String) Database.getInctance().smembers(Integer.toString(idCard)).toArray()[0];
        String[] lexemes = data.trim().split(" ");
        int cardType = Integer.parseInt(lexemes[0]);
        int avHlth = Integer.parseInt(lexemes[1]);
        int att = Integer.parseInt(lexemes[2]);
        int cst = Integer.parseInt(lexemes[3]);
        int gmrule = Integer.parseInt(lexemes[4]);

        if (cardType == 0){
            hand.add(new Creature(idCard, avHlth, att, cst, gmrule));
            System.out.println(hand.get(hand.size() -1).id);
        } else if(cardType == 1){
            hand.add(new Instant(idCard, avHlth, att, cst, gmrule));
        } else{
            hand.add(new GameRuleCard(idCard, avHlth, att, cst, gmrule));
        }
    }

    // сыграть карту
    public void playCard(Card newCard, boolean fromHand) {
        battleground.add((Card) newCard);
        if (fromHand) hand.remove(newCard);
        changeMana(newCard.cost);
    }

    // должно вызываться в начале игры
    public void deckCreate(){
        myDeck.create();
    }

    // все намана?
    public boolean isAlive(){
        return health >= 1;
    }

    public void getAvailableCards() {

    }

    // хз, опять запупа для потоков
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

    // обработка команд клиента
    void commands() throws IOException, InterruptedException {
        var input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        var output = new PrintWriter(clientSocket.getOutputStream(), true);
        String line;
        output.println(curGame.toJson(this));
        System.out.println("SENT JSON");
        while ((line = input.readLine()) != null) {
            if  (curGame.players.size() != 2){
                continue;
            }

            output.println(mana + "/mana "+ health + "/health");

            /// проверка на наличие живых игроков и поиск умерших карт
            /// тут можно вставить блок отправки инфы на клиент
            /// тип что бы ни произошло, выполнится этот if

            if (curGame.isHappend[indexHappend]) {
                curGame.isWinner();
                for (Player player : curGame.players) {
                    player.cardALive();
                }

                curGame.isHappend[indexHappend] = false;
                output.println(curGame.toJson(this));

            }


            System.out.println(line);
            String[] lexemes = line.trim().split(" ");
            if (lexemes[0].equals("quit")) {
                break;
            }


            /// ПОЧИСТИ!!
            if (lexemes[0].equals("hand")){
                StringBuilder oops = new StringBuilder();
                for (Card card : hand){
                    oops.append(card.id);
                    oops.append(" ");
                    oops.append(card.attack);
                    oops.append(" ");
                    oops.append(card.health);
                    oops.append(" ");
                    oops.append(card.cost);
                    oops.append(" ");
                    oops.append(card.gameRule);
                    oops.append("   /  ");

                }
                System.out.println(oops);
                output.println(oops);
            }
            if (lexemes[0].equals("batt")){
                StringBuilder oops = new StringBuilder();
                for (Card card : battleground){
                    oops.append(card.id);
                    oops.append(" ");
                    oops.append(card.attack);
                    oops.append(" ");
                    oops.append(card.health);
                    oops.append(" ");
                    oops.append(card.cost);
                    oops.append(" ");
                    oops.append(card.gameRule);
                    oops.append("   /  ");
                    oops.append(card.moved);

                }
                System.out.println(oops);
                output.println(oops);
            }





                /// пропуск обработки когда не твой ход
            if (curGame.getEnemy(first) == curGame.players.get(0)){
                if (curGame.turn % 2 == 0){
                    continue;
                }
            }else{
                if (curGame.turn % 2 != 0){
                    continue;
                }
            }

            if (lexemes[0].equals("attack")) {
                int place1 = findCard(false, Integer.parseInt(lexemes[1]));
                if (battleground.get(place1).moved){
                    continue;
                }
                int enemy = Integer.parseInt(lexemes[2]);
                if (enemy == 0) {
                    curGame.getEnemy(first).changeHealth(battleground.get(place1).attack);
                } else {
                    int place2 = curGame.getEnemy(first).findCard(false, enemy);
                    curGame.getEnemy(first).battleground.get(place2)
                            .changeHealth(battleground.get(place1).attack);
                    battleground.get(place1)
                            .changeHealth(curGame.getEnemy(first).battleground.get(place2).attack);
                }
                System.out.println("ATTACKED");
                battleground.get(place1).movedNow();
                curGame.happened(); /// меняет соответствующую переменную происшествия на true


            } else if (lexemes[0].equals("play")) {
                /// обрабатывает все операции с вытягиванием карт из руки
                int place1 = findCard(true, Integer.parseInt(lexemes[2]));
                output.println(hand.get(place1).cost);
                if (lexemes[1].equals("creature")) {
                    if (mana >= hand.get(place1).cost) {
                        playCard(hand.get(place1), true);
                        System.out.println("CREATED");
                    }
                }else if(lexemes[1].equals("spell")){
                    if (mana >= hand.get(place1).cost) {
                        changeMana(hand.get(place1).cost);
                        if (lexemes[3].equals("0")){
                            curGame.getEnemy(first).changeHealth(hand.get(place1).attack);
                        }else {
                            int place2 = curGame.getEnemy(first).findCard(false, Integer.parseInt(lexemes[3]));
                            System.out.println(hand.get(place1).attack + "/attack");
                            System.out.println(curGame.getEnemy(first).battleground.get(place2).health);
                            curGame.getEnemy(first).battleground.get(place2).changeHealth(hand.get(place1).attack);
                            System.out.println(curGame.getEnemy(first).battleground.get(place2).health);

                        }
                        System.out.println("SPELT");
                        hand.remove(place1);
                    }
                }else if(lexemes[1].equals("rule")){
                    if (mana >= hand.get(place1).cost) {
                        changeMana(hand.get(place1).cost);
                        curGame.gameRuleChange(hand.get(place1).gameRule);
                        hand.remove(place1);
                        System.out.println("RULED");
                    }
                }
                curGame.happened(); /// меняет соответствующую переменную происшествия на true

             /// использование заклинания, не будет в первых версиях
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
            }

            else if(lexemes[0].equals("next")){
                /// следующий ход
                curGame.nextTurn();
                curGame.happened(); /// меняет соответствующую переменную происшествия на true

                output.println(" ");
            }
        }
        System.out.println("Disconnected");
        output.println("Disconnected");
        Thread.sleep(500);
    }
}
