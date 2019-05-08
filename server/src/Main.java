import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
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
                 
                clients.execute(new Player(server.accept(), game, true));
                clients.execute(new Player(server.accept(), game, false));
            }
        }
    }
}


