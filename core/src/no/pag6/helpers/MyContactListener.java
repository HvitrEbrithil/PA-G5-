package no.pag6.helpers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Tobias on 18.04.2016.
 */
public class MyContactListener implements ContactListener {
    private int numFootContacts;

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

        if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("foot")) {
            numFootContacts += begin ? 1 : -1;
        }
        if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("foot")) {
            numFootContacts += begin ? 1 : -1;
        }
    }

    public boolean isPlayerOnGround() {
        return numFootContacts > 0;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
