package pm.pu.neon.genesis.managers;

import com.google.gson.*;
import pm.pu.neon.genesis.cards.Game;

import java.lang.reflect.Type;

public class GameDeserializer implements JsonDeserializer<Game>
{
    @Override
    public Game deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        JsonObject jsonObject = json.getAsJsonObject();

        Game game = new Game();
        game.gameRule = jsonObject.get("turn").getAsInt();
        game.gameRule = jsonObject.get("gameRule").getAsInt();
        game.isHappend = gson.fromJson(jsonObject.get("isHappend").getAsJsonArray().toString(), boolean[].class);
        return game;
    }
}