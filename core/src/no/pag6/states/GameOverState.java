package no.pag6.states;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class GameOverState extends State {

    // Renderers
    private ShapeRenderer drawer;
    private TweenManager tweener;

    // Game objects

    // Game assets

    // Tween assets

    // Game UI
    private List<SimpleButton> gameOverButtons = new ArrayList<SimpleButton>();
    private SimpleButton highscoreButtonGameOver;
    private SimpleButton menuButtonGameOver;

    public GameOverState(PAG6Game game) {
        super(game);

        // Set up drawer and batcher
        drawer = new ShapeRenderer();
        drawer.setProjectionMatrix(cam.combined);

        // Init objects and assets
        initTweenAssets();

        initGameObjects();
        initGameAssets();

        initUI();
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render sprites
        game.spriteBatch.setProjectionMatrix(cam.combined);
        game.spriteBatch.begin();
        game.spriteBatch.enableBlending();

        drawUI();

        game.spriteBatch.end();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = cam.unproject(touchPoint);
        screenX = (int) projected.x;
        screenY = (int) projected.y;

        highscoreButtonGameOver.isTouchDown(screenX, screenY);
        menuButtonGameOver.isTouchDown(screenX, screenY);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = cam.unproject(touchPoint);
        screenX = (int) projected.x;
        screenY = (int) projected.y;

        if (highscoreButtonGameOver.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().setScreen(new HighscoreMenu(game));
        } else if (menuButtonGameOver.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().setScreen(new MainMenu(game));
        }

        return true;
    }

    private void initTweenAssets() {
        // Register Tween Assets

        tweener = new TweenManager();

        // Tween animations
    }

    private void initGameObjects() {
    }

    private void initGameAssets() {
    }

    private void initUI() {
        gameOverButtons = new ArrayList<SimpleButton>();
        highscoreButtonGameOver = new SimpleButton(2560/2 - AssetLoader.highscoreButtonUp.getRegionWidth()/2 + 500, 1200,
                AssetLoader.highscoreButtonUp.getRegionWidth(), AssetLoader.highscoreButtonUp.getRegionHeight(),
                AssetLoader.highscoreButtonUp, AssetLoader.highscoreButtonDown);
        gameOverButtons.add(highscoreButtonGameOver);
        menuButtonGameOver = new SimpleButton(2560/2 - AssetLoader.mainMenuButtonUp.getRegionWidth()/2, 1200,
                AssetLoader.mainMenuButtonUp.getRegionWidth(), AssetLoader.mainMenuButtonUp.getRegionHeight(),
                AssetLoader.mainMenuButtonUp, AssetLoader.mainMenuButtonDown);
        gameOverButtons.add(menuButtonGameOver);
    }

    private void drawUI() {
        for (SimpleButton button : gameOverButtons) {
            button.draw(game.spriteBatch);
        }
    }

}
