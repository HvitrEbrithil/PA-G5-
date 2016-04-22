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
    private BitmapFont font;

    // Tween assets
    private Value alpha = new Value();

    // Game UI
    private List<SimpleButton> mainMenuButtons = new ArrayList<SimpleButton>();
    private SimpleButton playButton;
    private SimpleButton highscoresButton;
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
    public void dispose() {
        super.dispose();

        font.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = viewport.unproject(touchPoint);

        playButton.isTouchDown(projected.x, projected.y);
        highscoresButton.isTouchDown(projected.x, projected.y);
        optionsButton.isTouchDown(projected.x, projected.y);
        quitButton.isTouchDown(projected.x, projected.y);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = viewport.unproject(touchPoint);

        if (playButton.isTouchUp(projected.x, projected.y)) {
            game.getGameStateManager().pushScreen(new CharacterMenu(game));
        } else if (highscoresButton.isTouchUp(projected.x, projected.y)) {
            game.getGameStateManager().pushScreen(new HighscoreMenu(game));
        } else if (optionsButton.isTouchUp(projected.x, projected.y)) {
            game.getGameStateManager().pushScreen(new OptionsMenu(game));
        } else if (quitButton.isTouchUp(projected.x, projected.y)) {
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
        region = al.playButtonUp;
        regionWidth = region.getRegionWidth()*UI_SCALE*1.3f;
        regionHeight = region.getRegionHeight()*UI_SCALE*1.3f;
        playButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*14/24 - regionHeight/2,
                regionWidth, regionHeight,
                al.playButtonUp, al.playButtonDown
        );
        mainMenuButtons.add(playButton);

        region = al.highscoresButtonUp;
        regionWidth = region.getRegionWidth()*UI_SCALE;
        regionHeight = region.getRegionHeight()*UI_SCALE;
        highscoresButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*10/24 - regionHeight/2,
                regionWidth, regionHeight,
                al.highscoresButtonUp, al.highscoresButtonDown
        );
        mainMenuButtons.add(highscoresButton);

        region = al.optionsButtonUp;
        regionWidth = region.getRegionWidth()*UI_SCALE;
        regionHeight = region.getRegionHeight()*UI_SCALE;
        optionsButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*7/24 - regionHeight/2,
                regionWidth, regionHeight,
                al.optionsButtonUp, al.optionsButtonDown
        );
        mainMenuButtons.add(optionsButton);

        region = al.quitButtonUp;
        regionWidth = region.getRegionWidth()*UI_SCALE;
        regionHeight = region.getRegionHeight()*UI_SCALE;
        quitButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*4/24 - regionHeight/2,
                regionWidth, regionHeight,
                al.quitButtonUp, al.quitButtonDown
        );
        mainMenuButtons.add(quitButton);

        // Logo and copyright
        region = al.logo;
        regionWidth = region.getRegionWidth()*UI_SCALE;
        regionHeight = region.getRegionHeight()*UI_SCALE;
        logo = new Sprite(region);
        logo.setSize(regionWidth*UI_SCALE*1.1f, regionHeight*UI_SCALE*1.1f);
        logo.setPosition(V_WIDTH/2 - regionWidth/2, V_HEIGHT*20/24 - regionHeight/2);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = ((int) ((12.0f * UI_SCALE)));
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    private void drawUI() {
        for (SimpleButton button : mainMenuButtons) {
            button.draw(game.spriteBatch);
        }

        logo.draw(game.spriteBatch);
        font.draw(game.spriteBatch, "PAG6 © 2016", V_WIDTH/50, V_HEIGHT/26);
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
