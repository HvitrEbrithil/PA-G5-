package no.pag6.controllers;

public class MenuController {

    public enum MenuState {
        MAIN_MENU, OPTIONS_MENU, HIGHSCORE_MENU
    }

    private MenuState currentState;

    private float runTime = 0;

    public MenuController() {
        currentState = MenuState.MAIN_MENU;
    }

    public void update(float delta) {
        runTime += delta;

        switch (currentState) {
            case MAIN_MENU:
                updateMainMenu(delta);
                break;
            case OPTIONS_MENU:
                updateOptionsMenu(delta);
                break;
            case HIGHSCORE_MENU:
                updateHighscoreMenu(delta);
                break;
            default:
                break;
        }
    }

    public void goToMainMenu() {
        currentState = MenuState.MAIN_MENU;
    }

    public void goToOptionsMenu() {
        currentState = MenuState.OPTIONS_MENU;
    }

    public void goToHighscoreMenu() {
        currentState = MenuState.HIGHSCORE_MENU;
    }

    public boolean isMainMenu() {
        return currentState == MenuState.MAIN_MENU;
    }

    public boolean isOptionsMenu() {
        return currentState == MenuState.OPTIONS_MENU;
    }

    public boolean isHighscoreMenu() {
        return currentState == MenuState.HIGHSCORE_MENU;
    }

    private void updateMainMenu(float delta) {
    }

    private void updateOptionsMenu(float delta) {
    }

    private void updateHighscoreMenu(float delta) {
    }
}
