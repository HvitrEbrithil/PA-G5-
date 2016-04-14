package no.pag6.states;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class GameOverState extends State {
    private PAG6Game game;

    // Camera and viewport
    private OrthographicCamera cam;
    private Viewport viewPort;
    //    private int V_WIDTH = 800, V_HEIGHT = 480;
    private int V_WIDTH = 2560, V_HEIGHT = 1440;
    private float PPM = 100;

    // Renderers
    private ShapeRenderer drawer;
    private SpriteBatch batcher;
    private TweenManager tweener;

    // Game objects

    // Game assets

    // Tween assets

    // Game UI
    private List<SimpleButton> gameOverButtons = new ArrayList<SimpleButton>();
    private SimpleButton highscoreButtonGameOver;
    private SimpleButton menuButtonGameOver;

    public GameOverState(PAG6Game game) {
        this.game = game;
        Gdx.input.setInputProcessor(this);

        // Set up camera
        cam = new OrthographicCamera();
//        viewPort = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM, cam);
//        cam.setToOrtho(true, viewPort.getScreenWidth(), viewPort.getScreenHeight());
        cam.setToOrtho(true, V_WIDTH, V_HEIGHT);

        // Set up drawer and batcher
        drawer = new ShapeRenderer();
        drawer.setProjectionMatrix(cam.combined);
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        // Init objects and assets
        initTweenAssets();

        initGameObjects();
        initGameAssets();

        initUI();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        // Render sprites
        batcher.begin();
        batcher.enableBlending();

        drawUI();

        batcher.end();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        highscoreButtonGameOver.isTouchDown(screenX, screenY);
        menuButtonGameOver.isTouchDown(screenX, screenY);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (highscoreButtonGameOver.isTouchUp(screenX, screenY)) {
            game.gameStack.pop();
            game.gameStack.pop();
            HighscoreMenu highscoreMenu = new HighscoreMenu(game);
            game.gameStack.push(highscoreMenu);
            game.setScreen(highscoreMenu);
        } else if (menuButtonGameOver.isTouchUp(screenX, screenY)) {
            goBackToPreviousPreviousState(game);
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
            button.draw(batcher);
        }
    }

}
