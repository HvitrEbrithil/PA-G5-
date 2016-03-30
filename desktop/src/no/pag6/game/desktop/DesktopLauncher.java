package no.pag6.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import no.pag6.game.PAG6Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "PAG6 Game";
		config.height = 1080/2;
		config.width = 1920/2;
		new LwjglApplication(new PAG6Game(), config);
	}
}
