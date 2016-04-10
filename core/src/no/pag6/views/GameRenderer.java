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

        initTweenAssets();
        initGameObjects();
        initGameAssets();
    }

    // TODO: Test-code, to be removed
    float x = 10;
    float y = 150;
    // TODO: Test-code, to be removed

    public void render(float delta) {
        // Set color of background to light blue
        Gdx.gl.glClearColor(0, 0, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render shapes
        drawer.begin(ShapeRenderer.ShapeType.Filled);
        // TODO: Test-code, to be removed
        drawer.setColor(1, 0, 0, 1);
        drawer.rect(x++, ++y, 200, 150);
        drawer.setColor(0.2f, 0.6f, 0, 1);
        drawer.rect(x++, 500 + 2*y, 50, 50);
        drawer.setColor(0.2f, 0.2f, 0.8f, 1);
        drawer.rect(x, 100 + 5*y, 350, 250);
        // TODO: Test-code, to be removed
        drawer.end();

        // Render sprites
        batcher.begin();
        batcher.enableBlending();

        switch (gameController.getCurrentGameState()) {
            case RUNNING:
                break;
            case PAUSED:
                break;
            case GAME_OVER:
                break;
            default:
                break;
        }

        batcher.end();
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

}
