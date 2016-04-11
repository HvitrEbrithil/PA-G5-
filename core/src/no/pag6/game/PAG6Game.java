package no.pag6.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import no.pag6.controllers.GameController;
import no.pag6.controllers.MenuController;
import no.pag6.helpers.AssetLoader;
import no.pag6.helpers.GameInputHandler;
import no.pag6.helpers.MenuInputHandler;
import no.pag6.models.states.SplashScreen;
import no.pag6.models.states.State;
import no.pag6.views.GameRenderer;
import no.pag6.views.MenuRenderer;

import java.util.Stack;

public class PAG6Game extends Game {

    public static final String TAG = "PAG6Game";
	public Stack<State> gameStack;

    private MenuController menuController;
    private MenuRenderer menuRenderer;
    private MenuInputHandler menuInputHandler;

    private GameController gameController;
    private GameRenderer gameRenderer;
    private GameInputHandler gameInputHandler;

    @Override
	public void create () {
        Gdx.app.log(TAG, "created");

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 2560;
        float gameHeight = screenHeight/(screenWidth/gameWidth);

        AssetLoader.load();

		menuController = new MenuController();
        menuInputHandler = new MenuInputHandler(this, screenWidth/gameWidth, screenHeight/gameHeight);
        menuRenderer = new MenuRenderer(this);

        gameController = new GameController();
        gameInputHandler = new GameInputHandler(this, screenWidth/gameWidth, screenHeight/gameHeight);
        gameRenderer = new GameRenderer(this);

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

    public MenuController getMenuController() {
        return menuController;
    }

    public MenuRenderer getMenuRenderer() {
        return menuRenderer;
    }

    public MenuInputHandler getMenuInputHandler() {
        return menuInputHandler;
    }

    public GameController getGameController() {
        return gameController;
    }

    public GameRenderer getGameRenderer() {
        return gameRenderer;
    }

    public GameInputHandler getGameInputHandler() {
        return gameInputHandler;
    }

}
