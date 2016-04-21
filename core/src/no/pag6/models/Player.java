package no.pag6.models;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import no.pag6.helpers.Constants;
import no.pag6.tweenaccessors.Value;
import no.pag6.tweenaccessors.ValueAccessor;

public class Player extends Sprite implements Constants {

    private int score;
    private boolean onFirstLane;
    private Body b2dBody;
    private Texture texture;
    public boolean active = false;
    private int footContactCount;
    private int id;
    private int nofLives;
    private boolean shouldSwitchFilterBits;
    private boolean lanesSwitched = false;

    private float originWidth = 70/PPM;
    private float originHeight = 86/PPM;

    // Tween
    private TweenManager tweener;
    private Value playerScale = new Value();

    public Player(Body b2dBody, int id) {
        this.b2dBody = b2dBody;
        this.id = id;
        nofLives = 3;

        score = 0;
        onFirstLane = true;
        shouldSwitchFilterBits = false;
        texture = new Texture("textures/player1.png");

        setBounds(0, 0, originWidth, originHeight);
        setRegion(texture);

        initTweenAssets();
    }

    private void initTweenAssets() {
        // Register Tween Assets
        Tween.registerAccessor(Value.class, new ValueAccessor());

        tweener = new TweenManager();

        playerScale.setValue(1f);
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
        tweener.update(delta);

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
            lanesSwitched = false;
        }

        // Scale player
        float scaledWidth = originWidth*playerScale.getValue();
        float scaledHeight = originHeight*playerScale.getValue();

        // Scale sprite
        setScale(playerScale.getValue());
//        setSize(scaledWidth, scaledHeight);

        // Update position
        setPosition(b2dBody.getPosition().x - scaledWidth/2, b2dBody.getPosition().y - scaledHeight/2);
//        setPosition(b2dBody.getPosition().x - getWidth()/2, b2dBody.getPosition().y - getHeight()/2);
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void switchLanes() {
        // jump
        if (footContactCount > 0) {
            b2dBody.applyLinearImpulse(JUMP_IMPULSE, b2dBody.getWorldCenter(), true);
        }

        shouldSwitchFilterBits = !shouldSwitchFilterBits;
        lanesSwitched = true;

        // Sprite
//        scaleFixtures(onFirstLane ? .7f : 1f);
        tweenPlayer(onFirstLane ? .7f : 1f);

        onFirstLane = !onFirstLane;
    }

    // TODO: Fix this function if we have the time
    // Current bug: Switching two times fast with this function messes with the maskBits
//    private void scaleFixtures(float scale) {
//        // Remove old fixtures
//        Fixture bodyFixture = b2dBody.getFixtureList().first();
//        Fixture footFixture = b2dBody.getFixtureList().get(1);
//        b2dBody.destroyFixture(bodyFixture);
//        b2dBody.destroyFixture(footFixture);
//
//        // Create new body fixture
//        CircleShape newBodyShape = new CircleShape();
//        newBodyShape.setRadius((PLAYER_BODY_RADIUS/PPM)*scale);
//
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = newBodyShape;
//        fixtureDef.filter.maskBits = onFirstLane ? FIRST_LAYER_BITS : SECOND_LAYER_BITS;
//        b2dBody.createFixture(fixtureDef);
//        newBodyShape.dispose();
//
//        // Create new foot fixture
//        PolygonShape newFootShape = new PolygonShape();
//        newFootShape.setAsBox((13/PPM)*scale, (3/PPM)*scale, new Vector2(0, (-13/PPM)*scale), 0);
//
//        fixtureDef.shape = newFootShape;
//        fixtureDef.isSensor = true;
//        b2dBody.createFixture(fixtureDef).setUserData("player" + id + "foot");
//        newFootShape.dispose();
//    }

    private void tweenPlayer(float scale) {
        if (onFirstLane) {
            Tween.to(playerScale, -1, .5f)
                    .target(scale)
                    .ease(TweenEquations.easeOutQuad)
                    .start(tweener);
        } else {
            Tween.to(playerScale, -1, .5f)
                    .target(1f)
                    .ease(TweenEquations.easeOutQuad)
                    .start(tweener);
        }
    }

}
