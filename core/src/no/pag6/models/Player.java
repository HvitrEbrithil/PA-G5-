package no.pag6.models;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
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
    private String map;
    private int mapDifficulty = 1;
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
    private boolean shouldSwitchFilterBits;

    private float originWidth = 75/PPM;
    private float originHeight = 100/PPM;

    // Tween
    private TweenManager tweener;
    private Value playerScale = new Value();

    private boolean finished;
    private final static Vector2 MOVEMENT_IMPULSE = new Vector2(0, 0);

    public Player(OrthographicCamera cam, Body b2dBody, int id, String name, int characterType) {
        this.cam = cam;
        this.b2dBody = b2dBody;
        this.id = id;
        this.name = name;
        nofLives = 3;
        this.map = MAP_EASY_1_NAME;

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

    public void setB2dBody(Body b2dBody) {
        this.b2dBody = b2dBody;
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

        nofLives -= 1;

        // reset filter bits
        Filter filter = b2dBody.getFixtureList().get(1).getFilterData();
        filter.maskBits = FIRST_LAYER_BITS;
        b2dBody.getFixtureList().get(1).setFilterData(filter); // foot
        filter.maskBits |= GOAL_LAYER_BITS; // make sure the player can collide with goal layer
        b2dBody.getFixtureList().get(0).setFilterData(filter);
        onFirstLane = true;
    }

    public int getHighscore() {
        List<String> highscorePlayers = Arrays.asList(al.getHighscorePlayers().split(","));
        List<String> highscores = Arrays.asList(al.getHighscores().split(","));
        for (int i = 0; i < highscores.size(); i++) {
            if (highscorePlayers.get(i).equals(name)) {
                return Integer.valueOf(highscores.get(i));
            }
        }

        return 0;
    }
    public void setHighscore() {
        int playerIndex = -1;

        ArrayList<String> highscorePlayers = new ArrayList<String>(Arrays.asList(al.getHighscorePlayers().split(",")));
        ArrayList<String> highscores = new ArrayList<String>(Arrays.asList(al.getHighscores().split(",")));
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
        boolean scoreSet = false;
        if (!highscorePlayers.get(0).equals("")) {
            for (int i = 0; i < highscores.size(); i++) {
                if (score > Integer.valueOf(highscores.get(i))) {
                    highscorePlayers.add(i, name);
                    highscores.add(i, String.valueOf(score));
                    scoreSet = true;
                    break;
                }
            }
        }
        if (!scoreSet) {
            highscorePlayers.add(name);
            highscores.add(String.valueOf(score));
        }

        // Push to prefs
        al.setHighscorePlayers(joinStrings(highscorePlayers, ","));
        al.setHighscores(joinStrings(highscores, ","));
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

    public int getNofLives() {
        return nofLives;
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
            float velChange = (PLAYER_MAX_VELOCITY - b2dBody.getLinearVelocity().x)*(0.9f + ((float)(mapDifficulty)*0.1f));
            MOVEMENT_IMPULSE.x = b2dBody.getMass() * velChange;
            b2dBody.applyLinearImpulse(MOVEMENT_IMPULSE, b2dBody.getWorldCenter(), true);


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




        setScore((- (int) 1155/100 + (int)b2dBody.getPosition().x)*10);

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

        if (al.getSoundOn()) {
            al.swooshSound.play(0.3f);
        }
    }

    public void jump() {
        // jump
        if (footContactCount > 0) {
            b2dBody.setLinearVelocity(PLAYER_MAX_VELOCITY, 6);
        }
    }

    public void setMap() {
        if (this.map == MAP_EASY_1_NAME) {
            this.map = MAP_MED_1_NAME;
        } else if (this.map == MAP_MED_1_NAME) {
            this.map = MAP_HARD_1_NAME;
        } else if (this.map == MAP_HARD_1_NAME) {
            this.map = MAP_EASY_1_NAME;
        }
        this.mapDifficulty += 2;
    }

    public String getMap() {
        return map;
    }

    public float getMapDifficulty() {
        return mapDifficulty;
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

    private String joinStrings(List<String> stringArray, String delimiter) {
        StringBuilder sbStr = new StringBuilder();
        for (int i = 0, il = stringArray.size(); i < il; i++) {
            if (i > 0)
                sbStr.append(delimiter);
            sbStr.append(stringArray.get(i));
        }

        return sbStr.toString();
    }

}
