package no.pag6.helpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {

    private GameWorld world;

    private float scale_factor_x;
    private float scale_factor_y;

    public InputHandler(GameWorld world, float scale_factor_x, float scale_factor_y) {
        this.world = world;
        this.scale_factor_x = scale_factor_x;
        this.scale_factor_y = scale_factor_y;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            return true;
        }

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
        return (int) (screen_x/scale_factor_x);
    }

    private int scaleY(int screen_y) {
        return (int) (screen_y/scale_factor_y);
    }

}
