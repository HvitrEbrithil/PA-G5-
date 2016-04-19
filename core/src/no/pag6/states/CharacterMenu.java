package no.pag6.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
        screenX = (int) touchPoint.x;
        screenY = (int) touchPoint.y;

        playButton.isTouchDown(screenX, screenY);
        backButton.isTouchDown(screenX, screenY);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = cam.unproject(touchPoint);
        screenX = (int) touchPoint.x;
        screenY = (int) touchPoint.y;

        if (playButton.isTouchUp(screenX, screenY) && nofPlayers == 1) {
            game.getGameStateManager().pushScreen(new PlayState(game, 1, "test_lvl.tmx"));
        } else if (playButton.isTouchUp(screenX, screenY) && nofPlayers == 2) {
            game.getGameStateManager().pushScreen(new PlayState(game, 2, "test_lvl.tmx"));
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
        float uiScale;
        TextureRegion region;
        float regionWidth, regionHeight;

        // Buttons
        uiScale = 0.4f;

        region = AssetLoader.playSPButtonUp;
        regionWidth = region.getRegionWidth()*uiScale;
        regionHeight = region.getRegionHeight()*uiScale;
        playButton = new SimpleButton(
                V_WIDTH/3 - regionWidth/2, V_HEIGHT*8/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.playSPButtonUp, AssetLoader.playSPButtonDown
        );
        characterMenuButtons.add(playButton);

        region = AssetLoader.backButtonUp;
        regionWidth = region.getRegionWidth()*uiScale;
        regionHeight = region.getRegionHeight()*uiScale;
        backButton = new SimpleButton(
                V_WIDTH/2 - regionWidth/2, V_HEIGHT*6/12 - regionHeight/2,
                regionWidth, regionHeight,
                AssetLoader.backButtonUp, AssetLoader.backButtonDown
        );
        characterMenuButtons.add(backButton);
    }

    private void drawUI() {
        for (SimpleButton button : characterMenuButtons) {
            button.draw(game.spriteBatch);
        }
    }

}
