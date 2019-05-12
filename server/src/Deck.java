import java.util.ArrayList;
import java.util.Random;

class Deck {
    private static int size = 30;
    private Random rand;
    private int curIndex = 0;
    private ArrayList<Integer> deck = new ArrayList<>();

    // вытягивает следующую карту и меняет положение индекса
    public Integer nextCard() {
        curIndex++;
        return deck.get(curIndex-1);
    }

    // зачем нужен просмотр на несколько карт вперед

    /// разведка на несколько карт, хз есть ли в heartstone такие карты
    public Integer skipAndShowCard(int skip) {
        return deck.get(curIndex + skip);
    }

    // надо будет добавить в nextTurn вычитание хп, если закончилась колода
    public boolean isEmpty() {
        return curIndex == 30;
    }

    // создает и размешивает деку
    public void create(){
        for (int i=10; i<40;i++){
            deck.add(i);
        }
        shuffle(deck);
    }

    // размешивает ее, лол
    public void shuffle(ArrayList<Integer> playerDeck) {
        deck = playerDeck;
        for (int i = size - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = deck.get(i);
            deck.set(i, deck.get(index));
            deck.set(index, temp);
        }
    }
}
