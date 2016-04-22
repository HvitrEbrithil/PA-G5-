package no.pag6.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.ui.SimpleButton;

public class OptionsMenu extends State {

    // Renderers
    private ShapeRenderer drawer;

    // Game objects

    // Game assets
    private GlyphLayout gl = new GlyphLayout();
    private BitmapFont font;

    // Tween assets

    // Game UI
    private SimpleButton backButton;
    private SimpleButton soundOnButton;
    private SimpleButton soundOffButton;

    private boolean soundOn;

    public OptionsMenu(PAG6Game game) {
        super(game);

        // Set up drawer and batcher
        drawer = new ShapeRenderer();
        drawer.setProjectionMatrix(cam.combined);

        // Init objects and assets
        initUI();

        soundOn = AssetLoader.getSoundOn();
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = viewport.unproject(touchPoint);

        backButton.isTouchDown(projected.x, projected.y);
        if (soundOn) {
            soundOnButton.isTouchDown(projected.x, projected.y);
        } else {
            soundOffButton.isTouchDown(projected.x, projected.y);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = viewport.unproject(touchPoint);

        if (backButton.isTouchUp(projected.x, projected.y)) {
            game.getGameStateManager().popScreen();
        } else if (soundOnButton.isTouchUp(projected.x, projected.y)) {
            AssetLoader.setSoundOn(false);
            soundOn = false;
            AssetLoader.backgroundMusic.pause();
        } else if (soundOffButton.isTouchUp(projected.x, projected.y)) {
            AssetLoader.setSoundOn(true);
            soundOn = true;
            AssetLoader.backgroundMusic.play();
        }

        return true;
    }

    private void initUI() {
        TextureRegion region;
        float regionWidth, regionHeight;

        // Buttons
        region = AssetLoader.backButtonUp;
        regionWidth = region.getRegionWidth()*UI_SCALE;
        regionHeight = region.getRegionHeight()*UI_SCALE;
        backButton = new SimpleButton(
                V_WIDTH/3 - regionWidth/2, V_HEIGHT/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.backButtonUp, AssetLoader.backButtonDown
        );

        float tempUIScale = UI_SCALE*.5f;

        region = AssetLoader.soundOnButtonUp;
        regionWidth = region.getRegionWidth()*tempUIScale;
        regionHeight = region.getRegionHeight()*tempUIScale;
        soundOnButton = new SimpleButton(
                V_WIDTH*4/7 - regionWidth/2, V_HEIGHT*8/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.soundOnButtonUp, AssetLoader.soundOnButtonDown
        );

        region = AssetLoader.soundOffButtonUp;
        regionWidth = region.getRegionWidth()*tempUIScale;
        regionHeight = region.getRegionHeight()*tempUIScale;
        soundOffButton = new SimpleButton(
                V_WIDTH*4/7 - regionWidth/2, V_HEIGHT*8/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.soundOffButtonUp, AssetLoader.soundOffButtonDown
        );

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arialbd.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 42;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    private void drawUI() {
        backButton.draw(game.spriteBatch);

        if (soundOn) {
            soundOnButton.draw(game.spriteBatch);
        } else {
            soundOffButton.draw(game.spriteBatch);
        }

        gl.setText(font, "MUSIC");
        font.draw(game.spriteBatch, gl, V_WIDTH*3/7 - gl.width/2, V_HEIGHT*8/12 + gl.height/2);
    }

}

