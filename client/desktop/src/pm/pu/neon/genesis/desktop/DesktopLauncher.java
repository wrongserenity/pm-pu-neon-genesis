package pm.pu.neon.genesis.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import pm.pu.neon.genesis.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		cfg.title = "Your game";
		cfg.width = 1366;
		cfg.height = 768;
		cfg.fullscreen = true;
		new LwjglApplication(new MyGdxGame(), cfg);
	}
}
