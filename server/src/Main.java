import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import redis.clients.jedis.Jedis;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {
        try (var server = new ServerSocket(41316)) {
            var clients = Executors.newFixedThreadPool(20);
            while (true) {
                Game game = new Game();
                /*
                сервер может обрабатывать несколько задач, поэтому необходимо использование потоков,
                жа, создаётся поток с игроком, так как игрок экземпляр runnable, который можно передавать в поток для его запуска
                */

                /*
                System.out.println("game created");
                Player player = new Player(server.accept(), game, true);
                System.out.println("player created");
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();
                String json = gson.toJson(player);
                System.out.println(json);
                String json2 = gson.toJson(player.battleground);
                System.out.println(json2);
                break;
                */

                // БД JEDIS
                Jedis jedis = new Jedis("localhost");
                System.out.println("Connection successful");
                Database.init(jedis);

                clients.execute(new Player(server.accept(), game, true));
                clients.execute(new Player(server.accept(), game, false));
            }
        }
    }
}


