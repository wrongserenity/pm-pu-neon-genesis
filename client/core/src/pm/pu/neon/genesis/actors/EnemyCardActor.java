package pm.pu.neon.genesis.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EnemyCardActor extends Actor {

    public Texture texture;
    public TextureRegion textureRegion;
    public int id;
    public int zIndex = 0;
    public static int mult = 8;

    public EnemyCardActor(String texturePath, final String actorName, int x, int y, int id) {
        this.id = id;
        this.texture = new Texture(texturePath);
        setOrigin(0, 0);
        setBounds(x - texture.getWidth() / mult / 2, y,
                texture.getWidth() / mult, texture.getHeight() / mult);
        setRotation(180);
        textureRegion = new TextureRegion(texture);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureRegion, getX(), getY(), 0, 0,
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}