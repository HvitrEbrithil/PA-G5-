package no.pag6.helpers;

import com.badlogic.gdx.InputProcessor;
import no.pag6.controllers.MenuController;
import no.pag6.game.PAG6Game;
import no.pag6.models.states.PlayState;
import no.pag6.models.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class MenuInputHandler implements InputProcessor {

    private PAG6Game game;
    private MenuController menuController;

    private float scaleFactorX;
    private float scaleFactorY;

    private List<SimpleButton> menuButtons;
    private SimpleButton playButton;

    public MenuInputHandler(PAG6Game game, MenuController menuController, float scaleFactorX, float scaleFactorY) {
        this.game = game;
        this.menuController = menuController;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        menuButtons = new ArrayList<SimpleButton>();
        playButton = new SimpleButton(2560/2 - AssetLoader.playButtonUp.getRegionWidth()/2,
                1440/2 - AssetLoader.playButtonUp.getRegionHeight(),
                AssetLoader.playButtonUp.getRegionWidth(), AssetLoader.playButtonUp.getRegionHeight(),
                AssetLoader.playButtonUp, AssetLoader.playButtonDown);
        menuButtons.add(playButton);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        // Handle input
        switch (menuController.getCurrentMenuState()) {
            case MAIN_MENU:
                playButton.isTouchDown(screenX, screenY);
                break;
            case OPTIONS_MENU:
                break;
            case HIGHSCORE_MENU:
                break;
            default:
                return false;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        // Handle input
        switch (menuController.getCurrentMenuState()) {
            case MAIN_MENU:
                if (playButton.isTouchUp(screenX, screenY)) {
                    game.setScreen(new PlayState(game));
                }
                break;
            case OPTIONS_MENU:
                break;
            case HIGHSCORE_MENU:
                break;
            default:
                return false;
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public List<SimpleButton> getMenuButtons() {
        return menuButtons;
    }

    private int scaleX(int screen_x) {
        return (int) (screen_x/scaleFactorX);
    }

    private int scaleY(int screen_y) {
        return (int) (screen_y/scaleFactorY);
    }

}
