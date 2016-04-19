package no.pag6.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;

import no.pag6.helpers.Constants;

public class Player extends Sprite implements Constants {
    private int score;
    private boolean onFirstLane;
    private Body b2dBody;
    private Texture texture;
    public boolean active = false;
    private int footContactCount;
    private int id;

    public Player(Body b2dBody, int id) {
        this.b2dBody = b2dBody;
        this.id = id;

        score = 0;
        onFirstLane = true;
        texture = new Texture("textures/player.png");

        setBounds(0, 0, 70 / PPM, 86 / PPM);
        setRegion(texture);
    }

    public int getId() {
        return id;
    }

    public void incrementFootContactCount() {
        footContactCount++;
    }

    public void decrementFootContactCount() {
        footContactCount--;
    }

    public int getFootContactCount() {
        return footContactCount;
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
        if (active) {
            Vector2 vel = b2dBody.getLinearVelocity();
            float desiredVel = 2;
            float velChange = desiredVel - vel.x;
            float impulse = b2dBody.getMass() * velChange;
            b2dBody.applyLinearImpulse(new Vector2(impulse, 0), b2dBody.getWorldCenter(), true);
        }

        setPosition(b2dBody.getPosition().x - getWidth() / 2, b2dBody.getPosition().y - getHeight() / 2);
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void switchLanes() {
        // jump
        b2dBody.applyLinearImpulse(JUMP_IMPULSE, b2dBody.getWorldCenter(), true);

        // set filter bits
        Filter filter = b2dBody.getFixtureList().first().getFilterData();
        filter.maskBits = isOnFirstLane() ? SECOND_LAYER_BITS : FIRST_LAYER_BITS;
        b2dBody.getFixtureList().first().setFilterData(filter);
        b2dBody.getFixtureList().get(1).setFilterData(filter); // foot

        // scale
        b2dBody.getFixtureList().first().getShape().setRadius(isOnFirstLane() ? 5 / PPM : 10 / PPM);

        onFirstLane = !onFirstLane;
    }
}
