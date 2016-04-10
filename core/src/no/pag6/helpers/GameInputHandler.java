package no.pag6.helpers;

import com.badlogic.gdx.InputProcessor;
import no.pag6.controllers.GameController;
import no.pag6.game.PAG6Game;

public class GameInputHandler implements InputProcessor {

    private PAG6Game game;
    private GameController gameController;

    private float scaleFactorX;
    private float scaleFactorY;

    public GameInputHandler(PAG6Game game, GameController gameController, float scaleFactorX, float scaleFactorY) {
        this.game = game;
        this.gameController = gameController;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
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

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        // Handle input

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
