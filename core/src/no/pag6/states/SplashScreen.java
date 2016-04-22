package no.pag6.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;

public class SplashScreen extends State {

    private Boolean splashSoundPlayed = false;

    public SplashScreen(PAG6Game game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.spriteBatch.setProjectionMatrix(cam.combined);
        game.spriteBatch.begin();
        game.spriteBatch.draw(AssetLoader.splashAnimation.getKeyFrame(runTime), 0, 0, V_WIDTH, V_HEIGHT);
        game.spriteBatch.end();

        if (runTime > 2.0f) {
            game.getGameStateManager().setScreen(new MainMenu(game));
        }

        if (runTime > 1.0f && !splashSoundPlayed && AssetLoader.getSoundOn()) {
            AssetLoader.splashSound.play(0.8f);
            splashSoundPlayed = true;
        }
    }

}
