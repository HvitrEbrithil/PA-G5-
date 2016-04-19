package no.pag6.states;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
    private TweenManager tweener;

    // Game objects

    // Game assets

    // Tween assets
    private Value alpha = new Value();

    // Game UI
    private List<SimpleButton> mainMenuButtons = new ArrayList<SimpleButton>();
    private SimpleButton playButton;
    private SimpleButton highscoreButton;
    private SimpleButton optionsButton;
    private SimpleButton quitButton;
    private Sprite logo;

    public MainMenu(PAG6Game game) {
        super(game);

        // Init objects and assets
        initTweenAssets();

        initUI();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = cam.unproject(touchPoint);
        screenX = (int) projected.x;
        screenY = (int) projected.y;

        playButton.isTouchDown(screenX, screenY);
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

        if (playButton.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().pushScreen(new CharacterMenu(game));
        } else if (highscoreButton.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().pushScreen(new HighscoreMenu(game));
        } else if (optionsButton.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().pushScreen(new OptionsMenu(game));
        } else if (quitButton.isTouchUp(screenX, screenY)) {
            game.dispose();
            System.exit(0);
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

    private void initUI() {
        TextureRegion region;
        float regionWidth, regionHeight;

        // Buttons
        region = AssetLoader.playButtonUp;
        regionWidth = region.getRegionWidth()*UI_SCALE*1.5f;
        regionHeight = region.getRegionHeight()*UI_SCALE*1.5f;
        playButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*8/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.playButtonUp, AssetLoader.playButtonDown
        );
        mainMenuButtons.add(playButton);

        region = AssetLoader.highscoreButtonUp;
        regionWidth = region.getRegionWidth()*UI_SCALE;
        regionHeight = region.getRegionHeight()*UI_SCALE;
        highscoreButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*6/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.highscoreButtonUp, AssetLoader.highscoreButtonDown
        );
        mainMenuButtons.add(highscoreButton);

        region = AssetLoader.optionsButtonUp;
        regionWidth = region.getRegionWidth()*UI_SCALE;
        regionHeight = region.getRegionHeight()*UI_SCALE;
        optionsButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*4/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.optionsButtonUp, AssetLoader.optionsButtonDown
        );
        mainMenuButtons.add(optionsButton);

        region = AssetLoader.quitButtonUp;
        regionWidth = region.getRegionWidth()*UI_SCALE;
        regionHeight = region.getRegionHeight()*UI_SCALE;
        quitButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.quitButtonUp, AssetLoader.quitButtonDown
        );
        mainMenuButtons.add(quitButton);

        // Logo and copyright
        float tempUIScale = 0.04f;

        region = AssetLoader.logo;
        regionWidth = region.getRegionWidth()*tempUIScale;
        regionHeight = region.getRegionHeight()*tempUIScale;
        logo = new Sprite(region);
        logo.setSize(regionWidth, regionHeight);
        logo.setPosition(8, 2);
    }

    private void drawUI() {
        for (SimpleButton button : mainMenuButtons) {
            button.draw(game.spriteBatch);
        }

        logo.draw(game.spriteBatch);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 9;
        parameter.color = Color.BLACK;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        font.draw(game.spriteBatch, "COPYRIGHT 2016, PROG ARK GRUPPE 6", V_WIDTH/22, V_HEIGHT/26);
    }

    private void drawTransition(float delta) {
        tweener.update(delta);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        game.drawer.setProjectionMatrix(cam.combined);
        game.drawer.begin(ShapeRenderer.ShapeType.Filled);
        game.drawer.setColor(1, 1, 1, alpha.getValue());
        game.drawer.rect(0, 0, V_WIDTH, V_HEIGHT);
        game.drawer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

}
