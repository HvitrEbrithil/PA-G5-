package no.pag6.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.pag6.helpers.AssetLoader;
import no.pag6.helpers.GameStateManager;
import no.pag6.states.PlayState;
import no.pag6.states.SplashScreen;
import no.pag6.states.State;

import java.util.Stack;

public class PAG6Game extends Game {

    public static final String TAG = "PAG6Game";

	public SpriteBatch spriteBatch;
	private GameStateManager gameStateManager;

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		gameStateManager = new GameStateManager(this);

        Gdx.app.log(TAG, "created");

        AssetLoader.load();

		gameStateManager.pushScreen(new PlayState(this, 1, "maptest2.tmx"));
    }

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

	public GameStateManager getGameStateManager() {
		return gameStateManager;
	}

	@Override
	public void render() {
		super.render();
	}
}
