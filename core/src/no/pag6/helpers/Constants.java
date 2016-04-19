package no.pag6.helpers;

import com.badlogic.gdx.math.Vector2;

public interface Constants {

    // Screen and viewport
    int V_WIDTH = 1280, V_HEIGHT = 720;
    float PPM = 100;
    int A_WIDTH = (int) (V_WIDTH/PPM), A_HEIGHT = (int) (V_HEIGHT/PPM);
    float TIME_STEP = 1/60f;

    // UI
    float UI_SCALE = 0.4f;

    // Play state
    Vector2 GRAVITY = new Vector2(0, -10),
            MOVEMENT_IMPULSE = new Vector2(0.1f, 0),
            JUMP_IMPULSE = new Vector2(0, 6f);

    String FIRST_LAYER_NAME = "lane1_collision", SECOND_LAYER_NAME = "lane2_collision",
            FIRST_FIRST_GFX_LAYER_NAME = "lane1_ground1",
            FIRST_SECOND_GFX_LAYER_NAME = "lane1_ground2",
            SECOND_FIRST_GFX_LAYER_NAME = "lane2_ground1",
            SECOND_SECOND_GFX_LAYER_NAME = "lane2_ground2";

    String[] LAYERS = {FIRST_LAYER_NAME, SECOND_LAYER_NAME};

    short FIRST_LAYER_BITS = 2, SECOND_LAYER_BITS = 4;
    short[] FILTER_BITS = {FIRST_LAYER_BITS, SECOND_LAYER_BITS};

    int INIT_PLAYER_POS_X = 20, INIT_PLAYER_POS_Y = 600, PLAYER_BODY_RADIUS = 10;

    float PLAYER_MAX_VELOCITY = 2;

}
