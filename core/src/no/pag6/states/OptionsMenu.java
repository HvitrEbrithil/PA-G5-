package no.pag6.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.pag6.game.PAG6Game;
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
    private Sprite optionsTitle;
    private SimpleButton backButton;
    private SimpleButton musicOnButton;
    private SimpleButton musicOffButton;
    private SimpleButton soundOnButton;
    private SimpleButton soundOffButton;

    private boolean musicOn;
    private boolean soundOn;

    public OptionsMenu(PAG6Game game) {
        super(game);

        // Init objects and assets
        initUI();

        musicOn = al.getMusicOn();
        soundOn = al.getSoundOn();
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
        if (musicOn) {
            musicOnButton.isTouchDown(projected.x, projected.y);
        } else {
            musicOffButton.isTouchDown(projected.x, projected.y);
        }
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
        } else if (musicOnButton.isTouchUp(projected.x, projected.y)) {
            al.setMusicOn(false);
            musicOn = false;
            al.backgroundMusic.pause();
        } else if (musicOffButton.isTouchUp(projected.x, projected.y)) {
            al.setMusicOn(true);
            musicOn = true;
            al.backgroundMusic.play();
        } else if (soundOnButton.isTouchUp(projected.x, projected.y)) {
            al.setSoundOn(false);
            soundOn = false;
        } else if (soundOffButton.isTouchUp(projected.x, projected.y)) {
            al.setSoundOn(true);
            soundOn = true;
        }

        return true;
    }

    private void initUI() {
        TextureRegion region;
        float regionWidth, regionHeight;

        // Buttons
        region = al.backButtonUp;
        regionWidth = region.getRegionWidth();
        regionHeight = region.getRegionHeight();
        backButton = new SimpleButton(
                64, 64,
                regionWidth, regionHeight,
                al.backButtonUp, al.backButtonDown
        );

        float tempUIScale = UI_SCALE*.5f;

        region = al.onButtonUp;
        regionWidth = region.getRegionWidth()*tempUIScale;
        regionHeight = region.getRegionHeight()*tempUIScale;
        musicOnButton = new SimpleButton(
                V_WIDTH*4/7 - regionWidth/2, V_HEIGHT*8/12 - regionHeight/2,
                regionWidth, regionHeight,
                al.onButtonUp, al.onButtonDown
        );

        region = al.offButtonUp;
        regionWidth = region.getRegionWidth()*tempUIScale;
        regionHeight = region.getRegionHeight()*tempUIScale;
        musicOffButton = new SimpleButton(
                V_WIDTH*4/7 - regionWidth/2, V_HEIGHT*8/12 - regionHeight/2,
                regionWidth, regionHeight,
                al.offButtonUp, al.offButtonDown
        );

        region = al.onButtonUp;
        regionWidth = region.getRegionWidth()*tempUIScale;
        regionHeight = region.getRegionHeight()*tempUIScale;
        soundOnButton = new SimpleButton(
                V_WIDTH*4/7 - regionWidth/2, V_HEIGHT*6/12 - regionHeight/2,
                regionWidth, regionHeight,
                al.onButtonUp, al.onButtonDown
        );

        region = al.offButtonUp;
        regionWidth = region.getRegionWidth()*tempUIScale;
        regionHeight = region.getRegionHeight()*tempUIScale;
        soundOffButton = new SimpleButton(
                V_WIDTH*4/7 - regionWidth/2, V_HEIGHT*6/12 - regionHeight/2,
                regionWidth, regionHeight,
                al.offButtonUp, al.offButtonDown
        );

        // Title
        region = al.optionsTitle;
        regionWidth = region.getRegionWidth()*UI_SCALE;
        regionHeight = region.getRegionHeight()*UI_SCALE;
        optionsTitle = new Sprite(region);
        optionsTitle.setSize(regionWidth*UI_SCALE*1.1f, regionHeight*UI_SCALE*1.1f);
        optionsTitle.setPosition(V_WIDTH/2 - regionWidth/2, V_HEIGHT*20/24 - regionHeight/2);

        // Font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arialbd.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 42;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    private void drawUI() {
        backButton.draw(game.spriteBatch);

        if (musicOn) {
            musicOnButton.draw(game.spriteBatch);
        } else {
            musicOffButton.draw(game.spriteBatch);
        }

        optionsTitle.draw(game.spriteBatch);

        if (soundOn) {
            soundOnButton.draw(game.spriteBatch);
        } else {
            soundOffButton.draw(game.spriteBatch);
        }

        gl.setText(font, "MUSIC");
        font.draw(game.spriteBatch, gl, V_WIDTH*3/7 - gl.width/2, V_HEIGHT*8/12 + gl.height/2);
        gl.setText(font, "SOUND");
        font.draw(game.spriteBatch, gl, V_WIDTH*3/7 - gl.width/2, V_HEIGHT*6/12 + gl.height/2);
    }

}

