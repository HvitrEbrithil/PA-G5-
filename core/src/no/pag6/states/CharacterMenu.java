package no.pag6.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CharacterMenu extends State {

    public static final String TAG = "CharacterMenu";

    private int nofPlayers;
    private String playerNameRegex = "^[a-zA-Z .'-]+$";
    private String player1Name;
    private String player2Name;

    // Renderers

    // Game objects

    // Game assets

    // Tween assets

    // Game UI
    private boolean backButtonEnabled = true;
    private boolean playButtonEnabled = false;

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

        takePlayer1Name();
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

        if (backButtonEnabled) {
            backButton.isTouchDown(screenX, screenY);
        }
        if (playButtonEnabled) {
            playButton.isTouchDown(screenX, screenY);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = cam.unproject(touchPoint);
        screenX = (int) projected.x;
        screenY = (int) projected.y;

        if (backButtonEnabled) {
            if (backButton.isTouchUp(screenX, screenY)) {
                game.getGameStateManager().popScreen();
            }
        }
        if (playButtonEnabled) {
            if (nofPlayers == 1) {
                if (playButton.isTouchUp(screenX, screenY)) {
                    game.getGameStateManager().pushScreen(new PlayState(game, 1, "maptest2.tmx"));
                }
            } else if (nofPlayers == 2) {
                if (playButton.isTouchUp(screenX, screenY)) {
                    game.getGameStateManager().pushScreen(new PlayState(game, 2, "maptest2.tmx"));
                }
            }
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
    }

    private void takePlayer1Name() {
        disableBackButton();
        disablePlayButton();
        Gdx.input.getTextInput(new Input.TextInputListener() {
            @Override
            public void input(String text) {
                if (Pattern.matches(playerNameRegex, text)) {
                    player1Name = text;
                    enableBackButton();
                    enablePlayButton();

                    if (nofPlayers == 2) {
                        takePlayer2Name();
                    }
                } else {
                    takePlayer1Name();
                }
            }

            @Override
            public void canceled() {
                enableBackButton();
            }
        }, "Enter name of Player 1", "", "eg. Morlock");
    }

    private void takePlayer2Name() {
        disableBackButton();
        disablePlayButton();
        Gdx.input.getTextInput(new Input.TextInputListener() {
            @Override
            public void input(String text) {
                if (Pattern.matches(playerNameRegex, text)) {
                    player2Name = text;
                    enableBackButton();
                    enablePlayButton();
                } else {
                    takePlayer2Name();
                }
            }

            @Override
            public void canceled() {
                enableBackButton();
            }
        }, "Enter name of Player 2", "", "eg. Melkor");
    }

    private void drawUI() {
        for (SimpleButton button : characterMenuButtons) {
            button.draw(game.spriteBatch);
        }
    }

    private void enableBackButton() {
        Gdx.app.log(TAG, "buttons enabled");
        backButtonEnabled = true;
    }
    private void disableBackButton() {
        Gdx.app.log(TAG, "buttons disabled");
        backButtonEnabled = false;
    }

    private void enablePlayButton() {
        Gdx.app.log(TAG, "buttons enabled");
        playButtonEnabled = true;
    }
    private void disablePlayButton() {
        Gdx.app.log(TAG, "buttons disabled");
        playButtonEnabled = false;
    }

}
