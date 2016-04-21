package no.pag6.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.pag6.helpers.AssetLoader;
import no.pag6.helpers.Constants;
import no.pag6.helpers.GameStateManager;
import no.pag6.states.SplashScreen;

public class PAG6Game extends Game implements Constants {

    public static final String TAG = "PAG6Game";

    private float time = 0.0f;
    public ShapeRenderer drawer;
    public SpriteBatch spriteBatch;
    private GameStateManager gameStateManager;

    @Override
    public void create () {
        drawer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        gameStateManager = new GameStateManager(this);

        Gdx.app.log(TAG, "created");

        AssetLoader.load();

        gameStateManager.pushScreen(new SplashScreen(this));
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
