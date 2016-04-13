package no.pag6.helpers;

import com.badlogic.gdx.InputProcessor;
import no.pag6.controllers.GameController;
import no.pag6.controllers.MenuController;
import no.pag6.game.PAG6Game;
import no.pag6.models.states.HighscoreMenu;
import no.pag6.models.states.OptionsMenu;
import no.pag6.models.states.PauseState;
import no.pag6.models.states.State;
import no.pag6.models.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class GameInputHandler implements InputProcessor {

    private PAG6Game game;
    private MenuController menuController;
    private GameController gameController;

    private float scaleFactorX;
    private float scaleFactorY;

    private List<SimpleButton> playButtons;
    private SimpleButton pauseButton;

    private List<SimpleButton> pauseButtons;
    private SimpleButton resumeButton;
    private SimpleButton highscoreButtonPause;
    private SimpleButton optionsButton;
    private SimpleButton menuButtonPause;

    private List<SimpleButton> gameOverButtons;
    private SimpleButton highscoreButtonGameOver;
    private SimpleButton menuButtonGameOver;

    public GameInputHandler(PAG6Game game, float scaleFactorX, float scaleFactorY) {
        this.game = game;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        menuController = game.getMenuController();
        gameController = game.getGameController();

        playButtons = new ArrayList<SimpleButton>();
        pauseButton = new SimpleButton(2560 - AssetLoader.optionsButtonUp.getRegionWidth() - 64, 64,
                AssetLoader.optionsButtonUp.getRegionWidth(), AssetLoader.optionsButtonUp.getRegionHeight(),
                AssetLoader.optionsButtonUp, AssetLoader.optionsButtonDown);
        playButtons.add(pauseButton);

        pauseButtons = new ArrayList<SimpleButton>();
        resumeButton = new SimpleButton(2560 - AssetLoader.exitCrossButton.getRegionWidth() - 64, 64,
                AssetLoader.exitCrossButton.getRegionWidth(), AssetLoader.exitCrossButton.getRegionHeight(),
                AssetLoader.exitCrossButton, AssetLoader.exitCrossButton);
        pauseButtons.add(resumeButton);
        highscoreButtonPause = new SimpleButton(2560/2 - AssetLoader.highscoreButtonUp.getRegionWidth()/2 + 500, 1200,
                AssetLoader.highscoreButtonUp.getRegionWidth(), AssetLoader.highscoreButtonUp.getRegionHeight(),
                AssetLoader.highscoreButtonUp, AssetLoader.highscoreButtonDown);
        pauseButtons.add(highscoreButtonPause);
        optionsButton = new SimpleButton(2560/2 - AssetLoader.optionsButtonUp.getRegionWidth()/2 - 500, 1200,
                AssetLoader.optionsButtonUp.getRegionWidth(), AssetLoader.optionsButtonUp.getRegionHeight(),
                AssetLoader.optionsButtonUp, AssetLoader.optionsButtonDown);
        pauseButtons.add(optionsButton);
        menuButtonPause = new SimpleButton(2560/2 - AssetLoader.mainMenuButtonUp.getRegionWidth()/2, 1200,
                AssetLoader.mainMenuButtonUp.getRegionWidth(), AssetLoader.mainMenuButtonUp.getRegionHeight(),
                AssetLoader.mainMenuButtonUp, AssetLoader.mainMenuButtonDown);
        pauseButtons.add(menuButtonPause);

        gameOverButtons = new ArrayList<SimpleButton>();
        highscoreButtonGameOver = new SimpleButton(2560/2 - AssetLoader.highscoreButtonUp.getRegionWidth()/2 + 500, 1200,
                AssetLoader.highscoreButtonUp.getRegionWidth(), AssetLoader.highscoreButtonUp.getRegionHeight(),
                AssetLoader.highscoreButtonUp, AssetLoader.highscoreButtonDown);
        gameOverButtons.add(highscoreButtonGameOver);
        menuButtonGameOver = new SimpleButton(2560/2 - AssetLoader.mainMenuButtonUp.getRegionWidth()/2, 1200,
                AssetLoader.mainMenuButtonUp.getRegionWidth(), AssetLoader.mainMenuButtonUp.getRegionHeight(),
                AssetLoader.mainMenuButtonUp, AssetLoader.mainMenuButtonDown);
        gameOverButtons.add(menuButtonGameOver);

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
        switch (gameController.getCurrentGameState()) {
            case RUNNING:
                pauseButton.isTouchDown(screenX, screenY);

                break;
            case PAUSED:
                resumeButton.isTouchDown(screenX, screenY);
                highscoreButtonPause.isTouchDown(screenX, screenY);
                optionsButton.isTouchDown(screenX, screenY);
                menuButtonPause.isTouchDown(screenX, screenY);

                break;
            case GAME_OVER:
                highscoreButtonGameOver.isTouchDown(screenX, screenY);
                menuButtonGameOver.isTouchDown(screenX, screenY);

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
        switch (gameController.getCurrentGameState()) {
            case RUNNING:
                if (pauseButton.isTouchUp(screenX, screenY)) {
                    PauseState pauseState = new PauseState(game);
                    game.gameStack.push(pauseState);
                    game.setScreen(pauseState);

                    gameController.setCurrentGameState(GameController.GameState.PAUSED);
                }

                break;
            case PAUSED:
                if (resumeButton.isTouchUp(screenX, screenY)) {
                    game.gameStack.pop();
                    State previousState = game.gameStack.pop();
                    game.gameStack.push(previousState);
                    game.setScreen(previousState);

                    gameController.setCurrentGameState(GameController.GameState.RUNNING);
                } else if (highscoreButtonPause.isTouchUp(screenX, screenY)) {
                    HighscoreMenu highscoreMenu = new HighscoreMenu(game);
                    game.gameStack.push(highscoreMenu);
                    game.setScreen(highscoreMenu);

                    menuController.setCurrentMenuState(MenuController.MenuState.HIGHSCORE_MENU);
                } else if (optionsButton.isTouchUp(screenX, screenY)) {
                    OptionsMenu optionsMenu = new OptionsMenu(game);
                    game.gameStack.push(optionsMenu);
                    game.setScreen(optionsMenu);

                    menuController.setCurrentMenuState(MenuController.MenuState.OPTIONS_MENU);
                } else if (menuButtonPause.isTouchUp(screenX, screenY)) {
                    game.gameStack.pop();
                    game.gameStack.pop();
                    State previousState = game.gameStack.pop();
                    game.gameStack.push(previousState);
                    game.setScreen(previousState);

                    menuController.setCurrentMenuState(MenuController.MenuState.MAIN_MENU);

                break;
                }

            case GAME_OVER:
                if (highscoreButtonGameOver.isTouchUp(screenX, screenY)) {
                    game.gameStack.pop();
                    game.gameStack.pop();
                    HighscoreMenu highscoreMenu = new HighscoreMenu(game);
                    game.gameStack.push(highscoreMenu);
                    game.setScreen(highscoreMenu);

                    menuController.setCurrentMenuState(MenuController.MenuState.HIGHSCORE_MENU);
                } else if (menuButtonGameOver.isTouchUp(screenX, screenY)) {
                    game.gameStack.pop();
                    game.gameStack.pop();
                    State previousState = game.gameStack.pop();
                    game.gameStack.push(previousState);
                    game.setScreen(previousState);

                    menuController.setCurrentMenuState(MenuController.MenuState.MAIN_MENU);
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

    public List<SimpleButton> getPlayButtons() {
        return playButtons;
    }

    public List<SimpleButton> getPauseButtons() {
        return pauseButtons;
    }

    private int scaleX(int screen_x) {
        return (int) (screen_x/scaleFactorX);
    }

    private int scaleY(int screen_y) {
        return (int) (screen_y/scaleFactorY);
    }

}
