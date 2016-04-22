package no.pag6.helpers;

public class MyGestureInput {

    /**
     * Can be extended with LEFT and RIGHT if necessary
     */
    protected enum Direction {
        NONE, UP, DOWN
    }

    private Direction swipeDirection;

    public MyGestureInput(Direction swipeDirection) {
        this.swipeDirection = swipeDirection;
    }

    public Direction getSwipeDirection() {
        return swipeDirection;
    }
    public void setSwipeDirection(float velocityX, float velocityY) {
        // TODO: Set correct direction based on velocities.
    }

}
