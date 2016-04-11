package no.pag6.helpers;

import com.badlogic.gdx.InputProcessor;
import no.pag6.controllers.MenuController;
import no.pag6.game.PAG6Game;
import no.pag6.models.states.HighscoreMenu;
import no.pag6.models.states.OptionsMenu;
import no.pag6.models.states.PlayState;
import no.pag6.models.states.State;
import no.pag6.models.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class MenuInputHandler implements InputProcessor {

    private PAG6Game game;
    private MenuController menuController;

    private float scaleFactorX;
    private float scaleFactorY;

    private List<SimpleButton> mainMenuButtons;
    private SimpleButton playSPButton;
    private SimpleButton play2PButton;
    private SimpleButton highscoreButton;
    private SimpleButton optionsButton;
    private SimpleButton quitButton;

    private List<SimpleButton> highscoreMenuButtons;
    private SimpleButton backButtonHighscore;

    private List<SimpleButton> optionsMenuButtons;
    private SimpleButton backButtonOptions;

    public MenuInputHandler(PAG6Game game, float scaleFactorX, float scaleFactorY) {
        this.game = game;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        menuController = game.getMenuController();

        mainMenuButtons = new ArrayList<SimpleButton>();
        playSPButton = new SimpleButton(2560/2 - AssetLoader.playSPButtonUp.getRegionWidth()/2, 300,
                AssetLoader.playSPButtonUp.getRegionWidth(), AssetLoader.playSPButtonUp.getRegionHeight(),
                AssetLoader.playSPButtonUp, AssetLoader.playSPButtonDown);
        mainMenuButtons.add(playSPButton);
        play2PButton = new SimpleButton(2560/2 - AssetLoader.play2PButtonUp.getRegionWidth()/2, 500,
                AssetLoader.play2PButtonUp.getRegionWidth(), AssetLoader.play2PButtonUp.getRegionHeight(),
                AssetLoader.play2PButtonUp, AssetLoader.play2PButtonDown);
        mainMenuButtons.add(play2PButton);
        highscoreButton = new SimpleButton(2560/2 - AssetLoader.highscoreButtonUp.getRegionWidth()/2 - 400, 700,
                AssetLoader.highscoreButtonUp.getRegionWidth(), AssetLoader.highscoreButtonUp.getRegionHeight(),
                AssetLoader.highscoreButtonUp, AssetLoader.highscoreButtonDown);
        mainMenuButtons.add(highscoreButton);
        optionsButton = new SimpleButton(2560/2 - AssetLoader.optionsButtonUp.getRegionWidth()/2 + 400, 700,
                AssetLoader.optionsButtonUp.getRegionWidth(), AssetLoader.optionsButtonUp.getRegionHeight(),
                AssetLoader.optionsButtonUp, AssetLoader.optionsButtonDown);
        mainMenuButtons.add(optionsButton);
        quitButton = new SimpleButton(2560/2 - AssetLoader.exitButtonUp.getRegionWidth()/2, 1000,
                AssetLoader.exitButtonUp.getRegionWidth(), AssetLoader.exitButtonUp.getRegionHeight(),
                AssetLoader.exitButtonUp, AssetLoader.exitButtonDown);
        mainMenuButtons.add(quitButton);

        optionsMenuButtons = new ArrayList<SimpleButton>();
        backButtonOptions = new SimpleButton(64, 64,
                AssetLoader.backArrowButton.getRegionWidth(), AssetLoader.backArrowButton.getRegionHeight(),
                AssetLoader.backArrowButton, AssetLoader.backArrowButton);
        optionsMenuButtons.add(backButtonOptions);

        highscoreMenuButtons = new ArrayList<SimpleButton>();
        backButtonHighscore = new SimpleButton(64, 64,
                AssetLoader.backArrowButton.getRegionWidth(), AssetLoader.backArrowButton.getRegionHeight(),
                AssetLoader.backArrowButton, AssetLoader.backArrowButton);
        optionsMenuButtons.add(backButtonHighscore);
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
                playSPButton.isTouchDown(screenX, screenY);
                play2PButton.isTouchDown(screenX, screenY);
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
                if (playSPButton.isTouchUp(screenX, screenY)) {
                    PlayState playState = new PlayState(game, 1);
                    game.gameStack.push(playState);
                    game.setScreen(playState);
                } else if (play2PButton.isTouchUp(screenX, screenY)) {
                    PlayState playState = new PlayState(game, 2);
                    game.gameStack.push(playState);
                    game.setScreen(playState);
                } else if (highscoreButton.isTouchUp(screenX, screenY)) {
                    HighscoreMenu highscoreMenu = new HighscoreMenu(game);
                    game.gameStack.push(highscoreMenu);
                    game.setScreen(highscoreMenu);
                } else if (optionsButton.isTouchUp(screenX, screenY)) {
                    OptionsMenu optionsMenu = new OptionsMenu(game);
                    game.gameStack.push(optionsMenu);
                    game.setScreen(optionsMenu);
                } else if (quitButton.isTouchUp(screenX, screenY)) {
                    game.dispose();
                }

                break;
            case OPTIONS_MENU:
                if (backButtonOptions.isTouchUp(screenX, screenY)) {
                    goBackToPreviousMenu();
                }

                break;
            case HIGHSCORE_MENU:
                if (backButtonHighscore.isTouchUp(screenX, screenY)) {
                    goBackToPreviousMenu();
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

    public List<SimpleButton> getMainMenuButtons() {
        return mainMenuButtons;
    }

    public List<SimpleButton> getHighscoreMenuButtons() {
        return highscoreMenuButtons;
    }

    public List<SimpleButton> getOptionsMenuButtons() {
        return optionsMenuButtons;
    }

    private int scaleX(int screen_x) {
        return (int) (screen_x/scaleFactorX);
    }

    private int scaleY(int screen_y) {
        return (int) (screen_y/scaleFactorY);
    }

    private void goBackToPreviousMenu() {
        game.gameStack.pop();
        State previousState = game.gameStack.pop();
        game.gameStack.push(previousState);
        game.setScreen(previousState);
    }

}
