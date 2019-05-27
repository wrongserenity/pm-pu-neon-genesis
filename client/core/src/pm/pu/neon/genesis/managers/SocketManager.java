package pm.pu.neon.genesis.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pm.pu.neon.genesis.cards.Card;
import pm.pu.neon.genesis.cards.Game;
import pm.pu.neon.genesis.cards.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SocketManager {
    private static volatile SocketManager instance;
    public Game game;
    public Map<Integer, String> picture = new HashMap<>();

    public static SocketManager getInctance() throws IOException, InterruptedException {
        SocketManager localInstance = instance;
        if (localInstance == null) {
            synchronized (SocketManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SocketManager();
                }
            }
        }
        return localInstance;
    }

    public boolean isAttacking = false;
    public int enemy;
    BufferedReader input;
    PrintWriter output;
    Thread t;
    public volatile ArrayList<String> data = new ArrayList<String>();
    public SocketManager() throws IOException, InterruptedException {
        picture.put(10, "cards/10-11");
        picture.put(11, "cards/10-11");
        picture.put(12, "cards/12-13");
        picture.put(13, "cards/12-13");
        picture.put(14, "cards/14-15");
        picture.put(15, "cards/14-15");
        picture.put(16, "cards/16-17");
        picture.put(17, "cards/16-17");
        picture.put(18, "cards/18-19");
        picture.put(19, "cards/18-19");
        picture.put(20, "cards/20-21");
        picture.put(21, "cards/20-21");
        picture.put(22, "cards/22-23");
        picture.put(23, "cards/22-23");
        picture.put(24, "cards/24-25");
        picture.put(25, "cards/24-25");
        picture.put(26, "cards/26-27");
        picture.put(27, "cards/26-27");
        picture.put(28, "cards/28-30");
        picture.put(29, "cards/28-30");
        picture.put(30, "cards/28-30");
        picture.put(31, "cards/31-34");
        picture.put(32, "cards/31-34");
        picture.put(33, "cards/31-34");
        picture.put(34, "cards/31-34");
        picture.put(35, "cards/35-36");
        picture.put(36, "cards/35-36");
        picture.put(37, "cards/37");
        picture.put(38, "cards/38");
        picture.put(39, "cards/39");
        SocketHints hints = new SocketHints();
        Socket client = Gdx.net.newClientSocket(Net.Protocol.TCP, "192.168.0.106", 41316, hints);
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(client.getOutputStream(), true);
        Thread t = new Thread(new getMessageThread(input, data));
        t.start();
        instance = this;
    }

    public void allUpdate(){

    }

    public void write(String message) {
        output.println(message);
    }

    public void read(){
        if (data.size() > 0){
            game = Deserialize.getGame(data.get(1));
            data.remove(0);
            data.remove(0);
        }
    }
}
class getMessageThread implements Runnable {
    public volatile BufferedReader input;
    public String line;
    public volatile ArrayList<String> data;
    public getMessageThread(BufferedReader input, ArrayList<String> data){
        this.input = input;
        this.data = data;
    }
    @Override
    public synchronized void run() {
        try {
            while ((line = input.readLine()) != null) {
                data.add(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}