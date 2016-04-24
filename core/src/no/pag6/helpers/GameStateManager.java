package no.pag6.helpers;

import com.badlogic.gdx.Screen;
import no.pag6.game.PAG6Game;

import java.util.Stack;

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

    public Screen getScreen() {
        return screens.peek();
    }

}
