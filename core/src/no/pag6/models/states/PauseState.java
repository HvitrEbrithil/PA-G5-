package no.pag6.models.states;

import no.pag6.game.PAG6Game;

public class PauseState extends State {

    PAG6Game game;

    public PauseState(PAG6Game game) {
        this.game = game;
    }
}
