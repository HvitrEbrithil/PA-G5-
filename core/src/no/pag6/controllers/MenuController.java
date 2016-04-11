package no.pag6.controllers;

public class MenuController {

    public enum MenuState {
        MAIN_MENU, OPTIONS_MENU, HIGHSCORE_MENU
    }

    private MenuState currentMenuState;

    private float runTime = 0;

    public MenuController() {
        currentMenuState = MenuState.MAIN_MENU;
    }

    public void update(float delta) {
        runTime += delta;

        switch (currentMenuState) {
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
        currentMenuState = MenuState.MAIN_MENU;
    }

    public void goToOptionsMenu() {
        currentMenuState = MenuState.OPTIONS_MENU;
    }

    public void goToHighscoreMenu() {
        currentMenuState = MenuState.HIGHSCORE_MENU;
    }

    public MenuState getCurrentMenuState() {
        return currentMenuState;
    }
    public void setCurrentMenuState(MenuState newState) {
        currentMenuState = newState;
    }

    private void updateMainMenu(float delta) {
    }

    private void updateOptionsMenu(float delta) {
    }

    private void updateHighscoreMenu(float delta) {
    }
}
