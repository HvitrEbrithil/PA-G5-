package no.pag6.models.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class State implements Screen {

    public static final String TAG = "State";

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

}
