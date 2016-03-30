package no.pag6.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Preferences prefs;

    public static Texture logo_texture;
    public static TextureRegion logo, bird, bird_down, bird_up;

    public static Animation bird_animation;

    public static Sound dead;

    public static BitmapFont font, shadow;

    public static void load() {
        // Preferences
        prefs = Gdx.app.getPreferences("PAG6Game");
        if (!prefs.contains("high_score")) {
            prefs.putInteger("high_score", 0);
        }

        // Textures and TextureRegions
        logo_texture = new Texture(Gdx.files.internal("textures/badlogic.png"));
        logo_texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        logo = new TextureRegion(logo_texture, 0, 0);

        bird_down = new TextureRegion(logo_texture, 0, 0, 10, 10);
        bird_down.flip(false, true);

        bird = new TextureRegion(logo_texture, 0, 0, 50, 50);
        bird.flip(false, true);

        bird_up = new TextureRegion(logo_texture, 0, 0, 100, 100);
        bird_up.flip(false, true);

        TextureRegion[] birds = {bird_down, bird, bird_up};
        bird_animation = new Animation(0.06f, birds);
        bird_animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        // Sounds
        dead = Gdx.audio.newSound(Gdx.files.internal("sounds/dead.wav"));

        // Fonts
        font = new BitmapFont(Gdx.files.internal("fonts/text.fnt"));
        font.getData().setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("fonts/shadow.fnt"));
        shadow.getData().setScale(.25f, -.25f);
    }

    public static void dispose() {
        logo_texture.dispose();
        dead.dispose();
        font.dispose();
        shadow.dispose();
    }

    public static int getHighScore() {
        return prefs.getInteger("high_score");
    }
    public static void setHighScore(int val) {
        prefs.putInteger("high_score", val);
        prefs.flush();
    }

}
