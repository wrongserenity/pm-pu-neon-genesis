package pm.pu.neon.genesis.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import pm.pu.neon.genesis.managers.SocketManager;

import java.io.IOException;

public class EnemyCreatureActor extends Actor {

    public Texture texture;
    public int id;
    public int zIndex = 0;
    public static int mult = 6;
    public boolean hover;
    public float posX;
    public float posY;
    public Image cardInfoActor;

    public EnemyCreatureActor(String texturePath, final String actorName, int x, int y, final int id) {
        this.id = id;
        this.texture = new Texture(texturePath + "(2).png");
        cardInfoActor = new Image(new Texture(texturePath + ".png"));
        cardInfoActor.setPosition(10000, 10000);
        cardInfoActor.setScale(0.2f,0.2f);
        cardInfoActor.setZIndex(101);
        setBounds(x - texture.getWidth() / mult / 2, y,
                texture.getWidth() / mult, texture.getHeight() / mult);
        setTouchable(Touchable.enabled);

        addListener(new ClickListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                try {
                    if(SocketManager.getInctance().isAttacking){
                        SocketManager.getInctance().enemy = id;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cardInfoActor.setZIndex(100);
                cardInfoActor.setPosition(getX() + 125, getY());
                hover = true;
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                try {
                    if(SocketManager.getInctance().isAttacking){
                        SocketManager.getInctance().enemy = -1;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hover = false;
                cardInfoActor.setZIndex(0);
                cardInfoActor.setPosition(10000, 10000);
            }
        });
    }

    public int getId(){
        return id;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        cardInfoActor.draw(batch, 1);
    }
}