package no.pag6.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Preferences prefs;

    // Textures and TextureRegions
    public static Texture splashTexture1, splashTexture6, splashTexture11, splashTexture27, splashTexture33, splashTexture38, countTexture, backgroundTexture,
            logoTexture, playerTitleTexture, playersTitleTexture, pauseTitleTexture, optionsTitleTexture, highscoresTitleTexture, gameOverTitleTexture,
            playButtonTexture, highscoresButtonTexture, optionsButtonTexture, quitButtonTexture, pauseButtonTexture, backButtonTexture, resumeButtonTexture,
            mainMenuButtonTexture, characterBlueTexture, characterGreenTexture, characterOrangeTexture, characterPinkTexture, characterPurpleTexture,
            characterRedTexture, characterSilverTexture, characterYellowTexture, onButtonTexture, offButtonTexture;
    public static TextureRegion background, logo, playerTitle, playersTitle, pauseTitle, optionsTitle, highscoresTitle, gameOverTitle, playButtonUp,
            playButtonDown, highscoresButtonUp, highscoresButtonDown, optionsButtonUp, optionsButtonDown, quitButtonUp, quitButtonDown, pauseButtonUp,
            pauseButtonDown, backButtonUp, backButtonDown, resumeButtonUp, resumeButtonDown, mainMenuButtonUp, mainMenuButtonDown, onButtonUp,
            onButtonDown, offButtonUp, offButtonDown;

    // Animations
    public static Animation splashAnimation, countAnimation, characterBlueAnimation, characterGreenAnimation, characterOrangeAnimation, characterPinkAnimation,
            characterPurpleAnimation, characterRedAnimation, characterSilverAnimation, characterYellowAnimation;

    // Sounds
    public static Sound splashSound, countdownSound, swooshSound;
    // Music
    public static Music backgroundMusic, inGameMusic;

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

        playerTitleTexture = new Texture(Gdx.files.internal("textures/player_title.png"));
        playerTitleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playerTitle = new TextureRegion(playerTitleTexture, 0, 0, 768, 128);

        playersTitleTexture = new Texture(Gdx.files.internal("textures/players_title.png"));
        playersTitleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playersTitle = new TextureRegion(playersTitleTexture, 0, 0, 768, 128);

        pauseTitleTexture = new Texture(Gdx.files.internal("textures/pause_title.png"));
        pauseTitleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pauseTitle = new TextureRegion(pauseTitleTexture, 0, 0, 768, 128);

        optionsTitleTexture = new Texture(Gdx.files.internal("textures/options_title.png"));
        optionsTitleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        optionsTitle = new TextureRegion(optionsTitleTexture, 0, 0, 768, 128);

        highscoresTitleTexture = new Texture(Gdx.files.internal("textures/highscores_title.png"));
        highscoresTitleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        highscoresTitle = new TextureRegion(highscoresTitleTexture, 0, 0, 768, 128);

        gameOverTitleTexture = new Texture(Gdx.files.internal("textures/gameover_title.png"));
        gameOverTitleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        gameOverTitle = new TextureRegion(gameOverTitleTexture, 0, 0, 768, 128);

        playButtonTexture = new Texture(Gdx.files.internal("textures/play_button.png"));
        playButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playButtonUp = new TextureRegion(playButtonTexture, 0, 0, 512, 128);
        playButtonDown = new TextureRegion(playButtonTexture, 0, 129, 512, 128);

        highscoresButtonTexture = new Texture(Gdx.files.internal("textures/highscores_button.png"));
        highscoresButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        highscoresButtonUp = new TextureRegion(highscoresButtonTexture, 0, 0, 512, 128);
        highscoresButtonDown = new TextureRegion(highscoresButtonTexture, 0, 129, 512, 128);

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

        onButtonTexture = new Texture(Gdx.files.internal("textures/on_button.png"));
        onButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        onButtonUp = new TextureRegion(onButtonTexture, 0, 0, 256, 256);
        onButtonDown = new TextureRegion(onButtonTexture, 0, 257, 256, 256);

        offButtonTexture = new Texture(Gdx.files.internal("textures/off_button.png"));
        offButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        offButtonUp = new TextureRegion(offButtonTexture, 0, 0, 256, 256);
        offButtonDown = new TextureRegion(offButtonTexture, 0, 257, 256, 256);

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
        countdownSound = Gdx.audio.newSound(Gdx.files.internal("sounds/countdown.mp3"));
        swooshSound = Gdx.audio.newSound(Gdx.files.internal("sounds/swoosh.mp3"));

        // Music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/background.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(.5f);

        inGameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/in_game.mp3"));
        inGameMusic.setLooping(true);
        inGameMusic.setVolume(.5f);
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
        playersTitleTexture.dispose();
        playersTitleTexture.dispose();
        pauseButtonTexture.dispose();
        optionsTitleTexture.dispose();
        highscoresTitleTexture.dispose();
        gameOverTitleTexture.dispose();
        playButtonTexture.dispose();
        highscoresButtonTexture.dispose();
        optionsButtonTexture.dispose();
        quitButtonTexture.dispose();
        pauseButtonTexture.dispose();
        backButtonTexture.dispose();
        resumeButtonTexture.dispose();
        mainMenuButtonTexture.dispose();
        characterBlueTexture.dispose();
        characterGreenTexture.dispose();
        characterOrangeTexture.dispose();
        characterPinkTexture.dispose();
        characterPurpleTexture.dispose();
        characterRedTexture.dispose();
        characterSilverTexture.dispose();
        characterYellowTexture.dispose();
        onButtonTexture.dispose();
        offButtonTexture.dispose();

        splashSound.dispose();
        countdownSound.dispose();
        swooshSound.dispose();
        backgroundMusic.dispose();
    }

    public static boolean getMusicOn() {
        boolean musicOn = prefs.getBoolean("music_on", true);
        prefs.flush();
        return musicOn;
    }
    public static void setMusicOn(boolean soundOn) {
        prefs.putBoolean("music_on", soundOn);
        prefs.flush();
    }

    public static boolean getSoundOn() {
        boolean soundOn = prefs.getBoolean("sound_on", true);
        prefs.flush();
        return soundOn;
    }
    public static void setSoundOn(boolean soundOn) {
        prefs.putBoolean("sound_on", soundOn);
        prefs.flush();
    }

    public static String getHighscorePlayers() {
        String highscorePlayers = prefs.getString("highscore_players", "");
        prefs.flush();
        return highscorePlayers;
    }
    public static void setHighscorePlayers(String highscorePlayers) {
        if (highscorePlayers.startsWith(",")) {
            highscorePlayers = highscorePlayers.substring(1);
        }
        prefs.putString("highscore_players", highscorePlayers);
        prefs.flush();
    }

    public static String getHighscores() {
        String highscores = prefs.getString("highscores", "");
        prefs.flush();
        return highscores;
    }
    public static void setHighscores(String highscores) {
        if (highscores.startsWith(",")) {
            highscores = highscores.substring(1);
        }
        prefs.putString("highscores", highscores);
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
