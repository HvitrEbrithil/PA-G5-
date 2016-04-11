package no.pag6.models.states;

import com.badlogic.gdx.Gdx;
import no.pag6.controllers.MenuController;
import no.pag6.game.PAG6Game;
import no.pag6.views.MenuRenderer;

public class MainMenu extends State {

    private MenuController menuController;
    private MenuRenderer menuRenderer;

    public MainMenu(PAG6Game game) {
        menuController = game.getMenuController();
        menuRenderer = game.getMenuRenderer();
        Gdx.input.setInputProcessor(game.getMenuInputHandler());
    }

    @Override
    public void render(float delta) {
        menuController.update(delta);
        menuRenderer.render(delta);
    }

}
