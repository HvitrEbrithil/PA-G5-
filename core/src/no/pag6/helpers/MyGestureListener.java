package no.pag6.helpers;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import no.pag6.models.MyGestureInput;
import no.pag6.states.PlayState;

public class MyGestureListener implements GestureListener {

    private PlayState playState;

    private MyGestureInput myGestureInput;

    public MyGestureListener(PlayState playState) {
        this.playState = playState;

        myGestureInput = new MyGestureInput(MyGestureInput.Direction.NONE);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        // Update gesture
        myGestureInput.setSwipeDirection(velocityY);

        // Switch lanes
        if (myGestureInput.getSwipeDirection() == MyGestureInput.Direction.UP
                && playState.getActivePlayer().isOnFirstLane() && playState.isStarted()) {
            playState.getActivePlayer().switchLanes();
            playState.tweenLayers();
        } else if (myGestureInput.getSwipeDirection() == MyGestureInput.Direction.DOWN
                && !playState.getActivePlayer().isOnFirstLane() && playState.isStarted()) {
            playState.getActivePlayer().switchLanes();
            playState.tweenLayers();
        }

        return !(myGestureInput.getSwipeDirection() == MyGestureInput.Direction.NONE);

    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

}
