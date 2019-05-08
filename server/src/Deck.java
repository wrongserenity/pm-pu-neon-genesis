import java.util.Random;

class Deck {
    private static int size = 30;
    private Random rand;
    private int curIndex = 0;
    private Card[] deck = new Card[size];

    public Card nextCard() {
        return deck[curIndex++];
    }

    // зачем нужен просмотр на несколько карт вперед

    /// разведка на несколько карт, хз есть ли в heartstone такие карты
    public Card skipAndShowCard(int skip) {
        return deck[curIndex + skip];
    }

    public boolean isEmpty() {
        return curIndex == 30;
    }

    public void shuffle(Card[] playerDeck) {
        deck = playerDeck;
        for (int i = size - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            Card temp = deck[i];
            deck[i] = deck[index];
            deck[index] = temp;
        }
    }
}
