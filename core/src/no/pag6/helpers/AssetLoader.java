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

    public static Texture texture, logo_texture;
    public static TextureRegion logo, bird, bird_down, bird_up;

    public static Animation bird_animation;

    public static Sound dead;

    public static BitmapFont font, shadow;

    public static void load() {
        // Preferences
        prefs = Gdx.app.getPreferences("ZombieBird");
        if (!prefs.contains("high_score")) {
            prefs.putInteger("high_score", 0);
        }

        // Textures & TextureRegions
        logo_texture = new Texture(Gdx.files.internal("data/logo.png"));
        logo_texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        logo = new TextureRegion(logo_texture, 0, 0, 512, 114);

        bird_down = new TextureRegion(texture, 136, 0, 17, 12);
        bird_down.flip(false, true);

        bird = new TextureRegion(texture, 153, 0, 17, 12);
        bird.flip(false, true);

        bird_up = new TextureRegion(texture, 170, 0, 17, 12);
        bird_up.flip(false, true);

        TextureRegion[] birds = {bird_down, bird, bird_up};
        bird_animation = new Animation(0.06f, birds);
        bird_animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        // Sounds
        dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));

        // Fonts
        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.getData().setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.getData().setScale(.25f, -.25f);
    }

    public static void dispose() {
        texture.dispose();
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
