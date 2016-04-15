package no.pag6.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.pag6.helpers.AssetLoader;
import no.pag6.states.PlayState;
import no.pag6.states.SplashScreen;
import no.pag6.states.State;

import java.util.Stack;

public class PAG6Game extends Game {

    public static final String TAG = "PAG6Game";
	public static final int V_WIDTH = 800, V_HEIGHT = 480;
	public static final float PPM = 100;
	public static float TIME_STEP = 1/60f;

	public SpriteBatch spriteBatch;
	public Stack<State> gameStack;

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();

        Gdx.app.log(TAG, "created");

        AssetLoader.load();

		gameStack = new Stack<State>();
		SplashScreen splashScreen = new SplashScreen(this);
		gameStack.push(splashScreen);
        setScreen(new PlayState(this, 2, "test_lvl.tmx"));
    }

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

	@Override
	public void render() {
		super.render();
	}
}
