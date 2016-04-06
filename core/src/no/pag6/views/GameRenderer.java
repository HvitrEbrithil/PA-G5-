package no.pag6.views;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.pag6.controllers.GameController;

public class GameRenderer {

    private GameController gameController;

    private OrthographicCamera cam;

    private ShapeRenderer drawer;
    private SpriteBatch batcher;
    private TweenManager tweener;

    // Game objects

    // Game assets

    // Tween assets

    public GameRenderer(GameController gameController) {
        this.gameController = gameController;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 2560, 1440); // TODO: Must most likely be changed to take height or width as input

        drawer = new ShapeRenderer();
        drawer.setProjectionMatrix(cam.combined);
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        setupTweenAssets();

        initGameObjects();
        initGameAssets();
    }

    public void render(float delta) {
        // Set color of background to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render shapes
        drawer.begin();
        drawer.end();

        // Render sprites
        batcher.begin();
        batcher.end();
    }

    private void setupTweenAssets() {
        // Register Tween Assets

        tweener = new TweenManager();

        // Tween animations
    }

    private void initGameObjects() {
    }

    private void initGameAssets() {
    }

}
