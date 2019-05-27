package pm.pu.neon.genesis.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pm.pu.neon.genesis.cards.Player;
import pm.pu.neon.genesis.managers.SocketManager;

import java.io.IOException;

public class PlayerActor extends Actor {

    public Texture texture;
    public int id;
    public static float mult = 2.75f;
    public float y = 2;
    public Label label1;
    public Label label2;
    Player player;

    public PlayerActor(String texturePath, final String actorName, int first, boolean enemy, Skin skin, Stage stage) {
        try {
            player = SocketManager.getInctance().game.players.get(first);
            this.id = id;
            this.texture = new Texture(texturePath);
            setOrigin(0, 0);
            float x = Gdx.graphics.getWidth() / 2 - texture.getWidth() / mult / 2;
            if (enemy) {
                x += 1;
                y = Gdx.graphics.getHeight() - texture.getHeight() / mult - 2;
            }
            setBounds(x, y,
                    texture.getWidth() / mult, texture.getHeight() / mult);
            setZIndex(25);
            if (enemy) {
                y = Gdx.graphics.getHeight() - getHeight() / 2;
            }
            label1 = new Label(player.mana + " / " + player.maxMana, skin);
            label1.setFontScale(2);
            label1.setPosition(Gdx.graphics.getWidth() / 2 - 365, y + 8);
            label1.setColor(0, 0, 1, 1);
            label1.setZIndex(100);
            stage.addActor(label1);
            label2 = new Label(player.health + "", skin);
            label2.setFontScale(2);
            label2.setPosition(Gdx.graphics.getWidth() / 2 + 325, y + 8);
            label2.setColor(1, 0, 0, 1);
            label2.setZIndex(100);
            stage.addActor(label2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
