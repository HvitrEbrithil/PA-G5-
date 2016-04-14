package no.pag6.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import no.pag6.game.PAG6Game;

public class State implements Screen, InputProcessor {

    public static final String TAG = "State";

    // TODO: Scaling
    private float screenWidth = Gdx.graphics.getWidth();
    private float screenHeight = Gdx.graphics.getHeight();
    private float gameWidth = 2560;
    private float gameHeight = screenHeight/(screenWidth/gameWidth);

    private float scaleFactorX = screenWidth/gameWidth;
    private float scaleFactorY = screenHeight/gameHeight;

    @Override
    public void show() {
        Gdx.app.log(TAG, "show called");
    }

    @Override
    public void render(float delta) {
        Gdx.app.log(TAG, "render called");
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize called");
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "resume called");
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "hide called");
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose called");
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
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

    public void update(float delta) {
        Gdx.app.log(TAG, "update called");
    }

    public void goBackToPreviousState(PAG6Game game) {
        game.gameStack.pop();
        State previousState = game.gameStack.pop();
        game.gameStack.push(previousState);
        game.setScreen(previousState);
    }

    public void goBackToPreviousPreviousState(PAG6Game game) {
        game.gameStack.pop();
        goBackToPreviousState(game);
    }

    // TODO: Implement when we get a scale-factor for views
    public int scaleX(int screenX) {
        return (int) (screenX/scaleFactorX);
    }

    public int scaleY(int screenY) {
        return (int) (screenY/scaleFactorY);
    }

}
