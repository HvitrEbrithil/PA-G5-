package no.pag6.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Preferences prefs;

    // Textures and TextureRegions
    public static Texture logoTexture, playSPButtonTexture, play2PButtonTexture, highscoreButtonTexture, optionsButtonTexture,
            quitButtonTexture, pauseButtonTexture, backButtonTexture, resumeButtonTexture, mainMenuButtonTexture;
    public static TextureRegion logo, playSPButtonUp, playSPButtonDown, play2PButtonUp, play2PButtonDown,
            highscoreButtonUp, highscoreButtonDown, optionsButtonUp, optionsButtonDown, quitButtonUp, quitButtonDown,
            pauseButtonUp, pauseButtonDown, backButtonUp, backButtonDown, resumeButtonUp, resumeButtonDown, mainMenuButtonUp, mainMenuButtonDown;

    // Animations
//    public static Animation ;

    // Sounds
//    public static Sound ;

    // Fonts
//    public static BitmapFont ;
    public static BitmapFont font;

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

        playSPButtonTexture = new Texture(Gdx.files.internal("textures/play_sp_button.png"));
        playSPButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playSPButtonUp = new TextureRegion(playSPButtonTexture, 0, 0, 512, 128);
        playSPButtonDown = new TextureRegion(playSPButtonTexture, 0, 129, 512, 128);

        play2PButtonTexture = new Texture(Gdx.files.internal("textures/play_2p_button.png"));
        play2PButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        play2PButtonUp = new TextureRegion(play2PButtonTexture, 0, 0, 512, 128);
        play2PButtonDown = new TextureRegion(play2PButtonTexture, 0, 129, 512, 128);

        highscoreButtonTexture = new Texture(Gdx.files.internal("textures/highscore_button.png"));
        highscoreButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        highscoreButtonUp = new TextureRegion(highscoreButtonTexture, 0, 0, 512, 128);
        highscoreButtonDown = new TextureRegion(highscoreButtonTexture, 0, 129, 512, 128);

        optionsButtonTexture = new Texture(Gdx.files.internal("textures/options_button.png"));
        optionsButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        optionsButtonUp = new TextureRegion(optionsButtonTexture, 0, 0, 512, 128);
        optionsButtonDown = new TextureRegion(optionsButtonTexture, 0, 129, 512, 128);

        quitButtonTexture = new Texture(Gdx.files.internal("textures/quit_button.png"));
        quitButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        quitButtonUp = new TextureRegion(quitButtonTexture, 0, 0, 512, 128);
        quitButtonDown = new TextureRegion(quitButtonTexture, 0, 129, 512, 128);

        pauseButtonTexture = new Texture(Gdx.files.internal("textures/pause_button.png"));
        pauseButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pauseButtonUp = new TextureRegion(pauseButtonTexture, 0, 0, 256, 256);
        pauseButtonDown = new TextureRegion(pauseButtonTexture, 0, 257, 256, 256);

        backButtonTexture = new Texture(Gdx.files.internal("textures/back_button.png"));
        backButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backButtonUp = new TextureRegion(backButtonTexture, 0, 0, 512, 128);
        backButtonDown = new TextureRegion(backButtonTexture, 0, 129, 512, 128);

        resumeButtonTexture = new Texture(Gdx.files.internal("textures/resume_button.png"));
        resumeButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        resumeButtonUp = new TextureRegion(resumeButtonTexture, 0, 0, 512, 128);
        resumeButtonDown = new TextureRegion(resumeButtonTexture, 0, 129, 512, 128);

        mainMenuButtonTexture = new Texture(Gdx.files.internal("textures/main_menu_button.png"));
        mainMenuButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        mainMenuButtonUp = new TextureRegion(mainMenuButtonTexture, 0, 0, 512, 128);
        mainMenuButtonDown = new TextureRegion(mainMenuButtonTexture, 0, 129, 512, 128);

        // Animations
//        TextureRegion[]  = {};
//         = new Animation(0.06f, );
//        .setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        // Sounds
//         = Gdx.audio.newSound(Gdx.files.internal("sounds/.wav"));

        // Fonts
        font = new BitmapFont();
//        font.getData().setScale(.5f, .5f);
    }

    public static void dispose() {
        logoTexture.dispose();
        playSPButtonTexture.dispose();
        play2PButtonTexture.dispose();
        highscoreButtonTexture.dispose();
        optionsButtonTexture.dispose();
        quitButtonTexture.dispose();
        pauseButtonTexture.dispose();
        backButtonTexture.dispose();
        resumeButtonTexture.dispose();
        mainMenuButtonTexture.dispose();
    }

    public static int getHighScore() {
        return prefs.getInteger("high_score");
    }
    public static void setHighScore(int val) {
        prefs.putInteger("high_score", val);
        prefs.flush();
    }

}
