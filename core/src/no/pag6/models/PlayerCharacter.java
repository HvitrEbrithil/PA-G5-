package no.pag6.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import no.pag6.helpers.AssetLoader;

public class PlayerCharacter {
    private Texture characterTexture;
    private Animation characterAnimation;

    public PlayerCharacter(int colorCode) {
        switch (colorCode) {
            // Blue
            case(1):
                characterTexture = AssetLoader.characterBlueTexture;
                characterAnimation = AssetLoader.characterBlueAnimation;

                break;

            // Green
            case(2):
                characterTexture = AssetLoader.characterGreenTexture;
                characterAnimation = AssetLoader.characterGreenAnimation;

                break;

            // Orange
            case(3):
                characterTexture = AssetLoader.characterOrangeTexture;
                characterAnimation = AssetLoader.characterOrangeAnimation;

                break;

            // Pink
            case(4):
                characterTexture = AssetLoader.characterPinkTexture;
                characterAnimation = AssetLoader.characterPinkAnimation;

                break;

            // Purple
            case(5):
                characterTexture = AssetLoader.characterPurpleTexture;
                characterAnimation = AssetLoader.characterPurpleAnimation;

                break;

            // Red
            case(6):
                characterTexture = AssetLoader.characterRedTexture;
                characterAnimation = AssetLoader.characterRedAnimation;

                break;

            // Silver
            case(7):
                characterTexture = AssetLoader.characterSilverTexture;
                characterAnimation = AssetLoader.characterSilverAnimation;

                break;

            // Yellow
            case(8):
                characterTexture = AssetLoader.characterYellowTexture;
                characterAnimation = AssetLoader.characterYellowAnimation;

                break;

            default:
                characterTexture = AssetLoader.characterBlueTexture;

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
