package no.pag6.states;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class PlayState extends State {

    // Renderers
    private ShapeRenderer drawer;
    private TweenManager tweener;

    // Player stats
    private int nofPlayers;

    // map stuff
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    // box2d stuff
    private World world;
    private Box2DDebugRenderer b2dr;
    private Body playerBody;

    // constants
    private final static Vector2 gravity = new Vector2(0, -10),
            MOVEMENT_IMPULSE = new Vector2(0.1f, 0),
            JUMP_IMPULSE = new Vector2(0, 6f);

    final static String FIRST_LAYER_NAME = "obj_first",
            SECOND_LAYER_NAME = "obj_second", FIRST_GFX_LAYER_NAME = "gfx_first",
            SECOND_GFX_LAYER_NAME = "gfx_second";
    final static String[] LAYERS = {FIRST_LAYER_NAME, SECOND_LAYER_NAME};

    final static short FIRST_LAYER_BITS = 2, SECOND_LAYER_BITS = 4;
    final static short[] FILTER_BITS = {FIRST_LAYER_BITS, SECOND_LAYER_BITS};

    private final static int INIT_PLAYER_POS_X = 20, INIT_PLAYER_POS_Y = 200,
                    PLAYER_BODY_RADIUS = 10;

    final static float PLAYER_MAX_VELOCITY = 2;

    // Game objects

    // Game assets

    // Tween assets

    // Game UI
    private List<SimpleButton> playButtons = new ArrayList<SimpleButton>();
    private SimpleButton pauseButton;

    public PlayState(PAG6Game game, int nofPlayers, String mapFileName) {
        super(game);
        this.nofPlayers = nofPlayers;

        // Set up drawer and batcher
        drawer = new ShapeRenderer();
        drawer.setProjectionMatrix(cam.combined);

        // load the map
        map = new TmxMapLoader().load("maps/" + mapFileName);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / game.PPM);

        // set up box2d
        world = new World(gravity, true);
        b2dr = new Box2DDebugRenderer();
        addMapBodies();
        addPlayerBody();

        // Init objects and assets
        initTweenAssets();

        initGameObjects();
        initGameAssets();

        initUI();
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world, cam.combined);

        // Render sprites
        game.spriteBatch.setProjectionMatrix(cam.combined);
        game.spriteBatch.begin();
        game.spriteBatch.enableBlending();

        drawTiled();
        drawUI();

        game.spriteBatch.end();
    }

    @Override
    public void update(float delta) {
        world.step(game.TIME_STEP, 6, 2);

        cam.position.x = playerBody.getPosition().x; // center the camera around the player
        cam.update();

        mapRenderer.setView(cam);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = cam.unproject(touchPoint);
        screenX = (int) touchPoint.x;
        screenY = (int) touchPoint.y;
        pauseButton.isTouchDown(screenX, screenY);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = cam.unproject(touchPoint);
        screenX = (int) touchPoint.x;
        screenY = (int) touchPoint.y;

        if (pauseButton.isTouchUp(screenX, screenY)) {
            PauseState pauseState = new PauseState(game);
            game.gameStack.push(pauseState);
            game.setScreen(pauseState);
        } else if (playerBody.getLinearVelocity().x <= PLAYER_MAX_VELOCITY) {
            playerBody.applyLinearImpulse(MOVEMENT_IMPULSE, playerBody.getWorldCenter(), true);
        }
        // TODO: Handle laneswitching
//        if (true) {
//            switchLanes();
//        }

        return true;
    }

    private void initTweenAssets() {
        // Register Tween Assets

        tweener = new TweenManager();

        // Tween animations
    }

    private void initGameObjects() {
    }

    private void initGameAssets() {
    }

    private void initUI() {
        pauseButton = new SimpleButton(2560 - AssetLoader.optionsButtonUp.getRegionWidth() - 64, 64,
                AssetLoader.optionsButtonUp.getRegionWidth(), AssetLoader.optionsButtonUp.getRegionHeight(),
                AssetLoader.optionsButtonUp, AssetLoader.optionsButtonDown);
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
            for (MapObject mapObject : map.getLayers().get(LAYERS[i]).getObjects()
                                                .getByType(RectangleMapObject.class)) {

                Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();
                bodyDef.position.set(
                        (rect.getX() + rect.getWidth() / 2) / game.PPM,
                        (rect.getY() + rect.getHeight() / 2) / game.PPM);
                body = world.createBody(bodyDef);
                shape.setAsBox(rect.getWidth() / (2*game.PPM), rect.getHeight() / (2*game.PPM));
                fixtureDef.shape = shape;
                fixtureDef.filter.categoryBits = FILTER_BITS[i];
                body.createFixture(fixtureDef);
            }
        }
        shape.dispose();
    }

    private void addPlayerBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(INIT_PLAYER_POS_X / game.PPM, INIT_PLAYER_POS_Y / game.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(PLAYER_BODY_RADIUS / game.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.maskBits = FIRST_LAYER_BITS; // the player starts in lane 1
        playerBody.createFixture(fixtureDef);

        shape.dispose();
    }

    private void switchLanes() {
        // make the player jump
        playerBody.applyLinearImpulse(JUMP_IMPULSE, playerBody.getWorldCenter(), true);

        // change the collision bits
        Filter filter = playerBody.getFixtureList().first().getFilterData();
        filter.maskBits = SECOND_LAYER_BITS;
        playerBody.getFixtureList().first().setFilterData(filter);

        // scale the player
        playerBody.getFixtureList().first().getShape().setRadius(5 / game.PPM);

        // change the map layer opacities
        map.getLayers().get(FIRST_GFX_LAYER_NAME).setOpacity(1);
        map.getLayers().get(SECOND_GFX_LAYER_NAME).setOpacity(0.5f);
    }

}
