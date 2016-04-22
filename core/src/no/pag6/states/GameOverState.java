package no.pag6.states;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.models.Player;
import no.pag6.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class GameOverState extends State {
    Player[] players;

    // Renderers
    private ShapeRenderer drawer;
    private TweenManager tweener;
    private GlyphLayout gl = new GlyphLayout();

    // Game objects


    // Game assets
    BitmapFont font;

    // Tween assets

    // Game UI
    private List<SimpleButton> gameOverButtons = new ArrayList<SimpleButton>();
    private SimpleButton mainMenuButton;

    public GameOverState(PAG6Game game, Player[] players) {
        super(game);

        this.players = players;

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

        // Render sprites and text
        game.spriteBatch.setProjectionMatrix(cam.combined);
        game.spriteBatch.begin();
        game.spriteBatch.enableBlending();

        drawUI();
        drawScores();

        game.spriteBatch.end();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = viewport.unproject(touchPoint);

        mainMenuButton.isTouchDown(projected.x, projected.y);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = viewport.unproject(touchPoint);

        if (mainMenuButton.isTouchUp(projected.x, projected.y)) {
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

        region = AssetLoader.mainMenuButtonUp;
        regionWidth = region.getRegionWidth()*uiScale;
        regionHeight = region.getRegionHeight()*uiScale;
        mainMenuButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*2/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.mainMenuButtonUp, AssetLoader.mainMenuButtonDown
        );
        gameOverButtons.add(mainMenuButton);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arialbd.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 42;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    private void drawUI() {
        for (SimpleButton button : gameOverButtons) {
            button.draw(game.spriteBatch);
        }
    }

    private void drawScores() {
        // Draw name and score for each players
        String scores = "";
        for (int i = 1; i <= players.length; i++) {
            String scoreString = "" + players[i-1].getName() + ": " + Integer.toString(players[i-1].getScore());
            scores += scoreString + "\n";
        }
        gl.setText(font, scores);
        font.draw(game.spriteBatch, gl, V_WIDTH/2 - gl.width/2, V_HEIGHT*(6/12));
    }
}
