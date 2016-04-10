package no.pag6.models.states;

import com.badlogic.gdx.Gdx;
import no.pag6.controllers.GameController;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.GameInputHandler;
import no.pag6.views.GameRenderer;

public class PlayState extends State {

    private PAG6Game game;
    private GameController gameController;
    private GameRenderer gameRenderer;

    public PlayState(PAG6Game game) {
        this.game = game;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 2560;
        float gameHeight = screenHeight/(screenWidth/gameWidth);

        gameController = new GameController(1); // TODO: Determine number of players here
        Gdx.input.setInputProcessor(new GameInputHandler(game, gameController,
                screenWidth/gameWidth, screenHeight/gameHeight));
        gameRenderer = new GameRenderer(gameController);
    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
        gameRenderer.render(delta);
    }

}
