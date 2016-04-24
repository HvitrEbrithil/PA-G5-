package no.pag6.helpers;

import com.badlogic.gdx.physics.box2d.*;
import no.pag6.models.Player;

public class MyContactListener implements ContactListener {

    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void beginContact(Contact contact) {
        handleContact(contact, true);
    }

    @Override
    public void endContact(Contact contact) {
        handleContact(contact, false);
    }

    private void handleContact(Contact contact, boolean begin) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (((fixtureA.getUserData() != null && fixtureA.getUserData().equals("player" + player.getId() + "foot"))
                || fixtureB.getUserData() != null && fixtureB.getUserData().equals("player" + player.getId() + "foot"))
                && player.isActive()) {
            if (begin) {
                player.incrementFootContactCount();
            } else {
                player.decrementFootContactCount();
            }
        }

        if (((fixtureA.getUserData() != null && fixtureA.getUserData().equals("goal"))
                || (fixtureB.getUserData() != null && fixtureB.getUserData().equals("goal")))) {
            player.setFinished(true);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

}
