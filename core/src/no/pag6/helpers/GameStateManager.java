package no.pag6.helpers;

import com.badlogic.gdx.Screen;

import java.util.Stack;

import no.pag6.game.PAG6Game;

/**
 * Created by Tobias on 18.04.2016.
 */
public class GameStateManager {

    private PAG6Game game;
    private Stack<Screen> screens;

    public GameStateManager(PAG6Game game) {
        this.game = game;
        screens = new Stack<Screen>();
    }

    public Screen popScreen() {
        Screen popped = screens.pop();
        game.setScreen(screens.peek());
        return popped;
    }

    public void pushScreen(Screen screen) {
        screens.push(screen);
        game.setScreen(screen);
    }

    public void setScreen(Screen screen) {
        screens.pop();
        pushScreen(screen);
    }

}
