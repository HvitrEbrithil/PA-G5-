package no.pag6.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class AssetLoader {

    public static Preferences prefs;

    // Textures and TextureRegions
    public static Texture splashTexture1, splashTexture6, splashTexture11, splashTexture27, splashTexture33, splashTexture38, countTexture, backgroundTexture,
            logoTexture, playButtonTexture, highscoreButtonTexture, optionsButtonTexture, quitButtonTexture, pauseButtonTexture, backButtonTexture,
            resumeButtonTexture, mainMenuButtonTexture, characterBlueTexture, characterGreenTexture, characterOrangeTexture, characterPinkTexture,
            characterPurpleTexture, characterRedTexture, characterSilverTexture, characterYellowTexture;
    public static TextureRegion background, logo, playButtonUp, playButtonDown, highscoreButtonUp, highscoreButtonDown, optionsButtonUp, optionsButtonDown,
            quitButtonUp, quitButtonDown, pauseButtonUp, pauseButtonDown, backButtonUp, backButtonDown, resumeButtonUp, resumeButtonDown, mainMenuButtonUp,
            mainMenuButtonDown;

    // Animations
    public static Animation splashAnimation, countAnimation, characterBlueAnimation, characterGreenAnimation, characterOrangeAnimation, characterPinkAnimation,
            characterPurpleAnimation, characterRedAnimation, characterSilverAnimation, characterYellowAnimation;

    // Sounds
    public static Sound splashSound, backgroundMusic, countdownSound, swooshSound;

    public static void load() {
        // Preferences
        prefs = Gdx.app.getPreferences("PAG6Game");

        // Textures and TextureRegions
        backgroundTexture = new Texture(Gdx.files.internal("textures/menu_background.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        background = new TextureRegion(backgroundTexture, 0, 0, 2560, 1440);

        logoTexture = new Texture(Gdx.files.internal("textures/lanerunner_logo.png"));
        logoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        logo = new TextureRegion(logoTexture, 0, 0, 512, 512);

        playButtonTexture = new Texture(Gdx.files.internal("textures/play_button.png"));
        playButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playButtonUp = new TextureRegion(playButtonTexture, 0, 0, 512, 128);
        playButtonDown = new TextureRegion(playButtonTexture, 0, 129, 512, 128);

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
        TextureRegion[] splashFrames = initSplash();
        splashAnimation = new Animation(0.033f, splashFrames);
        splashAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        TextureRegion[] countFrames = initCount();
        countAnimation = new Animation(1.0f, countFrames);
        countAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        initCharacters();

        // Sounds
        splashSound = Gdx.audio.newSound(Gdx.files.internal("sounds/splash_screen_sound.mp3"));
        backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("sounds/background.mp3"));
        countdownSound = Gdx.audio.newSound(Gdx.files.internal("sounds/countdown.mp3"));
        swooshSound = Gdx.audio.newSound(Gdx.files.internal("sounds/swoosh.mp3"));

        // Fonts
//        font = new BitmapFont();
//        font = new BitmapFont(Gdx.files.internal("fonts/arial_72.fnt"), Gdx.files.internal("fonts/arial_72.png"), false);

    }

    public static void dispose() {
        splashTexture1.dispose();
        splashTexture6.dispose();
        splashTexture11.dispose();
        splashTexture27.dispose();
        splashTexture33.dispose();
        splashTexture38.dispose();
        countTexture.dispose();
        backgroundTexture.dispose();
        logoTexture.dispose();
        playButtonTexture.dispose();
        highscoreButtonTexture.dispose();
        optionsButtonTexture.dispose();
        quitButtonTexture.dispose();
        pauseButtonTexture.dispose();
        backButtonTexture.dispose();
        resumeButtonTexture.dispose();
        mainMenuButtonTexture.dispose();
    }

    public static int getHighScore(int playerNumber) {
        String key = "high_score_p" + playerNumber;
        return prefs.getInteger(key, 0);
    }
    public static void setHighScore(int playerNumber, int val) {
        String key = "high_score_p" + playerNumber;
        prefs.putInteger(key, val);
        prefs.flush();
    }

    private static TextureRegion[] initSplash() {
        TextureRegion[] splashFrames = new TextureRegion[60];

        // Frames 1 to 5
        splashTexture1 = new Texture(Gdx.files.internal("textures/splash_frames_1.png"));
        splashTexture1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        for (int i = 1; i <= 5; i++) {
            TextureRegion splashFrame = new TextureRegion(splashTexture1, 0, 0, 800, 450);
            splashFrames[i-1] = splashFrame;
        }

        // Frames 6 to 10
        splashTexture6 = new Texture(Gdx.files.internal("textures/splash_frames_6.png"));
        splashTexture6.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        for (int i = 6; i <= 10; i++) {
            TextureRegion splashFrame = new TextureRegion(splashTexture6, (i == 6 ? 0 : (800*(i-6) + 1)), 0, 800, 450);
            splashFrames[i-1] = splashFrame;
        }

        // Frames 11 to 26
        splashTexture11 = new Texture(Gdx.files.internal("textures/splash_frames_11.png"));
        splashTexture11.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        for (int i = 11; i <= 26; i++) {
            TextureRegion splashFrame = new TextureRegion(splashTexture11, 0, 0, 800, 450);
            splashFrames[i-1] = splashFrame;
        }

        // Frames 27 to 32
        splashTexture27 = new Texture(Gdx.files.internal("textures/splash_frames_27.png"));
        splashTexture27.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        for (int i = 27; i <= 32; i++) {
            TextureRegion splashFrame = new TextureRegion(splashTexture27, (i == 27? 0 : (800*(i-27) + 1)), 0, 800, 450);
            splashFrames[i-1] = splashFrame;
        }

        // Frames 33 to 37
        splashTexture33 = new Texture(Gdx.files.internal("textures/splash_frames_33.png"));
        splashTexture33.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        for (int i = 33; i <= 37; i++) {
            TextureRegion splashFrame = new TextureRegion(splashTexture33, (i == 33 ? 0 : (800*(i-33) + 1)), 0, 800, 450);
            splashFrames[i-1] = splashFrame;
        }

        // Frames 38 to 60
        splashTexture38 = new Texture(Gdx.files.internal("textures/splash_frames_38.png"));
        splashTexture38.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        for (int i = 38; i <= 60; i++) {
            TextureRegion splashFrame = new TextureRegion(splashTexture38, 0, 0, 800, 450);
            splashFrames[i-1] = splashFrame;
        }

        return splashFrames;
    }

    private static TextureRegion[] initCount() {
        TextureRegion[] countFrames = new TextureRegion[4];
        countTexture = new Texture(Gdx.files.internal("textures/count_frames.png"));
        countTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        for (int i = 1; i <= 4; i++) {
            TextureRegion countFrame = new TextureRegion(countTexture, (i == 1 ? 0 : (800*(i-1) + 1)), 0, 800, 450);
            countFrames[i-1] = countFrame;
        }

        return countFrames;

    }

    private static void initCharacters() {
        // Blue
        characterBlueTexture = new Texture(Gdx.files.internal("textures/character_blue.png"));
        characterBlueTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion[] characterBlueFrames = new TextureRegion[5];
        for (int i = 1; i <= 5; i++) {
            TextureRegion characterBlueFrame = new TextureRegion(characterBlueTexture, (i == 1 ? 0 : (150*(i-1) + 1)), 0, 150, 200);
            characterBlueFrames[i-1] = characterBlueFrame;
        }

        characterBlueAnimation = new Animation(0.1f, characterBlueFrames);
        characterBlueAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        // Green
        characterGreenTexture = new Texture(Gdx.files.internal("textures/character_green.png"));
        characterGreenTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion[] characterGreenFrames = new TextureRegion[5];
        for (int i = 1; i <= 5; i++) {
            TextureRegion characterGreenFrame = new TextureRegion(characterGreenTexture, (i == 1 ? 0 : (150*(i-1) + 1)), 0, 150, 200);
            characterGreenFrames[i-1] = characterGreenFrame;
        }

        characterGreenAnimation = new Animation(0.1f, characterGreenFrames);
        characterGreenAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        // Orange
        characterOrangeTexture = new Texture(Gdx.files.internal("textures/character_orange.png"));
        characterOrangeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion[] characterOrangeFrames = new TextureRegion[5];
        for (int i = 1; i <= 5; i++) {
            TextureRegion characterOrangeFrame = new TextureRegion(characterOrangeTexture, (i == 1 ? 0 : (150*(i-1) + 1)), 0, 150, 200);
            characterOrangeFrames[i-1] = characterOrangeFrame;
        }

        characterOrangeAnimation = new Animation(0.1f, characterOrangeFrames);
        characterOrangeAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        // Pink
        characterPinkTexture = new Texture(Gdx.files.internal("textures/character_pink.png"));
        characterPinkTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion[] characterPinkFrames = new TextureRegion[5];
        for (int i = 1; i <= 5; i++) {
            TextureRegion characterPinkFrame = new TextureRegion(characterPinkTexture, (i == 1 ? 0 : (150*(i-1) + 1)), 0, 150, 200);
            characterPinkFrames[i-1] = characterPinkFrame;
        }

        characterPinkAnimation = new Animation(0.1f, characterPinkFrames);
        characterPinkAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        // Purple
        characterPurpleTexture = new Texture(Gdx.files.internal("textures/character_purple.png"));
        characterPurpleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion[] characterPurpleFrames = new TextureRegion[5];
        for (int i = 1; i <= 5; i++) {
            TextureRegion characterPurpleFrame = new TextureRegion(characterPurpleTexture, (i == 1 ? 0 : (150*(i-1) + 1)), 0, 150, 200);
            characterPurpleFrames[i-1] = characterPurpleFrame;
        }

        characterPurpleAnimation = new Animation(0.1f, characterPurpleFrames);
        characterPurpleAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        // Red
        characterRedTexture = new Texture(Gdx.files.internal("textures/character_red.png"));
        characterRedTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion[] characterRedFrames = new TextureRegion[5];
        for (int i = 1; i <= 5; i++) {
            TextureRegion characterRedFrame = new TextureRegion(characterRedTexture, (i == 1 ? 0 : (150*(i-1) + 1)), 0, 150, 200);
            characterRedFrames[i-1] = characterRedFrame;
        }

        characterRedAnimation = new Animation(0.1f, characterRedFrames);
        characterRedAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        // Silver
        characterSilverTexture = new Texture(Gdx.files.internal("textures/character_silver.png"));
        characterSilverTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion[] characterSilverFrames = new TextureRegion[5];
        for (int i = 1; i <= 5; i++) {
            TextureRegion characterSilverFrame = new TextureRegion(characterSilverTexture, (i == 1 ? 0 : (150*(i-1) + 1)), 0, 150, 200);
            characterSilverFrames[i-1] = characterSilverFrame;
        }

        characterSilverAnimation = new Animation(0.1f, characterSilverFrames);
        characterSilverAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        // Yellow
        characterYellowTexture = new Texture(Gdx.files.internal("textures/character_yellow.png"));
        characterYellowTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion[] characterYellowFrames = new TextureRegion[5];
        for (int i = 1; i <= 5; i++) {
            TextureRegion characterYellowFrame = new TextureRegion(characterYellowTexture, (i == 1 ? 0 : (150*(i-1) + 1)), 0, 150, 200);
            characterYellowFrames[i-1] = characterYellowFrame;
        }

        characterYellowAnimation = new Animation(0.1f, characterYellowFrames);
        characterYellowAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

}
