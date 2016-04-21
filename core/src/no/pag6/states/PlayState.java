package no.pag6.states;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.helpers.MyContactListener;
import no.pag6.models.Player;
import no.pag6.tweenaccessors.Value;
import no.pag6.tweenaccessors.ValueAccessor;
import no.pag6.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class PlayState extends State {

    private float counttime = 0.0f;
    private float playtime = 0.0f;

    // Player stats
    private int nofPlayers;

    private Player[] players;
    private int activePlayerIdx;

    // map stuff
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    // box2d stuff
    private World world;
    private Box2DDebugRenderer b2dr;
    private MyContactListener cl;

    // Renderers
    private TweenManager tweener;

    // Game objects

    // Game assets

    // Tween assets
    private Value opacityLayer1 = new Value();
    private Value opacityLayer2 = new Value();

    // Game UI
    float tempUIScale = .2f/PPM;
    private List<SimpleButton> playButtons = new ArrayList<SimpleButton>();
    private SimpleButton pauseButton;

    public PlayState(PAG6Game game, int nofPlayers, List<String> playerNames, String mapFileName) {
        super(game);
        this.nofPlayers = nofPlayers;
        players = new Player[nofPlayers];
        activePlayerIdx = 0;

        viewport.setWorldSize(A_WIDTH, A_HEIGHT);

        // load the map
        map = new TmxMapLoader().load("maps/" + mapFileName);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        // set up box2d
        world = new World(GRAVITY, true);
        b2dr = new Box2DDebugRenderer();
        cl = new MyContactListener();
        world.setContactListener(cl);
        addMapBodies();
        addPlayerBodies();

        players[activePlayerIdx].active = true;
        cl.setPlayer(players[activePlayerIdx]);

        initTweenAssets();
        initGameObjects();
        initGameAssets();

        initUI();
    }

    @Override
    public void render(float delta) {
        update(delta);

        // Clear drawings
        Gdx.gl.glClearColor(208/255f, 244/255f, 247/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawTiled();

        // Render sprites
        game.spriteBatch.setProjectionMatrix(cam.combined);
        game.spriteBatch.begin();
        game.spriteBatch.enableBlending();

        drawUI();
        for (Player player : players) {
            player.draw(game.spriteBatch);
        }

        // This should be started when game starts and in case of player change
        if (counttime < 3.5f) {
            counttime += delta;
            game.spriteBatch.draw(AssetLoader.countAnimation.getKeyFrame(counttime), cam.position.x - A_WIDTH / 2, cam.position.y - A_HEIGHT / 2, A_WIDTH, A_HEIGHT);
        }

        game.spriteBatch.end();
        //b2dr.render(world, cam.combined);
    }

    @Override
    public void update(float delta) {
        tweener.update(delta);

        if (counttime > 3.5f) {
            playtime += delta;

            world.step(TIME_STEP, 6, 2); // update physics
        }

        // update camera
        Vector2 playerPos = players[activePlayerIdx].getB2dBody().getPosition();
        if (playerPos.x < A_WIDTH / 2) {
            cam.position.x = A_WIDTH / 2;
        } else {
            cam.position.x = playerPos.x; // center the camera around the activePlayer
        }
        cam.position.y = playerPos.y; // center the camera around the activePlayer
        cam.update();
        // update the players
        for (Player player : players) {
            player.update(delta);
        }


        // Update UI
        pauseButton.setX(cam.position.x - A_WIDTH/2 + 8/PPM);
        pauseButton.setY(cam.position.y + A_HEIGHT/2 - 8/PPM);

        map.getLayers().get(FIRST_FIRST_GFX_LAYER_NAME).setOpacity(opacityLayer1.getValue());
        map.getLayers().get(FIRST_SECOND_GFX_LAYER_NAME).setOpacity(opacityLayer1.getValue());

        map.getLayers().get(SECOND_FIRST_GFX_LAYER_NAME).setOpacity(opacityLayer2.getValue());
        map.getLayers().get(SECOND_SECOND_GFX_LAYER_NAME).setOpacity(opacityLayer2.getValue());

        // update the Tiled map renderer
        mapRenderer.setView(cam);

        if (players[activePlayerIdx].getB2dBody().getPosition().y < 0) {
            // TODO: implement proper death
            players[activePlayerIdx].active = false;
            activePlayerIdx = (activePlayerIdx + 1) % nofPlayers;
            players[activePlayerIdx].active = true;
            players[activePlayerIdx].incrementFootContactCount();
            cl.setPlayer(players[activePlayerIdx]);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = viewport.unproject(touchPoint);

        pauseButton.isTouchDown(projected.x, projected.y);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = viewport.unproject(touchPoint);

        if (pauseButton.isTouchUp(projected.x, projected.y)) {
            game.getGameStateManager().pushScreen(new PauseState(game));
        }

        return true;
    }

    private void initGameObjects() {
    }

    private void initGameAssets() {
    }

    private void initTweenAssets() {
        // Register Tween Assets
        Tween.registerAccessor(Value.class, new ValueAccessor());

        tweener = new TweenManager();

        opacityLayer1.setValue(1f);
        opacityLayer2.setValue(.5f);
    }

    private void initUI() {
        TextureRegion region;
        float regionWidth, regionHeight;

        // Buttons
        region = AssetLoader.pauseButtonUp;
        regionWidth = region.getRegionWidth()*tempUIScale;
        regionHeight = region.getRegionHeight()*tempUIScale;
        pauseButton = new SimpleButton(
                0, 500/PPM + A_HEIGHT/2 - 8/PPM,
                regionWidth, regionHeight,
                AssetLoader.pauseButtonUp, AssetLoader.pauseButtonDown
        );
        playButtons.add(pauseButton);
    }

    private void drawTiled() {
        mapRenderer.render();
    }

    private void drawUI() {
        for (SimpleButton button : playButtons) {
            button.draw(game.spriteBatch);
        }
    }

    private void addMapBodies() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        // loop through the layers in the map and add box2d bodies to the box2d world
        for (int i = 0; i < LAYERS.length; i++) {
            for (PolylineMapObject polylineMapObject : map.getLayers().get(LAYERS[i]).getObjects().
                    getByType(PolylineMapObject.class)) {
                Polyline line = polylineMapObject.getPolyline();

                float[] vertices = line.getTransformedVertices();
                Vector2[] worldVertices = new Vector2[vertices.length / 2];

                for (int j = 0; j < worldVertices.length; j++) {
                    worldVertices[j] = new Vector2(vertices[j*2] / PPM, vertices[j*2+1] / PPM);
                }

                ChainShape chainShape = new ChainShape();
                chainShape.createChain(worldVertices);

                bodyDef.position.set(
                        line.getOriginX() / PPM,
                        line.getOriginY() / PPM);

                body = world.createBody(bodyDef);
                fixtureDef.shape = chainShape;
                fixtureDef.filter.categoryBits = FILTER_BITS[i];
                body.createFixture(fixtureDef);
            }
        }

        shape.dispose();
    }

    private void addPlayerBodies() {
        for (int i = 0; i < nofPlayers; i++) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(INIT_PLAYER_POS_X / PPM, INIT_PLAYER_POS_Y / PPM);
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            Body playerBody = world.createBody(bodyDef);

            // body fixture
            CircleShape shape = new CircleShape();
            shape.setRadius(PLAYER_BODY_RADIUS / PPM);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.filter.maskBits = FIRST_LAYER_BITS; // the activePlayer starts in lane 1
            playerBody.createFixture(fixtureDef);
            shape.dispose();

            // add foot fixture
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(13 / PPM, 3 / PPM, new Vector2(0, -13 / PPM), 0);
            fixtureDef.shape = polygonShape;
            fixtureDef.isSensor = true;
            playerBody.createFixture(fixtureDef).setUserData("player" + i + "foot");
            polygonShape.dispose();

            players[i] = new Player(cam, playerBody, i, i+1);
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            players[activePlayerIdx].switchLanes();

            boolean playerIsOnFirstLane = players[activePlayerIdx].isOnFirstLane();

            // Tween animations
            if (!playerIsOnFirstLane) {
                Tween.to(opacityLayer1, -1, .5f)
                        .target(.5f)
                        .ease(TweenEquations.easeOutQuad)
                        .start(tweener);
                Tween.to(opacityLayer2, -1, .5f)
                        .target(1f)
                        .ease(TweenEquations.easeOutQuad)
                        .start(tweener);
            } else {
                Tween.to(opacityLayer1, -1, .5f)
                        .target(1f)
                        .ease(TweenEquations.easeOutQuad)
                        .start(tweener);
                Tween.to(opacityLayer2, -1, .5f)
                        .target(.5f)
                        .ease(TweenEquations.easeOutQuad)
                        .start(tweener);
            }
        }

        if (keycode == Input.Keys.R) {
            game.getGameStateManager().setScreen(new PlayState(game, 1, null, "Map1.tmx"));
        }

        return true;
    }

}
