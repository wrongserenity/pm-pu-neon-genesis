package pm.pu.neon.genesis.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import pm.pu.neon.genesis.managers.SocketManager;

import java.io.IOException;

public class CreatureActor extends Actor {

    public Texture texture;
    public int id;
    public int zIndex = 0;
    public static int mult = 6;
    public boolean hover;
    public float posX;
    public float posY;
    public boolean dragged = true;
    public Image cardInfoActor;
    public Image cursor;

    public CreatureActor(String texturePath, final String actorName, int x, int y, int id) {
        this.id = id;
        this.texture = new Texture(texturePath + "(2).png");
        cardInfoActor = new Image(new Texture(texturePath + ".png"));
        cardInfoActor.setPosition(10000, 10000);
        cardInfoActor.setScale(0.2f,0.2f);
        cardInfoActor.setZIndex(101);

        cursor = new Image(new Texture("cards/cursor.png"));
        cursor.setPosition(10000, 10000);
        cursor.setOrigin(0,0);
        cursor.setZIndex(102);

        setBounds(x - texture.getWidth() / mult / 2, y,
                texture.getWidth() / mult, texture.getHeight() / mult);
        setTouchable(Touchable.enabled);

        addListener(new DragListener() {
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                try {
                    SocketManager.getInctance().isAttacking = true;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cursor.setPosition(getX() - cursor.getWidth() / 2, getY() - cursor.getHeight() / 2);
            }
            public void drag(InputEvent event, float x, float y, int pointer) {
                cursor.moveBy(getX() + x - cursor.getX() - cursor.getWidth() / 2,
                        getY() + y - cursor.getY() - cursor.getHeight() / 2);
            }
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                try {
                    SocketManager.getInctance().isAttacking = false;
                    if (SocketManager.getInctance().enemy != -1) SocketManager.getInctance().write("attack " +
                            getId() + " " + SocketManager.getInctance().enemy);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cursor.setPosition(10000, 10000);
            }
        });
        addListener(new ClickListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                cardInfoActor.setZIndex(100);
                cardInfoActor.setPosition(getX() + 125, getY());
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                cardInfoActor.setZIndex(0);
                cardInfoActor.setPosition(10000, 10000);
            }
        });
    }

    public int getId() {
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
        cursor.draw(batch, 1);
    }
}