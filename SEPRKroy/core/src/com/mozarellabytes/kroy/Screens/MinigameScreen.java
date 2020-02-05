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
import com.mozarellabytes.kroy.minigame.Alien;
import com.mozarellabytes.kroy.minigame.Droplet;
import com.mozarellabytes.kroy.minigame.FireTruck;
import com.sun.corba.se.impl.orbutil.closure.Constant;

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

    /**
     * Constructor to instantiate all the assets and entities.
     */
    public MinigameScreen(Kroy game) {
        this.game = game;

        fireTruck = new FireTruck(Constants.GAME_WIDTH/2 - 64/2, 64);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        batch = new SpriteBatch();

//        fireTruck = new Rectangle();
//        fireTruck.x = Constants.GAME_WIDTH - 64/2;
//        fireTruck.y = 20;
//        fireTruck.width = 64;
//        fireTruck.height = 64;

        aliens = new Array<Alien>();
        spawnAlien();

        droplets = new Array<Droplet>();
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

        batch.draw(bgImage, 0 ,0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        batch.draw(fireTruck.getTexture(), fireTruck.getX(), fireTruck.getY());
        for (Alien alien: aliens) {
            batch.draw(alien.getTexture(), alien.getX(), alien.getY());
        }

        for (Droplet droplet: droplets) {
            batch.draw(droplet.getTexture(), droplet.getX(), droplet.getY());
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
            fireTruck.moveLeft(delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            fireTruck.moveRight(delta);
        }
//
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            // Shoot water droplet upwards
            if (TimeUtils.nanoTime() - lastDropTime > 50000000 || lastDropTime == 0) {
                shootDroplet();
            }
        }
//
//      Stop fire trucks moving out of bounds
        if (fireTruck.getX() < 0) {
            fireTruck.setPos(0, fireTruck.getY());
        }

        if (fireTruck.getX() > Constants.GAME_WIDTH  - 64) {
            fireTruck.setPos(Constants.GAME_WIDTH - 64, fireTruck.getY());
        }

        if (TimeUtils.nanoTime() - lastAlienSpawn > 1000000000) {
            spawnAlien();
        }

        // Alien movement and logic
        for (Iterator<Alien> iter = aliens.iterator(); iter.hasNext();) {
            Alien alien = iter.next();
            alien.moveDown(delta);

            if (alien.getY() + 64 < 0) {
                iter.remove();
            }

            // If a droplet hits an alien, remove both of them and increase the current game score.
            for (int i = 0; i < droplets.size; i++) {
                if (alien.getRect().overlaps(droplets.get(i).getRect())) {
                    iter.remove(); // Remove alien
                    droplets.removeIndex(i); // Remove droplet
                }
            }
        }

        // Droplet movement and logic
        for (Iterator<Droplet> iter = droplets.iterator(); iter.hasNext();) {
            Droplet droplet = iter.next();
            droplet.moveUp(delta);

            if (droplet.getY() > Constants.GAME_HEIGHT) {
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
        int x = MathUtils.random(0, Constants.GAME_WIDTH - 64);
        int y = Constants.GAME_HEIGHT;
        Alien alien = new Alien(x, y);
        aliens.add(alien);
        lastAlienSpawn = TimeUtils.nanoTime();
    }

    private void shootDroplet() {
        Droplet droplet = new Droplet(
                fireTruck.getX(),
                64
        );
        droplets.add(droplet);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
    }

}
