package no.pag6.models;

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
    public void setSwipeDirection(float velocityY) {
        if (velocityY < -1000) {
            swipeDirection = Direction.UP;
        } else if (velocityY > 1000) {
            swipeDirection = Direction.DOWN;
        } else {
            swipeDirection = Direction.NONE;
        }
    }

}
