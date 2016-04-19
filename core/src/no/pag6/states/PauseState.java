package no.pag6.states;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class PauseState extends State {

    // Renderers
    private ShapeRenderer drawer;
    private TweenManager tweener;

    // Game objects

    // Game assets

    // Tween assets

    // Game UI
    private List<SimpleButton> pauseButtons = new ArrayList<SimpleButton>();
    private SimpleButton resumeButton;
    private SimpleButton highscoreButton;
    private SimpleButton optionsButton;
    private SimpleButton mainMenuButton;

    public PauseState(PAG6Game game) {
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

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render sprites
        game.spriteBatch.setProjectionMatrix(cam.combined);
        game.spriteBatch.begin();
        game.spriteBatch.enableBlending();

        super.render(delta);

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

        resumeButton.isTouchDown(screenX, screenY);
        highscoreButton.isTouchDown(screenX, screenY);
        optionsButton.isTouchDown(screenX, screenY);
        mainMenuButton.isTouchDown(screenX, screenY);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = cam.unproject(touchPoint);
        screenX = (int) projected.x;
        screenY = (int) projected.y;

        if (resumeButton.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().popScreen();
        } else if (highscoreButton.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().pushScreen(new HighscoreMenu(game));
        } else if (optionsButton.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().pushScreen(new OptionsMenu(game));
        } else if (mainMenuButton.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().popScreen();
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
        float uiScale;
        TextureRegion region;
        float regionWidth, regionHeight;

        // Buttons
        uiScale = 0.67f;

        region = AssetLoader.resumeButtonUp;
        regionWidth = region.getRegionWidth()*uiScale;
        regionHeight = region.getRegionHeight()*uiScale;
        resumeButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*8/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.resumeButtonUp, AssetLoader.resumeButtonDown
        );
        pauseButtons.add(resumeButton);

        region = AssetLoader.highscoreButtonUp;
        regionWidth = region.getRegionWidth()*uiScale;
        regionHeight = region.getRegionHeight()*uiScale;
        highscoreButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*6/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.highscoreButtonUp, AssetLoader.highscoreButtonDown
        );
        pauseButtons.add(highscoreButton);

        region = AssetLoader.optionsButtonUp;
        regionWidth = region.getRegionWidth()*uiScale;
        regionHeight = region.getRegionHeight()*uiScale;
        optionsButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*4/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.optionsButtonUp, AssetLoader.optionsButtonDown
        );
        pauseButtons.add(optionsButton);

        region = AssetLoader.mainMenuButtonUp;
        regionWidth = region.getRegionWidth()*uiScale;
        regionHeight = region.getRegionHeight()*uiScale;
        mainMenuButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*2/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.mainMenuButtonUp, AssetLoader.mainMenuButtonDown
        );
        pauseButtons.add(mainMenuButton);
    }

    private void drawUI() {
        for (SimpleButton button : pauseButtons) {
            button.draw(game.spriteBatch);
        }
    }

}
