package no.pag6.views;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.pag6.controllers.MenuController;
import no.pag6.helpers.MenuInputHandler;
import no.pag6.helpers.tweenaccessors.Value;
import no.pag6.helpers.tweenaccessors.ValueAccessor;
import no.pag6.models.ui.SimpleButton;

import java.util.List;

public class MenuRenderer {

    private MenuController menuController;

    private OrthographicCamera cam;

    private ShapeRenderer drawer;
    private SpriteBatch batcher;
    private TweenManager tweener;

    // Game objects

    // Game assets

    // Tween assets
    private Value alpha = new Value();

    // Menu UI
    private List<SimpleButton> menuButtons;

    public MenuRenderer(MenuController menuController) {
        this.menuController = menuController;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 2560, 1440); // TODO: Must most likely be changed to take height or width as input

        drawer = new ShapeRenderer();
        drawer.setProjectionMatrix(cam.combined);
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        initTweenAssets();
        initGameObjects();
        initGameAssets();
        initMenuUI();
    }

    public void render(float delta) {
        // Set color of background to gray
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render shapes
        drawer.begin(ShapeRenderer.ShapeType.Filled);
        drawer.end();

        // Render sprites
        batcher.begin();
        batcher.enableBlending();

        switch (menuController.getCurrentMenuState()) {
            case MAIN_MENU:
                drawMenuUI();
                break;
            case OPTIONS_MENU:
                break;
            case HIGHSCORE_MENU:
                break;
            default:
                break;
        }

        batcher.end();

        drawTransition(delta);
    }

    private void initTweenAssets() {
        // Register Tween Assets
        Tween.registerAccessor(Value.class, new ValueAccessor());

        tweener = new TweenManager();

        // Tween animations
        Tween.to(alpha, -1, .5f)
                .target(0)
                .ease(TweenEquations.easeOutQuad)
                .start(tweener);
    }

    private void initGameObjects() {
    }

    private void initGameAssets() {
    }

    private void initMenuUI() {
        menuButtons = ((MenuInputHandler) Gdx.input.getInputProcessor()).getMenuButtons();
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            tweener.update(delta);

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            drawer.begin(ShapeRenderer.ShapeType.Filled);
            drawer.setColor(1, 1, 1, alpha.getValue());
            drawer.rect(0, 0, 2560, 1440);
            drawer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    private void drawMenuUI() {
        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }
    }

}
