package pm.pu.neon.genesis.cards;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private static int size = 30;
    private Random rand;
    @Expose public int curIndex = 0;
    @Expose public ArrayList<Integer> deck = new ArrayList<>();

    public Deck(){

    }

}
