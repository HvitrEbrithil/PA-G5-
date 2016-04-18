package no.pag6.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;

import no.pag6.helpers.Constants;

public class Player extends Sprite implements Constants {
    private int score;
    private boolean onFirstLane;
    private Body b2dBody;

    public Player(Body b2dBody) {
        this.b2dBody = b2dBody;
        score = 0;
        onFirstLane = true;
    }

    public void setScore(int points){
        score = points;
    }

    public int getScore() {
        return score;
    }

    public boolean isOnFirstLane() {
        return onFirstLane;
    }

    public Body getB2dBody() {
        return b2dBody;
    }

    public void update(float delta) {

        Vector2 vel = b2dBody.getLinearVelocity();
        float desiredVel = 2;
        float velChange = desiredVel - vel.x;
        float impulse = b2dBody.getMass() * velChange;
        b2dBody.applyLinearImpulse(new Vector2(impulse, 0), b2dBody.getWorldCenter(), true);
    }

    public void render(float delta) {

    }

    public void switchLanes() {
        // jump
        b2dBody.applyLinearImpulse(JUMP_IMPULSE, b2dBody.getWorldCenter(), true);

        // set filter bits
        Filter filter = b2dBody.getFixtureList().first().getFilterData();
        filter.maskBits = isOnFirstLane() ? SECOND_LAYER_BITS : FIRST_LAYER_BITS;
        b2dBody.getFixtureList().first().setFilterData(filter);
        b2dBody.getFixtureList().get(1).setFilterData(filter);

        // scale
        b2dBody.getFixtureList().first().getShape().setRadius(isOnFirstLane() ? 5 / PPM : 10 / PPM);

        onFirstLane = !onFirstLane;
    }
}
