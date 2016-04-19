package no.pag6.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CharacterMenu extends State {

    private int nofPlayers;
    private String playerNameRegex = "^[a-zA-Z .'-]{1,20}$";
    private String player1Name;
    private String player2Name;

    // Renderers
    private GlyphLayout gl = new GlyphLayout();

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
                if (Pattern.matches(playerNameRegex, text.trim())) {
                    player1Name = text.trim();
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
        }, "Enter name of Player 1", "", "name, eg. Aüwe");
    }

    private void takePlayer2Name() {
        disableBackButton();
        disablePlayButton();
        Gdx.input.getTextInput(new Input.TextInputListener() {
            @Override
            public void input(String text) {
                if (Pattern.matches(playerNameRegex, text.trim())) {
                    player2Name = text.trim();
                    enableBackButton();
                    enablePlayButton();
                } else {
                    takePlayer2Name();
                }
            }

            @Override
            public void canceled() {
                player1Name = null;
                enableBackButton();
            }
        }, "Enter name of Player 2", "", "name, eg. Melkor");
    }

    private void drawUI() {
        for (SimpleButton button : characterMenuButtons) {
            button.draw(game.spriteBatch);
        }

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/droid_serif.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        if (player1Name != null) {
            String player1NameString = "Player 1: " + player1Name;
            gl.setText(font, player1NameString);
            font.draw(game.spriteBatch, gl, V_WIDTH/2 - gl.width/2, V_HEIGHT*5/6);
        }
        if (player2Name != null) {
            String player1NameString = "Player 2: " + player2Name;
            gl.setText(font, player1NameString);
            font.draw(game.spriteBatch, gl, V_WIDTH/2 - gl.width/2, V_HEIGHT*4/6);
        }
    }

    private void enableBackButton() {
        backButtonEnabled = true;
    }
    private void disableBackButton() {
        backButtonEnabled = false;
    }

    private void enablePlayButton() {
        playButtonEnabled = true;
    }
    private void disablePlayButton() {
        playButtonEnabled = false;
    }

}
