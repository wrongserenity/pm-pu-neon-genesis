import redis.clients.jedis.Jedis;

public class Database {
    private static volatile Jedis instance;

    public static Jedis getInctance() {
        Jedis localInstance = instance;
        if (localInstance == null){
            synchronized (Database.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new Jedis();
                }
            }
        }
        return localInstance;
    }

    public static void init(Jedis jedis){
        instance = jedis;
    }
}
