package pm.pu.neon.genesis.managers;

import com.badlogic.gdx.Gdx;
import pm.pu.neon.genesis.actors.CreatureActor;
import pm.pu.neon.genesis.actors.EnemyCreatureActor;

import java.util.ArrayList;

public class BattleManager {
    public ArrayList<CreatureActor> creatures = new ArrayList<CreatureActor>();
    public ArrayList<EnemyCreatureActor> enemyCreatures = new ArrayList<EnemyCreatureActor>();
    public int halfDistance = 10;

    public void rearrange() {
        if(creatures.size() == 0) return;
        int startingPos = Gdx.graphics.getWidth() / 2 - (creatures.size() - 1) *
        (halfDistance + creatures.get(0).texture.getWidth() / CreatureActor.mult / 2)
        - creatures.get(0).texture.getWidth() / CreatureActor.mult / 2;
        for (int i = 0; i < creatures.size(); i++) {
            creatures.get(i).setZIndex(creatures.size() - i);
            creatures.get((i)).zIndex = creatures.size() - i;
            creatures.get(i).setPosition(startingPos,
                    Gdx.graphics.getHeight() / 2 - creatures.get(i).texture.getHeight() / CreatureActor.mult - 60);
            startingPos += 2 * halfDistance + creatures.get(0).texture.getWidth() / CreatureActor.mult;
        }
    }

    public void rearrangeEnemy() {
        if(enemyCreatures.size() == 0) return;
        int startingPos = Gdx.graphics.getWidth() / 2 - (enemyCreatures.size() - 1) *
                (halfDistance + enemyCreatures.get(0).texture.getWidth() / CreatureActor.mult / 2)
                - enemyCreatures.get(0).texture.getWidth() / CreatureActor.mult / 2;
        for (int i = 0; i < enemyCreatures.size(); i++) {
            enemyCreatures.get(i).setZIndex(enemyCreatures.size() - i);
            enemyCreatures.get((i)).zIndex = enemyCreatures.size() - i;
            enemyCreatures.get(i).setPosition(startingPos,
                    Gdx.graphics.getHeight() / 2 + 60);
            startingPos += 2 * halfDistance + enemyCreatures.get(0).texture.getWidth() / CreatureActor.mult;
        }
    }
}