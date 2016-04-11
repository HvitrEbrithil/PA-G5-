package no.pag6.models.states;

import com.badlogic.gdx.Gdx;
import no.pag6.controllers.GameController;
import no.pag6.game.PAG6Game;
import no.pag6.views.GameRenderer;

public class PlayState extends State {

    private GameController gameController;
    private GameRenderer gameRenderer;

    public PlayState(PAG6Game game, int nofPlayers) {
        gameController = game.getGameController();
        gameController.setNofPlayers(nofPlayers);
        gameRenderer = game.getGameRenderer();
        Gdx.input.setInputProcessor(game.getGameInputHandler());
    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
        gameRenderer.render(delta);
    }

}
