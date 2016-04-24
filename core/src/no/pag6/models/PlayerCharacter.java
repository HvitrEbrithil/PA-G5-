package no.pag6.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import no.pag6.helpers.Constants;

public class PlayerCharacter implements Constants {
    private Texture characterTexture;
    private Animation characterAnimation;

    public PlayerCharacter(int colorCode) {
        switch (colorCode) {
            // Blue
            case(1):
                characterTexture = al.characterBlueTexture;
                characterAnimation = al.characterBlueAnimation;

                break;

            // Green
            case(2):
                characterTexture = al.characterGreenTexture;
                characterAnimation = al.characterGreenAnimation;

                break;

            // Orange
            case(3):
                characterTexture = al.characterOrangeTexture;
                characterAnimation = al.characterOrangeAnimation;

                break;

            // Pink
            case(4):
                characterTexture = al.characterPinkTexture;
                characterAnimation = al.characterPinkAnimation;

                break;

            // Purple
            case(5):
                characterTexture = al.characterPurpleTexture;
                characterAnimation = al.characterPurpleAnimation;

                break;

            // Red
            case(6):
                characterTexture = al.characterRedTexture;
                characterAnimation = al.characterRedAnimation;

                break;

            // Silver
            case(7):
                characterTexture = al.characterSilverTexture;
                characterAnimation = al.characterSilverAnimation;

                break;

            // Yellow
            case(8):
                characterTexture = al.characterYellowTexture;
                characterAnimation = al.characterYellowAnimation;

                break;

            default:
                characterTexture = al.characterBlueTexture;

                break;
        }

    }

    public Texture getTexture() {
        return characterTexture;
    }

    public Animation getAnimation() {
        return characterAnimation;
    }
}
