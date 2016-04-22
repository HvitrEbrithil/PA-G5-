package no.pag6.states;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.g2d.Sprite;
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
    private Sprite pauseTitle;

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
        super.render(delta);

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
        projected = viewport.unproject(touchPoint);

        resumeButton.isTouchDown(projected.x, projected.y);
        highscoreButton.isTouchDown(projected.x, projected.y);
        optionsButton.isTouchDown(projected.x, projected.y);
        mainMenuButton.isTouchDown(projected.x, projected.y);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = viewport.unproject(touchPoint);

        if (resumeButton.isTouchUp(projected.x, projected.y)) {
            game.getGameStateManager().popScreen();
        } else if (highscoreButton.isTouchUp(projected.x, projected.y)) {
            game.getGameStateManager().pushScreen(new HighscoreMenu(game));
        } else if (optionsButton.isTouchUp(projected.x, projected.y)) {
            game.getGameStateManager().pushScreen(new OptionsMenu(game));
        } else if (mainMenuButton.isTouchUp(projected.x, projected.y)) {
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

        // Title
        region = AssetLoader.pauseTitle;
        regionWidth = region.getRegionWidth()*UI_SCALE;
        regionHeight = region.getRegionHeight()*UI_SCALE;
        pauseTitle = new Sprite(region);
        pauseTitle.setSize(regionWidth*UI_SCALE*1.1f, regionHeight*UI_SCALE*1.1f);
        pauseTitle.setPosition(V_WIDTH/2 - regionWidth/2, V_HEIGHT*20/24 - regionHeight/2);
    }

    private void drawUI() {
        for (SimpleButton button : pauseButtons) {
            button.draw(game.spriteBatch);
        }

        pauseTitle.draw(game.spriteBatch);
    }

}
