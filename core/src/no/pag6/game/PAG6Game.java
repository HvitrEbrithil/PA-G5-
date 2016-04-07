package no.pag6.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import no.pag6.helpers.AssetLoader;
import no.pag6.models.states.SplashScreen;

public class PAG6Game extends Game {

    public static final String TAG = "PAG6Game";

	@Override
	public void create () {
        Gdx.app.log(TAG, "created.");

        AssetLoader.load();

        setScreen(new SplashScreen());
    }

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
