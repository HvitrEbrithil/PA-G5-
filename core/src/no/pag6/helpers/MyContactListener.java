package no.pag6.helpers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import no.pag6.models.Player;

/**
 * Created by Tobias on 18.04.2016.
 */
public class MyContactListener implements ContactListener {
    private Player player;
    int count;

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

        if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("player" + player.getId() + "foot")) {
            if (begin) {
                player.incrementFootContactCount();
            } else {
                player.decrementFootContactCount();
            }
        }
        if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("player" + player.getId() + "foot")) {
            if (begin) {
                player.incrementFootContactCount();
            } else {
                player.decrementFootContactCount();
            }
        }
    }

    public boolean isPlayerOnGround() {
        return player.getFootContactCount() > 0;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
