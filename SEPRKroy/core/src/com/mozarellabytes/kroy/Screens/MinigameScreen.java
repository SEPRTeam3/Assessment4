package com.mozarellabytes.kroy.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mozarellabytes.kroy.Kroy;
import com.mozarellabytes.kroy.Utilities.Constants;

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

    /** Images for minigame firetruck */
    private Texture truckImage;
    private Texture truckLeft;
    private Texture truckRight;

    /** Image for minigame alien */
    private Texture alienImage;

    /** Water sound when attacking the aliens */
    private Sound waterSound;

    /** Camera to set the projection for the screen */
    private OrthographicCamera camera;

    /** SpriteBatch for rending all sprites in */
    private SpriteBatch batch;

    /** Rectangle for controlling the firetruck */
    private Rectangle fireTruck;

    /** Array to keep track of all aliens */
    private Array<Rectangle> aliens;

    /** Keeping track of the time since the last alien was spawned */
    private long lastAlienSpawn;

    /** Image for water droplet */
    private Texture dropletImage;

    /** Array to keep track of all water droplets */
    private Array<Rectangle> droplets;

    /** Keeping track of the last time a water droplet was shot */
    private long lastDropTime = 0;

    /**
     * Constructor to instantiate all the assets and entities.
     */
    public MinigameScreen(Kroy game) {
        this.game = game;

        truckLeft = new Texture(Gdx.files.internal("sprites/firetruck/left.png"));
        truckRight = new Texture(Gdx.files.internal("sprites/firetruck/right.png"));
        truckImage = truckLeft;

        alienImage = new Texture(Gdx.files.internal("sprites/alien/alien.png"));

        dropletImage = new Texture(Gdx.files.internal("sprites/droplet/droplet180.png"));

        waterSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/truck_attack.wav"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        batch = new SpriteBatch();

        fireTruck = new Rectangle();
        fireTruck.x = Constants.GAME_WIDTH - 64/2;
        fireTruck.y = 20;
        fireTruck.width = 64;
        fireTruck.height = 64;

        aliens = new Array<Rectangle>();
        spawnAlien();

        droplets = new Array<Rectangle>();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(truckImage, fireTruck.x, fireTruck.y);
        for (Rectangle alien: aliens) {
            batch.draw(alienImage, alien.x, alien.y);
        }

        for (Rectangle droplet: droplets) {
            batch.draw(dropletImage, droplet.x, droplet.y);
        }
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
            fireTruck.x -= 500 * delta;
            truckImage = truckLeft;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            fireTruck.x += 500 * delta;
            truckImage = truckRight;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            // Shoot water droplet upwards
            if (TimeUtils.nanoTime() - lastDropTime > 50000000 || lastDropTime == 0) {
                shootDroplet();
            }
        }

//      Stop fire trucks moving out of bounds
        if (fireTruck.x < 0) {
            fireTruck.x = 0;
        }

        if (fireTruck.x > Constants.GAME_WIDTH  - 64) {
            fireTruck.x = Constants.GAME_WIDTH - 64;
        }

        if (TimeUtils.nanoTime() - lastAlienSpawn > 1000000000) {
            spawnAlien();
        }

        // Alien movement and logic
        for (Iterator<Rectangle> iter = aliens.iterator(); iter.hasNext();) {
            Rectangle alien = iter.next();
            alien.y -= 150 * delta;

            if (alien.y + 64 < 0) {
                iter.remove();
            }

//            if (alien.overlaps(fireTruck)) {
//                iter.remove();
//            }

            for (Rectangle droplet: droplets) {
                if (alien.overlaps(droplet)) {
                    iter.remove();
                }
            }
        }

        // Droplet movement and logic
        for (Iterator<Rectangle> iter = droplets.iterator(); iter.hasNext();) {
            Rectangle droplet = iter.next();
            droplet.y += 300 * delta;

            if (droplet.y > Constants.GAME_HEIGHT) {
                iter.remove();
            }
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
     * Method for spawning new aliens.
     * */
    private void spawnAlien() {
        Rectangle alien = new Rectangle();
        alien.x = MathUtils.random(0, Constants.GAME_WIDTH - 64);
        alien.y = Constants.GAME_HEIGHT;
        alien.width = 64;
        alien.height = 64;
        aliens.add(alien);
        lastAlienSpawn = TimeUtils.nanoTime();
    }

    private void shootDroplet() {
        Rectangle droplet = new Rectangle();
        droplet.x = fireTruck.x;
        droplet.y = fireTruck.y + 64;
        droplet.width = 32;
        droplet.height = 32;
        droplets.add(droplet);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
    }


}
