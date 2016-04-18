package no.pag6.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class CharacterMenu extends State {

    private int nofPlayers;

    // Renderers

    // Game objects

    // Game assets

    // Tween assets

    // Game UI
    private List<SimpleButton> characterMenuButtons = new ArrayList<SimpleButton>();
    private SimpleButton playButton;
    private SimpleButton backButton;
    private TextField player1Name;
    private TextField player2Name;

    public CharacterMenu(PAG6Game game, int nofPlayers) {
        super(game);
        this.nofPlayers = nofPlayers;

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

        // Render shapes
        game.drawer.begin(ShapeRenderer.ShapeType.Filled);
        game.drawer.setColor(0.4f, 1.0f, 0.3f, 1);
        game.drawer.rect(0, 0, V_WIDTH, V_HEIGHT);
        game.drawer.end();

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
        projected = cam.unproject(touchPoint);
        screenX = (int) projected.x;
        screenY = (int) projected.y;

        playButton.isTouchDown(screenX, screenY);
        backButton.isTouchDown(screenX, screenY);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = cam.unproject(touchPoint);
        screenX = (int) projected.x;
        screenY = (int) projected.y;

        if (nofPlayers == 1) {
            if (playButton.isTouchUp(screenX, screenY)) {
                game.getGameStateManager().pushScreen(new PlayState(game, 1, "maptest2.tmx"));
            }
        } else if (nofPlayers == 2) {
            if (playButton.isTouchUp(screenX, screenY)) {
                game.getGameStateManager().pushScreen(new PlayState(game, 2, "maptest2.tmx"));
            }
        } else if (backButton.isTouchUp(screenX, screenY)) {
            game.getGameStateManager().popScreen();
        }

        return true;
    }

    private void initTweenAssets() {
    }

    private void initGameObjects() {
    }

    private void initGameAssets() {
    }

    private void initUI() {
        TextureRegion region;
        float regionWidth, regionHeight;

        // Buttons
        region = AssetLoader.playSPButtonUp;
        regionWidth = region.getRegionWidth()*UI_SCALE;
        regionHeight = region.getRegionHeight()*UI_SCALE;
        playButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*4/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.playSPButtonUp, AssetLoader.playSPButtonDown
        );
        characterMenuButtons.add(playButton);

        region = AssetLoader.backButtonUp;
        regionWidth = region.getRegionWidth()*UI_SCALE;
        regionHeight = region.getRegionHeight()*UI_SCALE;
        backButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.backButtonUp, AssetLoader.backButtonDown
        );
        characterMenuButtons.add(backButton);

        Skin skin = new Skin();
        skin.add(
                "background",
                new NinePatch(new Texture(Gdx.files.internal("textures/play_sp_button.png")), 32, 32, 32, 32));
        skin.add("cursor", new Texture(Gdx.files.internal("textures/pause_button.png")));

        TextField.TextFieldStyle tStyle = new TextField.TextFieldStyle();
        tStyle.font = AssetLoader.font;
        tStyle.fontColor = Color.BLACK;
        tStyle.background = skin.getDrawable("background");
        tStyle.cursor = skin.newDrawable("cursor", Color.GREEN);
        tStyle.cursor.setMinWidth(2f);
        tStyle.selection = skin.newDrawable("background", 0.5f, 0.5f, 0.5f, 0.5f);
        player1Name = new TextField("test", tStyle);
        if (nofPlayers == 2) {
            player2Name = new TextField("test2", tStyle);
        }
    }

    private void drawUI() {
        for (SimpleButton button : characterMenuButtons) {
            button.draw(game.spriteBatch);
        }

        player1Name.draw(game.spriteBatch, 1);
        if (nofPlayers == 2) {
            player2Name.draw(game.spriteBatch, 1);
        }
    }

}
