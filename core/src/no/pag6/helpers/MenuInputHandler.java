package no.pag6.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import no.pag6.controllers.MenuController;
import no.pag6.game.PAG6Game;
import no.pag6.models.states.HighscoreMenu;
import no.pag6.models.states.OptionsMenu;
import no.pag6.models.states.PlayState;
import no.pag6.models.states.State;
import no.pag6.models.ui.SimpleButton;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class MenuInputHandler implements InputProcessor {

    private PAG6Game game;
    private MenuController menuController;

    private float scaleFactorX;
    private float scaleFactorY;

    private List<SimpleButton> menuButtons;
    private SimpleButton playButton;
    private SimpleButton highscoreButton;
    private SimpleButton optionsButton;
    private SimpleButton quitButton;

    private List<SimpleButton> optionsButtons;
    private SimpleButton backButtonOptions;

    private List<SimpleButton> highscoreButtons;
    private SimpleButton backButtonHighscore;

    public MenuInputHandler(PAG6Game game, MenuController menuController, float scaleFactorX, float scaleFactorY) {
        this.game = game;
        this.menuController = menuController;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        menuButtons = new ArrayList<SimpleButton>();
        playButton = new SimpleButton(2560/2 - AssetLoader.playButtonUp.getRegionWidth()/2,
                1440/2 - AssetLoader.playButtonUp.getRegionHeight(),
                AssetLoader.playButtonUp.getRegionWidth(), AssetLoader.playButtonUp.getRegionHeight(),
                AssetLoader.playButtonUp, AssetLoader.playButtonDown);
        menuButtons.add(playButton);
        // highscoreButtonMenu = new SimpleButton( TODO: Set button position and size );
        menuButtons.add(highscoreButton);
        // optionsMenu = new SimpleButton( TODO: Set button position and size );
        menuButtons.add(optionsButton);
        // quitButtonMenu = new SimpleButton( TODO: Set button position and size );
        menuButtons.add(quitButton);

        optionsButtons = new ArrayList<SimpleButton>();
        // backButtonOptions = new SimpleButton( TODO: Set button position and size );
        optionsButtons.add(backButtonOptions);

        highscoreButtons = new ArrayList<SimpleButton>();
        // backButtonHighscore = new SimpleButton( TODO: Set button position and size );
        optionsButtons.add(backButtonHighscore);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        // Handle input
        switch (menuController.getCurrentMenuState()) {
            case MAIN_MENU:
                playButton.isTouchDown(screenX, screenY);
                highscoreButton.isTouchDown(screenX, screenY);
                optionsButton.isTouchDown(screenX, screenY);
                quitButton.isTouchDown(screenX, screenY);

                break;
            case OPTIONS_MENU:
                backButtonOptions.isTouchDown(screenX, screenY);

                break;
            case HIGHSCORE_MENU:
                backButtonHighscore.isTouchDown(screenX, screenY);

                break;
            default:
                return false;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        // Handle input
        switch (menuController.getCurrentMenuState()) {
            case MAIN_MENU:
                if (playButton.isTouchUp(screenX, screenY)) {
                    PlayState playState = new PlayState(game);
                    game.gameStack.push(playState);
                    game.setScreen(playState);
                }

                else if (highscoreButton.isTouchUp(screenX, screenY)) {
                    HighscoreMenu highscoreMenu = new HighscoreMenu(game);
                    game.gameStack.push(highscoreMenu);
                    game.setScreen(highscoreMenu);
                }

                else if (optionsButton.isTouchUp(screenX, screenY)) {
                    OptionsMenu optionsMenu = new OptionsMenu(game);
                    game.gameStack.push(optionsMenu);
                    game.setScreen(optionsMenu);
                }

                else if (quitButton.isTouchUp(screenX, screenY)) {
                    game.dispose();
                }

                break;
            case OPTIONS_MENU:
                if (backButtonOptions.isTouchUp(screenX, screenY)) {
                    game.gameStack.pop();
                    State previousState = game.gameStack.pop();
                    game.gameStack.push(previousState);
                    game.setScreen(previousState);
                }

                break;
            case HIGHSCORE_MENU:
                if (backButtonHighscore.isTouchUp(screenX, screenY)) {
                    game.gameStack.pop();
                    State previousState = game.gameStack.pop();
                    game.gameStack.push(previousState);
                    game.setScreen(previousState);
                }

                break;
            default:
                return false;
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public List<SimpleButton> getMenuButtons() {
        return menuButtons;
    }

    private int scaleX(int screen_x) {
        return (int) (screen_x/scaleFactorX);
    }

    private int scaleY(int screen_y) {
        return (int) (screen_y/scaleFactorY);
    }

}
