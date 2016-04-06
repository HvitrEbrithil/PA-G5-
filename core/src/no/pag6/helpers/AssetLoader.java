package no.pag6.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AssetLoader {

    public static Preferences prefs;

    // Textures and TextureRegions
//    public static Texture ;
//    public static TextureRegion ;

    // Animations
//    public static Animation ;

    // Sounds
//    public static Sound ;

    // Fonts
//    public static BitmapFont ;

    public static void load() {
        // Preferences
        prefs = Gdx.app.getPreferences("PAG6Game");
        if (!prefs.contains("high_score")) {
            prefs.putInteger("high_score", 0);
        }

        // Textures and TextureRegions
//         = new Texture(Gdx.files.internal("textures/.png"));
//        .setFilter(TextureFilter.Linear, TextureFilter.Linear);

//         = new TextureRegion(, 0, 0);
//        .flip(false, true);

        // Animations
//        TextureRegion[]  = {};
//         = new Animation(0.06f, );
//        .setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        // Sounds
//         = Gdx.audio.newSound(Gdx.files.internal("sounds/.wav"));

        // Fonts
//         = new BitmapFont(Gdx.files.internal("fonts/.fnt"));
//        .getData().setScale(.25f, -.25f);
    }

    public static void dispose() {
//        .dispose();
    }

    public static int getHighScore() {
        return prefs.getInteger("high_score");
    }
    public static void setHighScore(int val) {
        prefs.putInteger("high_score", val);
        prefs.flush();
    }

}
