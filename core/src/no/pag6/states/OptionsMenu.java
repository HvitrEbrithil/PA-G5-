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

public class OptionsMenu extends State {

    // Renderers
    private ShapeRenderer drawer;
    private TweenManager tweener;

    // Game objects

    // Game assets

    // Tween assets

    // Game UI
    private List<SimpleButton> optionsMenuButtons = new ArrayList<SimpleButton>();
    private SimpleButton backButtonOptions;
    private Sprite optionsTitle;

    public OptionsMenu(PAG6Game game) {
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

        backButtonOptions.isTouchDown(projected.x, projected.y);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = viewport.unproject(touchPoint);

        if (backButtonOptions.isTouchUp(projected.x, projected.y)) {
            game.getGameStateManager().popScreen();
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
        TextureRegion region;
        float regionWidth, regionHeight;

        // Buttons
        backButtonOptions = new SimpleButton(64, 64,
                AssetLoader.backButtonUp.getRegionWidth(), AssetLoader.backButtonUp.getRegionHeight(),
                AssetLoader.backButtonUp, AssetLoader.backButtonDown);
        optionsMenuButtons.add(backButtonOptions);

        // Title
        region = AssetLoader.optionsTitle;
        regionWidth = region.getRegionWidth()*UI_SCALE;
        regionHeight = region.getRegionHeight()*UI_SCALE;
        optionsTitle = new Sprite(region);
        optionsTitle.setSize(regionWidth*UI_SCALE*1.1f, regionHeight*UI_SCALE*1.1f);
        optionsTitle.setPosition(V_WIDTH/2 - regionWidth/2, V_HEIGHT*20/24 - regionHeight/2);
    }

    private void drawUI() {
        for (SimpleButton button : optionsMenuButtons) {
            button.draw(game.spriteBatch);
        }

        optionsTitle.draw(game.spriteBatch);
    }

}

