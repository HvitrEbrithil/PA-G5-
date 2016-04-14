package no.pag6.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import no.pag6.helpers.AssetLoader;
import no.pag6.states.SplashScreen;
import no.pag6.states.State;

import java.util.Stack;

public class PAG6Game extends Game {

    public static final String TAG = "PAG6Game";
	public Stack<State> gameStack;

    @Override
	public void create () {
        Gdx.app.log(TAG, "created");

        AssetLoader.load();

		gameStack = new Stack<State>();
		SplashScreen splashScreen = new SplashScreen(this);
		gameStack.push(splashScreen);
        setScreen(splashScreen);
    }

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}
