package no.pag6.states;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.tweenaccessors.SpriteAccessor;

public class SplashScreen extends State {

    private TweenManager tweener;

    private Sprite logo;

    public SplashScreen(PAG6Game game) {
        super(game);
    }

    @Override
    public void show() {
        logo = new Sprite(AssetLoader.logo);
        logo.setColor(1, 1, 1, 0);

        float desiredWidth = V_WIDTH*0.3f;
        float desiredHeight = logo.getHeight()*(desiredWidth/logo.getWidth());

        logo.setSize(desiredWidth, desiredHeight);
        logo.setPosition(V_WIDTH/2 - logo.getWidth()/2, V_HEIGHT/2 - logo.getHeight()/2);

        setupTween();
    }

    @Override
    public void render(float delta) {
        tweener.update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.spriteBatch.setProjectionMatrix(cam.combined);
        game.spriteBatch.begin();
        logo.draw(game.spriteBatch);
        game.spriteBatch.end();
    }

    private void setupTween() {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        tweener = new TweenManager();

        TweenCallback cb = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.getGameStateManager().setScreen(new MainMenu(game));
            }
        };

        Tween.to(logo, SpriteAccessor.ALPHA, .8f).target(1)
                .ease(TweenEquations.easeInOutQuad).repeatYoyo(1, .4f)
                .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(tweener);
    }
}
