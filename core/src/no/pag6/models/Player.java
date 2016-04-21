package no.pag6.models;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;

import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.helpers.Constants;

public class Player extends Sprite implements Constants {
    private OrthographicCamera cam;
    private String name;
    private float playtime = 0.0f;
    private int score;
    private boolean onFirstLane;
    private PlayerCharacter playerCharacter;
    private Texture playerTexture;
    private Animation playerAnimation;
    private Body b2dBody;
    public boolean active = false;
    private int footContactCount;
    private int id;
    private int nofLives;
    private boolean shouldSwitchFilterBits;

    public Player(OrthographicCamera cam, Body b2dBody, int id, int characterType) {
        this.cam = cam;
        this.b2dBody = b2dBody;
        this.id = id;
        nofLives = 3;

        score = 0;
        onFirstLane = true;
        shouldSwitchFilterBits = false;

        playerCharacter = new PlayerCharacter(characterType);
        playerTexture = playerCharacter.getTexture();
        playerAnimation = playerCharacter.getAnimation();

        setBounds(0, 0, 75 / PPM, 100 / PPM);
        setRegion(playerTexture);
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
        playtime += delta;

        if (active) {
            Vector2 vel = b2dBody.getLinearVelocity();
            float desiredVel = 2;
            float velChange = desiredVel - vel.x;
            float impulse = b2dBody.getMass() * velChange;
            b2dBody.applyLinearImpulse(new Vector2(impulse, 0), b2dBody.getWorldCenter(), true);
        }

        if (shouldSwitchFilterBits && b2dBody.getLinearVelocity().y <= 0) {
            // set filter bits
            Filter filter = b2dBody.getFixtureList().first().getFilterData();
            boolean wasFirst = filter.maskBits == FIRST_LAYER_BITS;
            filter.maskBits = wasFirst ? SECOND_LAYER_BITS : FIRST_LAYER_BITS;
            b2dBody.getFixtureList().first().setFilterData(filter);
            b2dBody.getFixtureList().get(1).setFilterData(filter); // foot
            shouldSwitchFilterBits = false;
        }

        setPosition(b2dBody.getPosition().x - getWidth() / 2, b2dBody.getPosition().y - getHeight() / 2);
    }

    public void draw(Batch batch) {
        batch.draw(playerAnimation.getKeyFrame(playtime), getX(), getY(), 75/PPM, 100/PPM);
    }

    public void switchLanes() {
        // jump
        if (footContactCount > 0) {
            b2dBody.applyLinearImpulse(JUMP_IMPULSE, b2dBody.getWorldCenter(), true);
        }

        shouldSwitchFilterBits = ! shouldSwitchFilterBits;

        // scale
        // TODO: implement scaling as: 1) remove fixtures but remember size 2) add new, scaled fixtures to the body
        onFirstLane = !onFirstLane;
    }
}
