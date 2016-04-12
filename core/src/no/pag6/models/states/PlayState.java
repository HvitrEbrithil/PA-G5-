package no.pag6.models.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import no.pag6.controllers.GameController;
import no.pag6.game.PAG6Game;
import no.pag6.views.GameRenderer;

public class PlayState extends State {

    private GameController gameController;
    private GameRenderer gameRenderer;

    // camera stuff
    OrthographicCamera gameCamera;
    Viewport viewPort;
    int V_WIDTH = 800, V_HEIGHT = 480;
    float PPM = 100;

    // map stuff
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;

    // box2d stuff
    World world;
    Box2DDebugRenderer b2dr;
    Body playerBody;
    final static Vector2 gravity = new Vector2(0, -10);

    // constants
    final static String MAP_FILE_NAME = "test_lvl.tmx", FIRST_LAYER_NAME = "obj_first",
            SECOND_LAYER_NAME = "obj_second", FIRST_GFX_LAYER_NAME = "gfx_first",
            SECOND_GFX_LAYER_NAME = "gfx_second";
    final static String[] LAYERS = {FIRST_LAYER_NAME, SECOND_LAYER_NAME};

    final static short FIRST_LAYER_BITS = 2, SECOND_LAYER_BITS = 4;
    final static short[] FILTER_BITS = {FIRST_LAYER_BITS, SECOND_LAYER_BITS};

    final static int INIT_PLAYER_POS_X = 20, INIT_PLAYER_POS_Y = 200,
                    PLAYER_BODY_RADIUS = 10;

    final static float TIME_STEP = 1/60f, PLAYER_MAX_VELOCITY = 2;

    final static Vector2 MOVEMENT_IMPULSE = new Vector2(0.1f, 0),
            JUMP_IMPULSE = new Vector2(0, 6f);

    public PlayState(PAG6Game game, int nofPlayers) {
        gameController = game.getGameController();
        gameController.setNofPlayers(nofPlayers);
        gameRenderer = game.getGameRenderer();
        Gdx.input.setInputProcessor(game.getGameInputHandler());

        // set up the camera
        gameCamera = new OrthographicCamera();
        viewPort = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM, gameCamera);

        // load the map
        map = new TmxMapLoader().load("maps/" + MAP_FILE_NAME);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        // set up box2d
        world = new World(gravity, true);
        b2dr = new Box2DDebugRenderer();
        addMapBodies();
        addPlayerBody();
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world, gameCamera.combined);

        mapRenderer.render();
        //gameController.update(delta);
        //gameRenderer.render(delta);
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
                        (rect.getX() + rect.getWidth() / 2) / PPM,
                        (rect.getY() + rect.getHeight() / 2) / PPM);
                body = world.createBody(bodyDef);
                shape.setAsBox(rect.getWidth() / (2*PPM), rect.getHeight() / (2*PPM));
                fixtureDef.shape = shape;
                fixtureDef.filter.categoryBits = FILTER_BITS[i];
                body.createFixture(fixtureDef);
            }
        }
        shape.dispose();
    }

    private void addPlayerBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(INIT_PLAYER_POS_X / PPM, INIT_PLAYER_POS_Y / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(PLAYER_BODY_RADIUS / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.maskBits = FIRST_LAYER_BITS; // the player starts in lane 1
        playerBody.createFixture(fixtureDef);

        shape.dispose();
    }

    private void update(float delta) {
        handleInput(delta);
        world.step(TIME_STEP, 6, 2);

        gameCamera.position.x = playerBody.getPosition().x; // center the camera around the player
        gameCamera.update();

        mapRenderer.setView(gameCamera);
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) &&
                playerBody.getLinearVelocity().x <= PLAYER_MAX_VELOCITY) {
            playerBody.applyLinearImpulse(MOVEMENT_IMPULSE, playerBody.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            switchLanes();
        }
    }

    private void switchLanes() {
        // make the player jump
        playerBody.applyLinearImpulse(JUMP_IMPULSE, playerBody.getWorldCenter(), true);

        // change the collision bits
        Filter filter = playerBody.getFixtureList().first().getFilterData();
        filter.maskBits = SECOND_LAYER_BITS;
        playerBody.getFixtureList().first().setFilterData(filter);

        // scale the player
        playerBody.getFixtureList().first().getShape().setRadius(5 / PPM);

        // change the map layer opacities
        map.getLayers().get(FIRST_GFX_LAYER_NAME).setOpacity(1);
        map.getLayers().get(SECOND_GFX_LAYER_NAME).setOpacity(0.5f);
    }

}
