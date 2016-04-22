package no.pag6.models;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import no.pag6.helpers.AssetLoader;
import no.pag6.helpers.Constants;
import no.pag6.tweenaccessors.Value;
import no.pag6.tweenaccessors.ValueAccessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    // TODO
    private int nofLives;
    private boolean isKilled = false;
    private boolean shouldSwitchFilterBits;

    private float originWidth = 75/PPM;
    private float originHeight = 100/PPM;

    // Tween
    private TweenManager tweener;
    private Value playerScale = new Value();

    private boolean finished;

    public Player(OrthographicCamera cam, Body b2dBody, int id, String name, int characterType) {
        this.cam = cam;
        this.b2dBody = b2dBody;
        this.id = id;
        this.name = name;
        nofLives = 3;

        score = 0;
        onFirstLane = true;
        shouldSwitchFilterBits = false;

        playerCharacter = new PlayerCharacter(characterType);
        playerTexture = playerCharacter.getTexture();
        playerAnimation = playerCharacter.getAnimation();

        setBounds(0, 0, originWidth, originHeight);
        setRegion(playerTexture);

        initTweenAssets();
    }

    private void initTweenAssets() {
        // Register Tween Assets
        Tween.registerAccessor(Value.class, new ValueAccessor());

        tweener = new TweenManager();

        playerScale.setValue(1f);
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public void kill() {
        if (score > getHighscore()) {
            setHighscore();
        }

        isKilled = true;
    }

    public boolean isKilled() {
        return isKilled;
    }

    public int getHighscore() {
        List<String> highscorePlayers = Arrays.asList(AssetLoader.getHighscorePlayers().split(","));
        List<String> highscores = Arrays.asList(AssetLoader.getHighscores().split(","));
        for (int i = 0; i < highscores.size(); i++) {
            if (highscorePlayers.get(i).equals(name)) {
                return Integer.valueOf(highscores.get(i));
            }
        }

        return 0;
    }
    public void setHighscore() {
        int playerIndex = -1;

        ArrayList<String> highscorePlayers = new ArrayList<String>(Arrays.asList(AssetLoader.getHighscorePlayers().split(",")));
        ArrayList<String> highscores = new ArrayList<String>(Arrays.asList(AssetLoader.getHighscores().split(",")));
        Gdx.app.log("HS", highscorePlayers.toString());
        Gdx.app.log("HS", highscores.toString());
        for (int i = 0; i < highscores.size(); i++) {
            if (highscorePlayers.get(i).equals(name)) {
                playerIndex = i;
            }
        }

        // Remove existing highscore
        if (playerIndex >= 0) {
            highscorePlayers.remove(playerIndex);
            highscores.remove(playerIndex);
        }

        // Add new highscore
        if (!highscorePlayers.get(0).equals("")) {
            for (int i = 0; i < highscores.size(); i++) {
                if (score > Integer.valueOf(highscores.get(i))) {
                    highscorePlayers.add(i, name);
                    highscores.add(i, String.valueOf(score));
                    break;
                }
            }
        } else {
            highscorePlayers.add(name);
            highscores.add(String.valueOf(score));
        }

        // Push to prefs
        AssetLoader.setHighscorePlayers(String.join(",", highscorePlayers));
        AssetLoader.setHighscores(String.join(",", highscores));
    }

    public int getScore() {
        return score;
    }
    public void incrementScore(int points) {
        score += points;
    }
    public void setScore(int score){
        this.score = score;
    }

    public boolean isOnFirstLane() {
        return onFirstLane;
    }

    public Body getB2dBody() {
        return b2dBody;
    }

    public void update(float delta) {
        playtime += delta;

        tweener.update(delta);

        if (active) {
            Vector2 vel = b2dBody.getLinearVelocity();
            float desiredVel = PLAYER_MAX_VELOCITY;
            float velChange = desiredVel - vel.x;
            float impulse = b2dBody.getMass() * velChange;
            b2dBody.applyLinearImpulse(new Vector2(impulse, 0), b2dBody.getWorldCenter(), true);

            // TODO: Move to wherever when score-system is implemented
            incrementScore(1);
        }

        if (shouldSwitchFilterBits && b2dBody.getLinearVelocity().y <= 0) {
            // set filter bits
            Filter filter = b2dBody.getFixtureList().get(1).getFilterData();
            boolean wasFirst = filter.maskBits == FIRST_LAYER_BITS;
            filter.maskBits = wasFirst ? SECOND_LAYER_BITS : FIRST_LAYER_BITS;
            b2dBody.getFixtureList().get(1).setFilterData(filter); // foot
            filter.maskBits |= GOAL_LAYER_BITS;
            b2dBody.getFixtureList().get(0).setFilterData(filter);
            shouldSwitchFilterBits = false;
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
        batch.draw(playerAnimation.getKeyFrame(playtime), getX(), getY(), 75/PPM, 100/PPM);
    }

    public void switchLanes() {
        // jump and switch
        if (footContactCount > 0) {
            b2dBody.setLinearVelocity(PLAYER_MAX_VELOCITY, 6);
        }

        shouldSwitchFilterBits = !shouldSwitchFilterBits;

        // Sprite
//        scaleFixtures(onFirstLane ? .7f : 1f);
        tweenPlayer(onFirstLane ? .7f : 1f);

        onFirstLane = !onFirstLane;

        if (AssetLoader.getSoundOn()) {
            AssetLoader.swooshSound.play(0.3f);
        }
    }

    public void jump() {
        // jump
        if (footContactCount > 0) {
            b2dBody.setLinearVelocity(PLAYER_MAX_VELOCITY, 6);
        }
    }

//    // TODO: Fix this function if we have the time
//    // Current bug: Switching two times fast with this function messes with the maskBits
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
