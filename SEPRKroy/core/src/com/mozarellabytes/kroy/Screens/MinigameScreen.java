package com.mozarellabytes.kroy.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mozarellabytes.kroy.Kroy;
import com.mozarellabytes.kroy.Utilities.Constants;
import com.mozarellabytes.kroy.minigame.Alien;
import com.mozarellabytes.kroy.minigame.Droplet;
import com.mozarellabytes.kroy.minigame.FireTruck;

import java.sql.Time;
import java.util.Iterator;


/*
*  #Assessent3
*
*  All code in this class is for Assessment 3.
* */
public class MinigameScreen implements Screen {

    /** Instance of the game */
    private final Kroy game;

    /** Camera to set the projection for the screen */
    private OrthographicCamera camera;

    /** SpriteBatch for rending all sprites in */
    private SpriteBatch batch;

    /** Rectangle for controlling the firetruck */
//    private Rectangle fireTruck;
    private FireTruck fireTruck;

    /** Array to keep track of all aliens */
    private Array<Alien> aliens;

    /** Keeping track of the time since the last alien was spawned */
    private long lastAlienSpawn;

    /** Array to keep track of all water droplets */
    private Array<Droplet> droplets;

    /** Keeping track of the last time a water droplet was shot */
    private long lastDropTime = 0;

    /** Background image for the minigame */
    private Texture bgImage = new Texture(Gdx.files.internal("images/minigame-bg.jpg"));
    private Texture roadImage = new Texture(Gdx.files.internal("images/minigame-road.png"));

    private int score = 0;

    private BitmapFont font;
    private String scoreText;
    private GlyphLayout scoreLayout;

    /**
     * Constructor to instantiate all the assets and entities.
     */
    public MinigameScreen(Kroy game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        batch = new SpriteBatch();

        fireTruck = new FireTruck(Constants.GAME_WIDTH/2 - 64/2, 96);

        aliens = new Array<Alien>();
        spawnAlien();

        droplets = new Array<Droplet>();
    }

    @Override
    public void show() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Magero.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 60;
        font = generator.generateFont(parameter);
        scoreLayout = new GlyphLayout();

        updateScoreText();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(bgImage, 0 ,0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        batch.draw(roadImage, 0 ,0, Constants.GAME_WIDTH, 384);

        batch.draw(fireTruck.getTexture(), fireTruck.getX(), fireTruck.getY());
        for (Alien alien: aliens) {
            batch.draw(alien.getTexture(), alien.getX(), alien.getY());
        }

        for (Droplet droplet: droplets) {
            batch.draw(droplet.getTexture(), droplet.getX(), droplet.getY());
        }

        font.draw(
                batch,
                scoreLayout,
                Constants.GAME_WIDTH/2 - scoreLayout.width/2,
                Constants.GAME_HEIGHT - scoreLayout.height
        );

        batch.end();

        this.update(delta);
    }

    /**
     * Handle controls and minigame logic.
     *
     * @param delta The time in milliseconds since the last render
     * */
    public void update(float delta) {

//      Fire truck controls
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            fireTruck.moveLeft(delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            fireTruck.moveRight(delta);
        }
//
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            // Shoot water droplet upwards
            if (TimeUtils.nanoTime() - lastDropTime > 300000000 || lastDropTime == 0) {
                shootDroplet();
            }
        }

//      Stop fire trucks moving out of bounds
        if (fireTruck.getX() < 0) {
            fireTruck.setPos(0, fireTruck.getY());
        }

        if (fireTruck.getX() > Constants.GAME_WIDTH  - 64) {
            fireTruck.setPos(Constants.GAME_WIDTH - 64, fireTruck.getY());
        }

//      Timer for spawning aliens every second
        if (TimeUtils.nanoTime() - lastAlienSpawn > 1000000000) {
            spawnAlien();
        }

//      Alien movement and logic
        for (Iterator<Alien> iter = aliens.iterator(); iter.hasNext();) {
            Alien alien = iter.next();
            alien.moveDown(delta);

            // Checking if an alien has reached "road level", removing it if so.
            // Game ends at this point.
            if (alien.getY() + 64 < 144) {
                iter.remove();
                invokeGameOver(game);
            }

            // If a droplet hits an alien, remove both of them and increase the current game score.
            for (int i = 0; i < droplets.size; i++) {
                if (alien.getRect().overlaps(droplets.get(i).getRect())) {
                    iter.remove(); // Remove alien
                    droplets.removeIndex(i); // Remove droplet

                    this.score += 10;
                    updateScoreText();
                }
            }
        }

//      Droplet movement and logic
        for (Iterator<Droplet> iter = droplets.iterator(); iter.hasNext();) {
            Droplet droplet = iter.next();
            droplet.moveUp(delta);

            if (droplet.getY() > Constants.GAME_HEIGHT) {
                iter.remove();
            }
        }

        if (score == 100) {
            invokeGameOver(game);
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

    /**
     * Method for spawning new aliens at the top of the screen at a random x location.
     * */
    private void spawnAlien() {
        int x = MathUtils.random(0, Constants.GAME_WIDTH - 64);
        int y = Constants.GAME_HEIGHT;
        Alien alien = new Alien(x, y);
        aliens.add(alien);
        lastAlienSpawn = TimeUtils.nanoTime();
    }

    private void shootDroplet() {
        Droplet droplet = new Droplet(
                fireTruck.getX() + fireTruck.getWidth()/2,
                128
        );
        droplets.add(droplet);
        lastDropTime = TimeUtils.nanoTime();
    }

    /**
     * Updates the current score glyph layout.
     * Invoked whenever an alien is destroyed.
     */
    private void updateScoreText() {
        this.scoreText = "Score: " + this.score;
        this.scoreLayout.setText(font, this.scoreText);
    }

    private void invokeGameOver(Kroy game) {
        dispose();
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void dispose() {
    }

}
