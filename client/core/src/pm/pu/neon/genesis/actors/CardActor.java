package pm.pu.neon.genesis.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import pm.pu.neon.genesis.cards.Card;
import pm.pu.neon.genesis.managers.SocketManager;

import java.io.IOException;

public class CardActor extends Actor {

    public Texture texture;
    public int id;
    public int zIndex = 0;
    public static int mult = 8;
    public boolean hover;
    public float posX;
    public float posY;
    public float diff;
    public Image image;
    public Card cardSource;

    public CardActor(String texturePath, final String actorName, int x, int y, final int id, Card source) {
        cardSource = source;
        this.id = id;
        this.texture = new Texture(texturePath + ".png");
        setBounds(x - texture.getWidth() / mult / 2, y,
                texture.getWidth() / mult, texture.getHeight() / mult);
        setTouchable(Touchable.enabled);
        image = new Image(texture);
        image.setScale(1f / mult);
        diff = texture.getWidth() / mult / 2 - texture.getWidth() / 10;
        setDebug(true);
        addListener(new DragListener() {
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                setZIndex(100);
                image.setPosition(getX(), getY());
                image.setScale(1f / mult);
            }

            public void drag(InputEvent event, float x, float y, int pointer) {
                setZIndex(100);
                moveBy(x - getWidth() / 2, y - getHeight() / 2);
                image.moveBy(x - getWidth() / 2, y - getHeight() / 2);
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (getY() > posY + getHeight() / 2) {
                    System.out.println("dsac");
                    if (cardSource.id < 28) {
                        try {
                            SocketManager.getInctance().write("play creature " + cardSource.id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (cardSource.id < 37) {
                        try {
                            SocketManager.getInctance().write("play spell " + cardSource.id +
                                    " " + SocketManager.getInctance().enemy);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {
                            SocketManager.getInctance().write("play rule " + id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                setPosition(posX, posY);
                setZIndex(zIndex);
                hover = false;
            }
        });
        addListener(new ClickListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                setZIndex(100);
                image.setPosition(posX + diff, getY());
                image.setScale(0.2f);
                hover = true;
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                hover = false;
                setZIndex(zIndex);
                setPosition(posX, getY());
                image.setScale(1f / mult);
            }
        });
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        image.setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        if (!hover) setZIndex(zIndex);
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        image.draw(batch, 1);
    }
}