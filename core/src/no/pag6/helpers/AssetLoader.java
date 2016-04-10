package no.pag6.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Preferences prefs;

    // Textures and TextureRegions
    public static Texture logoTexture, playButtonTexture;
    public static TextureRegion logo, playButtonUp, playButtonDown;

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
        logoTexture = new Texture(Gdx.files.internal("textures/logo.png"));
        logoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        logo = new TextureRegion(logoTexture, 0, 0, 512, 512);

        playButtonTexture = new Texture(Gdx.files.internal("textures/play_button.png"));
        playButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playButtonUp = new TextureRegion(playButtonTexture, 0, 0, 256, 128);
        playButtonUp.flip(false, true);
        playButtonDown = new TextureRegion(playButtonTexture, 0, 129, 256, 128);
        playButtonDown.flip(false, true);

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
        logoTexture.dispose();
        playButtonTexture.dispose();
    }

    public static int getHighScore() {
        return prefs.getInteger("high_score");
    }
    public static void setHighScore(int val) {
        prefs.putInteger("high_score", val);
        prefs.flush();
    }

}
