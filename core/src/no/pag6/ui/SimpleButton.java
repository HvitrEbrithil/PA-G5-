package no.pag6.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class SimpleButton {

    private float x, y, width, height;

    private TextureRegion buttonUp;
    private TextureRegion buttonDown;

    private Rectangle bounds;

    private boolean isPressed = false;

    public SimpleButton(float x, float y, float width, float height, TextureRegion buttonUp, TextureRegion buttonDown) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;

        bounds = new Rectangle(x, y, width, height);
    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }

    public void draw(SpriteBatch batcher) {
        if (isPressed) {
            batcher.draw(buttonDown, x, y, width, height);
        } else {
            batcher.draw(buttonUp, x, y, width, height);
        }
    }

    public boolean isTouchDown(float screenX, float screenY) {
        if (bounds.contains(screenX, screenY)) {
            isPressed = true;

            return true;
        }

        return false;
    }

    public boolean isTouchUp(float screenX, float screenY) {
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;

            return true;
        }

        isPressed = false;

        return false;
    }

    public void setX(float x) {
        this.x = x;
        bounds.x = x;
    }

    public void setY(float y) {
        this.y = y;
        bounds.y = y;
    }

}
