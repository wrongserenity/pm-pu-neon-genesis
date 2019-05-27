package pm.pu.neon.genesis.managers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pm.pu.neon.genesis.cards.Card;
import pm.pu.neon.genesis.cards.Game;
import pm.pu.neon.genesis.cards.Player;

import java.util.ArrayList;

public class Deserialize {
    public static Game getGame(String string) {
        Game game = new Game();
        JsonReader json = new JsonReader();
        JsonValue base = json.parse(string);
        Json json1 = new Json();
        game.gameRule = base.getInt("gameRule");
        game.turn = base.getInt("turn");
        game.isHappend = base.get("isHappend").asBooleanArray();
        JsonValue.JsonIterator iter = base.get("players").iterator();
        game.players = new ArrayList<>();
        while(iter.hasNext())
        {
            Player player = new Player();
            JsonValue playerJSON = iter.next();
            System.out.println(playerJSON.toString());
            player.first = playerJSON.getBoolean("first");
            player.name = playerJSON.getString("name");
            player.health = playerJSON.getInt("health");
            player.mana = playerJSON.getInt("mana");
            player.maxMana = playerJSON.getInt("maxMana");
            player.indexHappend = playerJSON.getInt("indexHappend");
            player.winner = playerJSON.getBoolean("winner");
            JsonValue.JsonIterator iter1 = playerJSON.get("hand").iterator();
            player.hand = new ArrayList<Card>();
            while(iter1.hasNext()) {
                JsonValue cardJSON = iter1.next();
                Card card = new Card();
                card.attack = cardJSON.getInt("attack");
                card.health = cardJSON.getInt("health");
                card.avgHealth = cardJSON.getInt("avgHealth");
                card.cost = cardJSON.getInt("cost");
                card.id = cardJSON.getInt("id");
                card.moved = cardJSON.getBoolean("moved");
                card.gameRule = cardJSON.getInt("gameRule");
                player.hand.add(card);
            }
            JsonValue.JsonIterator iter2 = playerJSON.get("battleground").iterator();
            player.battleground = new ArrayList<Card>();
            while(iter1.hasNext()) {
                JsonValue cardJSON = iter1.next();
                Card card = new Card();
                card.attack = cardJSON.getInt("attack");
                card.health = cardJSON.getInt("health");
                card.avgHealth = cardJSON.getInt("avgHealth");
                card.cost = cardJSON.getInt("cost");
                card.id = cardJSON.getInt("id");
                card.moved = cardJSON.getBoolean("moved");
                card.gameRule = cardJSON.getInt("gameRule");
                player.hand.add(card);
            }
            game.players.add(player);
        }

        return game;
    }
}