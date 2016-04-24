package no.pag6.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import no.pag6.game.PAG6Game;

public class SplashScreen extends State {

    private Boolean splashSoundPlayed = false;

    public SplashScreen(PAG6Game game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.spriteBatch.setProjectionMatrix(cam.combined);
        game.spriteBatch.begin();

        game.spriteBatch.draw(al.splashAnimation.getKeyFrame(runTime - 1), 0, 0, V_WIDTH, V_HEIGHT);

        game.spriteBatch.end();

        // Play sound
        if (runTime > 2.0f && !splashSoundPlayed && al.getSoundOn()) {
            al.splashSound.play(0.8f);
            splashSoundPlayed = true;
        }

        // Show main menu
        if (al.splashAnimation.isAnimationFinished(runTime - 1)) {
            game.getGameStateManager().setScreen(new MainMenu(game));
        }
    }

}
