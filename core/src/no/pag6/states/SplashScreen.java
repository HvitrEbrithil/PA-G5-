package no.pag6.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;

public class SplashScreen extends State {

    private float runtime = 0.0f;
    private Boolean splashSoundPlayed = false;

    public SplashScreen(PAG6Game game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        runtime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.spriteBatch.setProjectionMatrix(cam.combined);
        game.spriteBatch.begin();
        game.spriteBatch.draw(AssetLoader.splashAnimation.getKeyFrame(runtime), 0, 0, V_WIDTH, V_HEIGHT);
        game.spriteBatch.end();

        if (runtime > 2.0f) {
            game.getGameStateManager().setScreen(new MainMenu(game));
        }

        if (runtime > 1.0f && ! splashSoundPlayed) {
            AssetLoader.splashSound.play();
            splashSoundPlayed = true;
        }
    }

}
