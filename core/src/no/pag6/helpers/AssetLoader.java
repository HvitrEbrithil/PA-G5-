package no.pag6.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Preferences prefs;

    // Textures and TextureRegions
    public static Texture logoTexture, playSPButtonTexture, play2PButtonTexture, mainMenuButtonTexture,
            exitButtonTexture, exitCrossButtonTexture, highscoreButtonTexture, backArrowButtonTexture,
            optionsButtonTexture;
    public static TextureRegion logo, playSPButtonUp, playSPButtonDown, play2PButtonUp, play2PButtonDown,
            mainMenuButtonUp, mainMenuButtonDown, exitButtonUp, exitButtonDown, exitCrossButton,
            highscoreButtonUp, highscoreButtonDown, backArrowButton, optionsButtonUp, optionsButtonDown;

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
        backArrowButtonTexture = new Texture(Gdx.files.internal("textures/back_arrow_button.png"));
        backArrowButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backArrowButton = new TextureRegion(backArrowButtonTexture, 0, 0, 256, 256);

        exitButtonTexture = new Texture(Gdx.files.internal("textures/exit_button.png"));
        exitButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        exitButtonUp = new TextureRegion(exitButtonTexture, 0, 0, 512, 128);
        exitButtonDown = new TextureRegion(exitButtonTexture, 0, 129, 512, 128);

        exitCrossButtonTexture = new Texture(Gdx.files.internal("textures/exit_cross_button.png"));
        exitCrossButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        exitCrossButton = new TextureRegion(exitCrossButtonTexture, 0, 0, 256, 256);

        highscoreButtonTexture = new Texture(Gdx.files.internal("textures/highscore_button.png"));
        highscoreButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        highscoreButtonUp = new TextureRegion(highscoreButtonTexture, 0, 0, 512, 128);
        highscoreButtonDown = new TextureRegion(highscoreButtonTexture, 0, 129, 512, 128);

        logoTexture = new Texture(Gdx.files.internal("textures/logo.png"));
        logoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        logo = new TextureRegion(logoTexture, 0, 0, 512, 512);

        mainMenuButtonTexture = new Texture(Gdx.files.internal("textures/main_menu_button.png"));
        mainMenuButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        mainMenuButtonUp = new TextureRegion(mainMenuButtonTexture, 0, 0, 512, 128);
        mainMenuButtonDown = new TextureRegion(mainMenuButtonTexture, 0, 129, 512, 128);

        optionsButtonTexture = new Texture(Gdx.files.internal("textures/options_button.png"));
        optionsButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        optionsButtonUp = new TextureRegion(optionsButtonTexture, 0, 0, 512, 128);
        optionsButtonDown = new TextureRegion(optionsButtonTexture, 0, 129, 512, 128);

        playSPButtonTexture = new Texture(Gdx.files.internal("textures/play_sp_button.png"));
        playSPButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playSPButtonUp = new TextureRegion(playSPButtonTexture, 0, 0, 512, 128);
        playSPButtonDown = new TextureRegion(playSPButtonTexture, 0, 129, 512, 128);

        play2PButtonTexture = new Texture(Gdx.files.internal("textures/play_2p_button.png"));
        play2PButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        play2PButtonUp = new TextureRegion(play2PButtonTexture, 0, 0, 512, 128);
        play2PButtonDown = new TextureRegion(play2PButtonTexture, 0, 129, 512, 128);

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
        playSPButtonTexture.dispose();
        play2PButtonTexture.dispose();
        mainMenuButtonTexture.dispose();
        exitButtonTexture.dispose();
        exitCrossButtonTexture.dispose();
        highscoreButtonTexture.dispose();
        backArrowButtonTexture.dispose();
        optionsButtonTexture.dispose();
    }

    public static int getHighScore() {
        return prefs.getInteger("high_score");
    }
    public static void setHighScore(int val) {
        prefs.putInteger("high_score", val);
        prefs.flush();
    }

}
