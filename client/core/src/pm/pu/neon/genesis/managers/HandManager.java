package pm.pu.neon.genesis.managers;

import com.badlogic.gdx.Gdx;
import pm.pu.neon.genesis.actors.CardActor;
import pm.pu.neon.genesis.actors.EnemyCardActor;

import java.util.ArrayList;

public class HandManager {
    public ArrayList<CardActor> actors = new ArrayList<CardActor>();
    public ArrayList<EnemyCardActor> enemyActors = new ArrayList<EnemyCardActor>();
    public int halfDistance = 40;
    public void rearrange(){
        int startingPos = -(actors.size() / 2) * 2 * halfDistance
                - actors.get(0).texture.getWidth() / CardActor.mult / 2;
        if(actors.size() % 2 == 0) startingPos -= halfDistance;
        for (int i = 0; i < actors.size(); i++) {
            actors.get(i).setZIndex(actors.size() - i);
            actors.get((i)).zIndex = actors.size() - i;
            actors.get(i).setPosition(Gdx.graphics.getWidth()/2 + startingPos,0);
            startingPos += 2 * halfDistance;
        }
    }

    public void rearrangeEnemyDeg(){
        for (int i = 0; i < enemyActors.size(); i++) {
            int startingAngle = (i + 1) * 180 / (enemyActors.size() + 1);
            enemyActors.get(i).setZIndex(i + 1);
            enemyActors.get((i)).zIndex = i + 1;
            float posX = Gdx.graphics.getWidth() / 2
                    + enemyActors.get(i).texture.getWidth() / EnemyCardActor.mult / 2
                    - (float)Math.cos(Math.toRadians(startingAngle)) * 150;
            float posY = (float)Math.sin(Math.toRadians(startingAngle)) * 50 - 38;
            enemyActors.get(i).setPosition(posX, Gdx.graphics.getHeight()
                    - posY);
        }
    }

    public void rearrangeDeg(){
        for (int i = 0; i < actors.size(); i++) {
            int startingAngle = (i + 1) * 180 / (actors.size() + 1);
            actors.get(i).setZIndex(actors.size() - i);
            actors.get((i)).zIndex = actors.size() - i;
            actors.get(i).posX = Gdx.graphics.getWidth() / 2
                    - actors.get(i).texture.getWidth() / CardActor.mult / 2
                    - (float)Math.cos(Math.toRadians(startingAngle)) * 150;
            actors.get(i).posY = (float)Math.sin(Math.toRadians(startingAngle)) * 50 - 38;
            actors.get(i).setPosition(actors.get(i).posX,
                    actors.get(i).posY);
        }
    }

}
