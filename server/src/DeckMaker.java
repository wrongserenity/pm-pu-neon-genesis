import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutionException;

public class DeckMaker {
    public static void main (String[] args)throws Exception{
        try{
            Jedis jedis = new Jedis();
            jedis.sadd("10", "0 2 3 2 0");
            jedis.sadd("11", "0 2 3 2 0");
            jedis.sadd("12", "0 2 3 2 0");
            jedis.sadd("13", "0 2 3 2 0");
            jedis.sadd("14", "0 3 3 3 0");
            jedis.sadd("15", "0 3 3 3 0");
            jedis.sadd("16", "0 2 3 3 0");
            jedis.sadd("17", "0 2 3 3 0");
            jedis.sadd("18", "0 4 2 4 0");
            jedis.sadd("19", "0 4 2 4 0");
            jedis.sadd("20", "0 5 4 4 0");
            jedis.sadd("21", "0 5 4 4 0");
            jedis.sadd("22", "0 5 3 4 0");
            jedis.sadd("23", "0 5 3 4 0");
            jedis.sadd("24", "0 6 3 4 0");
            jedis.sadd("25", "0 6 3 4 0");
            jedis.sadd("26", "0 7 6 6 0");
            jedis.sadd("27", "0 7 6 6 0");
            jedis.sadd("28", "1 0 2 1 0");
            jedis.sadd("29", "1 0 2 1 0");
            jedis.sadd("30", "1 0 2 1 0");
            jedis.sadd("31", "1 0 3 2 0");
            jedis.sadd("32", "1 0 3 2 0");
            jedis.sadd("33", "1 0 3 2 0");
            jedis.sadd("34", "1 0 3 2 0");
            jedis.sadd("35", "1 0 6 4 0");
            jedis.sadd("36", "1 0 6 4 0");
            jedis.sadd("37", "1 0 0 5 1");
            jedis.sadd("38", "2 0 0 5 2");
            jedis.sadd("39", "2 0 0 5 3");
            jedis.bgsave();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
