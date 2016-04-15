package no.pag6.states;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

        // Set up drawer
        drawer = new ShapeRenderer();

        // Init objects and assets
        initTweenAssets();

        initGameObjects();
        initGameAssets();

        initUI();
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1.0f, 0.4f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render sprites
        game.spriteBatch.setProjectionMatrix(cam.combined);
        game.spriteBatch.begin();
        game.spriteBatch.enableBlending();

        drawUI();

        game.spriteBatch.end();

        if (alpha.getValue() > 0) {
            drawTransition(delta);
        }
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = cam.unproject(touchPoint);
        screenX = (int) touchPoint.x;
        screenY = (int) touchPoint.y;

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
        screenX = (int) touchPoint.x;
        screenY = (int) touchPoint.y;

        if (playSPButton.isTouchUp(screenX, screenY)) {
            PlayState playState = new PlayState(game, 1, "test_lvl.tmx");
            game.gameStack.push(playState);
            game.setScreen(playState);
        } else if (play2PButton.isTouchUp(screenX, screenY)) {
            PlayState playState = new PlayState(game, 2, "test_lvl.tmx");
            game.gameStack.push(playState);
            game.setScreen(playState);
        } else if (highscoreButton.isTouchUp(screenX, screenY)) {
            HighscoreMenu highscoreMenu = new HighscoreMenu(game);
            game.gameStack.push(highscoreMenu);
            game.setScreen(highscoreMenu);
        } else if (optionsButton.isTouchUp(screenX, screenY)) {
            OptionsMenu optionsMenu = new OptionsMenu(game);
            game.gameStack.push(optionsMenu);
            game.setScreen(optionsMenu);
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
        float uiScale = 0.4f;
        TextureRegion region;
        float regionWidth, regionHeight;

        region = AssetLoader.playSPButtonUp;
        regionWidth = region.getRegionWidth()*uiScale;
        regionHeight = region.getRegionHeight()*uiScale;
        playSPButton = new SimpleButton(
                V_WIDTH/3 - regionWidth/2, V_HEIGHT*8/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.playSPButtonUp, AssetLoader.playSPButtonDown
        );
        mainMenuButtons.add(playSPButton);

        region = AssetLoader.play2PButtonUp;
        regionWidth = region.getRegionWidth()*uiScale;
        regionHeight = region.getRegionHeight()*uiScale;
        play2PButton = new SimpleButton(
                V_WIDTH*2/3 - regionWidth/2, V_HEIGHT*8/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.play2PButtonUp, AssetLoader.play2PButtonDown
        );
        mainMenuButtons.add(play2PButton);

        region = AssetLoader.highscoreButtonUp;
        regionWidth = region.getRegionWidth()*uiScale;
        regionHeight = region.getRegionHeight()*uiScale;
        highscoreButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*6/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.highscoreButtonUp, AssetLoader.highscoreButtonDown
        );
        mainMenuButtons.add(highscoreButton);

        region = AssetLoader.optionsButtonUp;
        regionWidth = region.getRegionWidth()*uiScale;
        regionHeight = region.getRegionHeight()*uiScale;
        optionsButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*4/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.optionsButtonUp, AssetLoader.optionsButtonDown
        );
        mainMenuButtons.add(optionsButton);

        region = AssetLoader.exitButtonUp;
        regionWidth = region.getRegionWidth()*uiScale;
        regionHeight = region.getRegionHeight()*uiScale;
        quitButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.exitButtonUp, AssetLoader.exitButtonDown
        );
        mainMenuButtons.add(quitButton);
    }

    private void drawUI() {
        for (SimpleButton button : mainMenuButtons) {
            button.draw(game.spriteBatch);
        }
    }

    private void drawTransition(float delta) {
        tweener.update(delta);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        drawer.setProjectionMatrix(cam.combined);
        drawer.begin(ShapeRenderer.ShapeType.Filled);
        drawer.setColor(1, 1, 1, alpha.getValue());
        drawer.rect(0, 0, A_WIDTH, A_HEIGHT);
        drawer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

}
