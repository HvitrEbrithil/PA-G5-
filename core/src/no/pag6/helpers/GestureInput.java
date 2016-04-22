package no.pag6.helpers;

public class GestureInput {

    private enum Direction {
        NONE, UP, RIGHT, DOWN, LEFT
    }

    private Direction swipeDirection = Direction.NONE;

    public void setSwipeDirection(float velocityX, float velocityY) {
        // TODO: Set correct direction based on velocities.
    }

}
