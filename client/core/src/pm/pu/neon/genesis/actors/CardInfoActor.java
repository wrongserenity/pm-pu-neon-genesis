package pm.pu.neon.genesis.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class CardInfoActor extends Actor {

    public Texture texture;
    public int id;
    public int zIndex = 0;
    public static int mult = 5;
    public boolean hover;
    public float posX;
    public float posY;
    public float diff;
    public CardInfoActor(String texturePath, final String actorName, int x, int y, int id) {
        this.id = id;
        this.texture = new Texture(texturePath);
        setBounds(x - texture.getWidth() / mult / 2, y,
                texture.getWidth() / mult, texture.getHeight() / mult);

        //setTouchable(Touchable.enabled);
        //diff = texture.getWidth() / mult / 2 - texture.getWidth() / 10;
        /*addListener(new DragListener() {
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                setZIndex(100);
                setWidth(texture.getWidth() / mult);
                setHeight(texture.getHeight() / mult);
            }
            public void drag(InputEvent event, float x, float y, int pointer) {
                setZIndex(100);
                moveBy(x - getWidth() / 2, y - getHeight() / 2);
            }
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                setPosition(posX, posY);
                setZIndex(zIndex);
                hover = false;
            }
        });
        addListener(new ClickListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                setZIndex(100);
                setPosition(posX + diff, getY());
                setWidth(texture.getWidth() / 5);
                setHeight(texture.getHeight() / 5);
                hover = true;
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                hover = false;
                setZIndex(zIndex);
                setPosition(posX, getY());
                setWidth(texture.getWidth() / mult);
                setHeight(texture.getHeight() / mult);
            }
        });*/
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}