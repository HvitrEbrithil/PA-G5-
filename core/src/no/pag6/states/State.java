package no.pag6.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.Constants;

public abstract class State implements Screen, InputProcessor, Constants {

    protected float runTime;

    protected PAG6Game game;
    protected OrthographicCamera cam;
    protected Viewport viewport;
    protected Vector3 projected, touchPoint = new Vector3(0, 0, 0);

    // Background
    private  Sprite background;

    public State(PAG6Game game) {
        this.game = game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(V_WIDTH, V_HEIGHT, cam);
        cam.position.set(V_WIDTH/2, V_HEIGHT/2, 0);

        initBackground();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        // Update logic
        update(delta);

        // Clear drawings
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render background
        game.spriteBatch.setProjectionMatrix(cam.combined);
        game.spriteBatch.begin();
        game.spriteBatch.disableBlending();

        drawBackground();

        game.spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
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
        runTime += delta;

        // Music
        if (runTime > 2f && al.getMusicOn() && !al.backgroundMusic.isPlaying() && !(game.getGameStateManager().getScreen() instanceof PlayState)) {
            al.backgroundMusic.play();
        }
    }

    private void initBackground() {
        TextureRegion region = al.background;
        background = new Sprite(region);
        background.setSize(V_WIDTH, V_HEIGHT);
        background.setPosition(0, 0);
    }

    private void drawBackground(){
        background.draw(game.spriteBatch);
    }

}
