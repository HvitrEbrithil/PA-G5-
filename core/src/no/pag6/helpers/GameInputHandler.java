package no.pag6.helpers;

import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.List;

import no.pag6.controllers.GameController;
import no.pag6.game.PAG6Game;
import no.pag6.models.states.HighscoreMenu;
import no.pag6.models.states.OptionsMenu;
import no.pag6.models.states.PauseState;
import no.pag6.models.states.PlayState;
import no.pag6.models.states.State;
import no.pag6.models.ui.SimpleButton;

public class GameInputHandler implements InputProcessor {

    private PAG6Game game;
    private GameController gameController;

    private float scaleFactorX;
    private float scaleFactorY;

    private List<SimpleButton> playButtons;
    private SimpleButton pauseButton;

    private List<SimpleButton> pauseButtons;
    private SimpleButton resumeButton;
    private SimpleButton highscoreButton;
    private SimpleButton optionsButton;
    private SimpleButton menuButton;

    public GameInputHandler(PAG6Game game, GameController gameController, float scaleFactorX, float scaleFactorY) {
        this.game = game;
        this.gameController = gameController;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        playButtons = new ArrayList<SimpleButton>();
        // pauseButton = new SimpleButton( TODO: Set button position and size );
        playButtons.add(pauseButton);

        pauseButtons = new ArrayList<SimpleButton>();
        // resumeButton = new SimpleButton( TODO: Set button position and size );
        pauseButtons.add(resumeButton);
        // highscoreButton = new SimpleButton( TODO: Set button position and size );
        pauseButtons.add(highscoreButton);
        // optionsButton = new SimpleButton( TODO: Set button position and size );
        pauseButtons.add(optionsButton);
        // menuButton = new SimpleButton( TODO: Set button position and size );
        pauseButtons.add(menuButton);
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
                highscoreButton.isTouchDown(screenX, screenY);
                optionsButton.isTouchDown(screenX, screenY);
                menuButton.isTouchDown(screenX, screenY);

                break;
            case GAME_OVER:
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
                }

                break;
            case PAUSED:
                if (resumeButton.isTouchUp(screenX, screenY)) {
                    game.gameStack.pop();
                    State previousState = game.gameStack.pop();
                    game.gameStack.push(previousState);
                    game.setScreen(previousState);
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

                else if (menuButton.isTouchUp(screenX, screenY)) {
                    game.gameStack.pop();
                    game.gameStack.pop();
                    State previousState = game.gameStack.pop();
                    game.gameStack.push(previousState);
                    game.setScreen(previousState);
                }

                break;
            case GAME_OVER:
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

    private int scaleX(int screen_x) {
        return (int) (screen_x/ scaleFactorX);
    }

    private int scaleY(int screen_y) {
        return (int) (screen_y/ scaleFactorY);
    }

}
