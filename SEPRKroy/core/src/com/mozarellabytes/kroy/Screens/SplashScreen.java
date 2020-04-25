package com.mozarellabytes.kroy.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.mozarellabytes.kroy.Kroy;
import com.badlogic.gdx.graphics.GL20;

/** This is the first screen shown when the user starts the game.
 * It shows the group's logo.
 */

public class SplashScreen implements Screen {


    private final Kroy game;

    /** The first image displayed as the splash screen */
    private final Texture backGroundLogo1;
    /** The second image displayed as the splash screen */
    private final Texture backgroundLogo2;


    /** The time that the splash screen has been displayed to the screen */
    private long startTime;

    private float spriteBatchTransparency;

    /** Constructor for the splash screen
     *
     * @param game LibGDX game
     */
    public SplashScreen(Kroy game) {
        this.game = game;
        backGroundLogo1 = new Texture(Gdx.files.internal("images/SEPRet Studios Logo. Splashpng.png"));
        backgroundLogo2 = new Texture(Gdx.files.internal("images/backgroundLogo.jpg"), true);
        backgroundLogo2.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        spriteBatchTransparency = 1;
    }

    /** Logs the time that the screen was first rendered */
    @Override
    public void show() {
        startTime = TimeUtils.millis();
    }

    /** Renders the splash screen image and changes the screen to the
     * menu screen after 3 millisecond
     * */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.setColor(1,1,1,1);
        game.batch.draw(backgroundLogo2, 0, 0, Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
        game.batch.setColor(1,1,1,spriteBatchTransparency);
        game.batch.draw(backGroundLogo1,0,0,Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
        game.batch.end();

        if(TimeUtils.timeSinceMillis(startTime) > 1000) {
            spriteBatchTransparency -= Gdx.graphics.getDeltaTime() * 1f;
        }
        // #Assessment3
        if(TimeUtils.timeSinceMillis(startTime) > 3000){
            game.setScreen(new ControlsScreen(game, new MenuScreen(game), "menu"));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /** Called when this screen should release all resources. */
    @Override
    public void dispose() {
        backgroundLogo2.dispose();
    }
}
