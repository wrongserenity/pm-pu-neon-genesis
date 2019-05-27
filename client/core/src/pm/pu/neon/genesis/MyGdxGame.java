package pm.pu.neon.genesis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import pm.pu.neon.genesis.actors.*;
import pm.pu.neon.genesis.cards.Card;
import pm.pu.neon.genesis.managers.BattleManager;
import pm.pu.neon.genesis.managers.HandManager;
import pm.pu.neon.genesis.managers.SocketManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static pm.pu.neon.genesis.managers.SocketManager.getInctance;

public class MyGdxGame extends ApplicationAdapter {
	private Stage stage;
	private Table table;
	Texture img;
	CardActor actor;
	Image backgroundImage;
	Skin skin;
	public int first = 1;
	public int second = 0;
	@Override
	public void create () {
		try {
			stage = new Stage(new ScreenViewport());
			Gdx.input.setInputProcessor(stage);

			SocketManager.getInctance().write("Hello");
			while (true) {
				if (SocketManager.getInctance().data.size() > 0) {
					if (getInctance().data.get(0).equals("true")) {
						first = 0;
						second = 1;
					}
					break;
				}
			}
			while (true) {
				if (SocketManager.getInctance().data.size() > 1) {
					SocketManager.getInctance().read();
					break;
				}
			}

			Gdx.input.setInputProcessor(stage);
			skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
			HandManager hd = new HandManager();
			BattleManager bt = new BattleManager();
			for (int i = 0; i < SocketManager.getInctance().game.players.get(first).hand.size(); i++) {
				Card card = SocketManager.getInctance().game.players.get(first).hand.get(i);
				CardActor actor = new CardActor(getInctance().picture.get(card.id), "fd", 800, 0, i, card);
				stage.addActor(actor);
				hd.actors.add(actor);
			}
			for (int i = 0; i < SocketManager.getInctance().game.players.get(second).hand.size(); i++) {
				EnemyCardActor actor =
						new EnemyCardActor("cards/card shirt spiral.png", "fd", 800, 0, i);
				stage.addActor(actor);
				hd.enemyActors.add(actor);
			}
			for (int i = 0; i < getInctance().game.players.get(first).battleground.size(); i++) {
				Card creature = SocketManager.getInctance().game.players.get(first).hand.get(i);
				CreatureActor vActor =
						new CreatureActor(getInctance().picture.get(creature.id), "fd", 800, 0, i);
				stage.addActor(vActor);
				bt.creatures.add(vActor);
			}
			for (int i = 0; i < getInctance().game.players.get(second).battleground.size(); i++) {
				Card creature = SocketManager.getInctance().game.players.get(second).hand.get(i);
				EnemyCreatureActor vActor =
						new EnemyCreatureActor(getInctance().picture.get(creature.id), "fd", 800, 0, i);
				stage.addActor(vActor);
				bt.enemyCreatures.add(vActor);
			}

			PlayerActor player = new PlayerActor("cards/никита-сверху.png", "pl",
					second, true, skin, stage);
			PlayerActor player2 = new PlayerActor("cards/никита-снизу.png", "p2",
					first, false, skin, stage);
			stage.addActor(player);
			stage.addActor(player2);

			bt.rearrange();
			bt.rearrangeEnemy();
			hd.rearrangeDeg();
			hd.rearrangeEnemyDeg();

			Image backgroundImage = new Image(new Texture("cards/background.png"));
			backgroundImage.setBounds(0, 0, 1366, 768);
			stage.addActor(backgroundImage);
			backgroundImage.setZIndex(0);
		}catch (InterruptedException | IOException e){
			e.printStackTrace();
		}
	}
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render () {
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			try {
				SocketManager.getInctance().write("next");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			try {
				SocketManager.getInctance().write("quit");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
	}
}