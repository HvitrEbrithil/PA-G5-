package no.pag6.controllers;

public class GameController {

    public enum GameState {
        RUNNING, PAUSED, GAME_OVER
    }

    private GameState currentState;

    private float runTime = 0;

    private int nofPlayers;
    private int p1Score = 0;
    private int p2Score = 0;

    public GameController(int nofPlayers) {
        this.nofPlayers = nofPlayers;

        currentState = GameState.RUNNING;
    }

    public void update(float delta) {
        runTime += delta;

        switch (currentState) {
            case RUNNING:
                updateRunning(delta);
                break;
            case PAUSED:
                updatePaused(delta);
                break;
            case GAME_OVER:
                updateGameOver(delta);
                break;
            default:
                break;
        }
    }

    public void run() {
        currentState = GameState.RUNNING;
    }

    public void pause() {
        currentState = GameState.PAUSED;
    }

    public void end() {
        currentState = GameState.GAME_OVER;
    }

    public int getNofPlayers() {
        return nofPlayers;
    }

    public int getP1Score() {
        return p1Score;
    }
    public void addP1Score(int increment) {
        p1Score += increment;
    }

    public int getP2Score() {
        return p2Score;
    }
    public void addP2Score(int increment) {
        p2Score += increment;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public boolean isPaused() {
        return currentState == GameState.PAUSED;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAME_OVER;
    }

    private void updateRunning(float delta) {
    }

    private void updatePaused(float delta) {
    }

    private void updateGameOver(float delta) {
    }

}
