package pm.pu.neon.genesis.cards;

import com.google.gson.annotations.Expose;

import java.net.Socket;
import java.util.ArrayList;

public class Player {
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
}
