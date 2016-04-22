package no.pag6.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.ui.SimpleButton;

import java.util.Arrays;
import java.util.List;

public class HighscoreMenu extends State {

    // Game assets
    private GlyphLayout gl = new GlyphLayout();
    private BitmapFont font;

    // Game UI
    private SimpleButton backButton;

    // Highscores
    List<String> highscorePlayers;
    List<String> highscores;

    public HighscoreMenu(PAG6Game game) {
        super(game);

        // Init objects and assets
        initUI();

        getPlayerHighscores();
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

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = viewport.unproject(touchPoint);

        if (backButton.isTouchUp(projected.x, projected.y)) {
            game.getGameStateManager().popScreen();
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

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arialbd.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    private void getPlayerHighscores() {
        highscorePlayers = Arrays.asList(AssetLoader.getHighscorePlayers().split(","));
        highscores = Arrays.asList(AssetLoader.getHighscores().split(","));
        for (int i = 0; i < (HIGHSCORES_TO_SHOW > highscores.size() ? highscores.size() : HIGHSCORES_TO_SHOW); i++) {
            Gdx.app.log("SCORES", "Player " + highscorePlayers.get(i) + ": " + highscores.get(i));
        }

    }

    private void drawUI() {
        backButton.draw(game.spriteBatch);

        // Player highscores
        for (int i = 0; i < highscores.size(); i++) {
            gl.setText(font, highscorePlayers.get(i) + ": " + highscores.get(i));
            font.draw(game.spriteBatch, gl, V_WIDTH/2 - gl.width/2, V_HEIGHT*(13 - i)/14 + gl.height/2);
        }
    }

}
