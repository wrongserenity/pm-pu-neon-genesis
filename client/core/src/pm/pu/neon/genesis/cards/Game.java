package pm.pu.neon.genesis.cards;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private Random rand;
    @Expose public int turn = 0;
    @Expose public Player player1;
    @Expose public Player player2;
    @Expose public int gameRule = 0;
    public ArrayList<Player> players = new ArrayList<>();
    @Expose public boolean[] isHappend;

    public Game(){
    }

}
