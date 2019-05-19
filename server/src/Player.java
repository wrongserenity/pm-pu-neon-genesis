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

    public String name = "Pash0k";
    public int health = 18;
    public int mana = 1;
    public int maxMana = 1;
    public int indexHappend = -1;
    public boolean isWizard = true;
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
        deckCreate();

        if (curGame.getEnemy(first) == curGame.players.get(0)){
            indexHappend = 1;
        }else{
            indexHappend = 0;
        }
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
        maxMana++;
        mana = maxMana;
        addCard(myDeck.nextCard());
    }

    public void cardALive(){
        for (Creature card: battleground) {
            if (!card.isAlive()){
                battleground.remove(card);
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
        Card newCard;

        if (cardType == 0){
            newCard = new Creature(idCard, avHlth, att, cst);
        } else if(cardType == 1){
            newCard = new Instant(idCard, avHlth, att, cst);
        } else{
            newCard = new GameRuleCard(idCard, avHlth, att, cst, gmrule);
        }
        hand.add(newCard);
    }

    // сыграть карту
    public void playCard(Card newCard, boolean fromHand) {
        battleground.add((Creature) newCard);
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

        while ((line = input.readLine()) != null) {

            /// проверка на наличие живых игроков и поиск умерших карт
            /// тут можно вставить блок отправки инфы на клиент
            /// тип что бы ни произошло, выполнится этот if
            if (curGame.isHappend[indexHappend]) {
                curGame.isWinner();
                for (Player player : curGame.players) {
                    player.cardALive();
                }

                curGame.isHappend[indexHappend] = false;
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

            String[] lexemes = line.trim().split(" ");
            if (lexemes[0].equals("quit")) {
                break;

            } else if (lexemes[0].equals("attack")) {
                int place1 = findCard(false, Integer.parseInt(lexemes[1]));
                int enemy = Integer.parseInt(lexemes[2]);
                if (enemy == 0) {
                    curGame.getEnemy(first).changeHealth(battleground.get(place1).attack);
                } else {
                    int place2 = curGame.getEnemy(first).findCard(false, enemy);
                    curGame.getEnemy(first).battleground.get(place2)
                            .changeHealth(-battleground.get(place1).attack);
                    battleground.get(place1)
                            .changeHealth(curGame.getEnemy(first).battleground.get(place2).attack);
                }
                curGame.happened(); /// меняет соответствующую переменную происшествия на true


            } else if (lexemes[0].equals("play")) {
                /// обрабатывает все операции с вытягиванием карт из руки
                int place1 = findCard(true, Integer.parseInt(lexemes[2]));
                if (lexemes[1].equals("creature")) {
                    if (mana > hand.get(place1).cost) {
                        changeMana(hand.get(place1).cost);
                        playCard(hand.get(place1), true);
                    }
                }else if(lexemes[1].equals("spell")){
                    if (mana > hand.get(place1).cost) {
                        changeMana(hand.get(place1).cost);
                        if (lexemes[3].equals("0")){
                            curGame.getEnemy(first).changeHealth(hand.get(place1).attack);
                        }else {
                            int place2 = curGame.getEnemy(first).findCard(false, Integer.parseInt(lexemes[3]));
                            curGame.getEnemy(first).battleground.get(place2).changeHealth(battleground.get(place1).attack);
                        }
                    }
                }else if(lexemes[1].equals("rule")){
                    if (mana > hand.get(place1).cost) {
                        changeMana(hand.get(place1).cost);
                        curGame.gameRuleChange(hand.get(place1).gameRule);
                    }
                }
                hand.remove(place1);
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
            }
        }
        System.out.println("Disconnected");
        output.println("Disconnected");
        Thread.sleep(500);
    }
}
