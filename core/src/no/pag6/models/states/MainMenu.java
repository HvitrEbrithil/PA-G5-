package no.pag6.models.states;

import com.badlogic.gdx.Gdx;
import no.pag6.controllers.MenuController;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.MenuInputHandler;
import no.pag6.views.MenuRenderer;

public class MainMenu extends State {

    private PAG6Game game;
    private MenuController menuController;
    private MenuRenderer menuRenderer;

    public MainMenu(PAG6Game game) {
        this.game = game;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 2560;
        float gameHeight = screenHeight/(screenWidth/gameWidth);

        menuController = new MenuController();
        Gdx.input.setInputProcessor(new MenuInputHandler(game, menuController,
                screenWidth/gameWidth, screenHeight/gameHeight));
        menuRenderer = new MenuRenderer(menuController);
    }

    @Override
    public void render(float delta) {
        menuController.update(delta);
        menuRenderer.render(delta);
    }

}
