package no.pag6.states;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.tweenaccessors.Value;
import no.pag6.tweenaccessors.ValueAccessor;
import no.pag6.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends State {

    // Renderers
    private ShapeRenderer drawer;
    private TweenManager tweener;

    // Game objects

    // Game assets

    // Tween assets
    private Value alpha = new Value();

    // Game UI
    private List<SimpleButton> mainMenuButtons = new ArrayList<SimpleButton>();
    private SimpleButton playSPButton;
    private SimpleButton play2PButton;
    private SimpleButton highscoreButton;
    private SimpleButton optionsButton;
    private SimpleButton quitButton;

    public MainMenu(PAG6Game game) {
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

        Gdx.gl.glClearColor(1.0f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render sprites
        game.spriteBatch.setProjectionMatrix(cam.combined);
        game.spriteBatch.begin();
        game.spriteBatch.enableBlending();

        drawUI();

        game.spriteBatch.end();

        drawTransition(delta);
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

        playSPButton.isTouchDown(screenX, screenY);
        play2PButton.isTouchDown(screenX, screenY);
        highscoreButton.isTouchDown(screenX, screenY);
        optionsButton.isTouchDown(screenX, screenY);
        quitButton.isTouchDown(screenX, screenY);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = cam.unproject(touchPoint);
        screenX = (int) projected.x;
        screenY = (int) projected.y;

        if (playSPButton.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().pushScreen(new PlayState(game, 1, "test_lvl.tmx"));
        } else if (play2PButton.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().pushScreen(new PlayState(game, 2, "test_lvl.tmx"));
        } else if (highscoreButton.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().pushScreen(new HighscoreMenu(game));
        } else if (optionsButton.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().pushScreen(new OptionsMenu(game));
        } else if (quitButton.isTouchUp(screenX, screenY)) {
            game.dispose();
        }

        return true;
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

    private void initUI() {
        playSPButton = new SimpleButton(2560/2 - AssetLoader.playSPButtonUp.getRegionWidth()/2, 300,
                AssetLoader.playSPButtonUp.getRegionWidth(), AssetLoader.playSPButtonUp.getRegionHeight(),
                AssetLoader.playSPButtonUp, AssetLoader.playSPButtonDown);
        mainMenuButtons.add(playSPButton);
        play2PButton = new SimpleButton(2560/2 - AssetLoader.play2PButtonUp.getRegionWidth()/2, 500,
                AssetLoader.play2PButtonUp.getRegionWidth(), AssetLoader.play2PButtonUp.getRegionHeight(),
                AssetLoader.play2PButtonUp, AssetLoader.play2PButtonDown);
        mainMenuButtons.add(play2PButton);
        highscoreButton = new SimpleButton(2560/2 - AssetLoader.highscoreButtonUp.getRegionWidth()/2 - 400, 700,
                AssetLoader.highscoreButtonUp.getRegionWidth(), AssetLoader.highscoreButtonUp.getRegionHeight(),
                AssetLoader.highscoreButtonUp, AssetLoader.highscoreButtonDown);
        mainMenuButtons.add(highscoreButton);
        optionsButton = new SimpleButton(2560/2 - AssetLoader.optionsButtonUp.getRegionWidth()/2 + 400, 700,
                AssetLoader.optionsButtonUp.getRegionWidth(), AssetLoader.optionsButtonUp.getRegionHeight(),
                AssetLoader.optionsButtonUp, AssetLoader.optionsButtonDown);
        mainMenuButtons.add(optionsButton);
        quitButton = new SimpleButton(2560/2 - AssetLoader.exitButtonUp.getRegionWidth()/2, 1000,
                AssetLoader.exitButtonUp.getRegionWidth(), AssetLoader.exitButtonUp.getRegionHeight(),
                AssetLoader.exitButtonUp, AssetLoader.exitButtonDown);
        mainMenuButtons.add(quitButton);
    }

    private void drawUI() {
        for (SimpleButton button : mainMenuButtons) {
            button.draw(game.spriteBatch);
        }
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            tweener.update(delta);

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            drawer.begin(ShapeRenderer.ShapeType.Filled);
            drawer.setColor(1, 1, 1, alpha.getValue());
            drawer.rect(0, 0, A_WIDTH, A_HEIGHT);
            drawer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

}
