package com.mozarellabytes.kroy.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mozarellabytes.kroy.Kroy;
import com.mozarellabytes.kroy.Utilities.Constants;
import com.mozarellabytes.kroy.Utilities.SoundFX;
import com.mozarellabytes.kroy.minigame.Alien;
import com.mozarellabytes.kroy.minigame.Droplet;
import com.mozarellabytes.kroy.minigame.FireTruck;

import java.util.Iterator;


/*
*  #Assessent3
*
*  All code in this class is for Assessment 3.
* */
public class MinigameScreen implements Screen {

    /** Instance of the game */
    private final Kroy game;

    /** Reference to the screen calling minigame.
     * This means we can easily return to the previous screen without
     * losing any state data.
     */
    private final Screen parent;

    /** Camera to set the projection for the screen */
    private OrthographicCamera camera;

    /** SpriteBatch for rending all sprites in */
    private SpriteBatch batch;

    /** Rectangle for controlling the firetruck */
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

    /** Keeping track of the current minigame score. Initilaised to 0 on screen show() */
    private int score;

    /** Stores the font/typeface used for the minigame's UI. */
    private BitmapFont font;

    /** Stores the current text to be rendered. Will be "Score: " + the current score. */
    private String scoreText;

    /** Represents the actual "object" that is rendered by the screen.
     * Has properties like dimensions and position to allow for easy positioning on the screen.
     */
    private GlyphLayout scoreLayout;

    /**
     * #Assessment3
     * Constructor to initialise the MinigameScreen with all necessary classes and attributes.
     * @param game A reference to the overarching Kroy game controller.
     * @param parent A reference to the screen that setScreen() to the minigame. Allows for returning back to previous screen without loss of state.
     */
    public MinigameScreen(Kroy game, Screen parent) {
        this.game = game;
        this.parent = parent;

        // Instantiate a camera with width and height of the game window to render and display the content.
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        // Sprites are rendered as a batch to improve performance.
        batch = new SpriteBatch();

        // Instantiate a fireTruck in the middle of the screen at the bottom.
        fireTruck = new FireTruck(Constants.GAME_WIDTH/2 - 64/2, 96);

        aliens = new Array<>();
        spawnAlien();

        droplets = new Array<>();
    }

    /**
     * #Assessment3
     * Called whenever MinigameScreen is shown (i.e. whenever setScreen(new MinigameScreen) is run.
     * Initilaises the starting score to 0 and creates the initial GlyphLayout on the screen.
     */
    @Override
    public void show() {
        SoundFX.sfx_soundtrack.setVolume(.5f);
        SoundFX.sfx_minigamebgm.play();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Magero.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        score = 0;

        parameter.size = 60;
        font = generator.generateFont(parameter);
        scoreLayout = new GlyphLayout();

        updateScoreText();
    }

    /**
     * #Assessment3
     * Called every `delta` time step to render the new positions/values of entities and objects.
     * @param delta the increment of time between each render call.
     */
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
     * #Assessment3
     * Handle controls and minigame logic.
     *
     * @param delta The time in milliseconds since the last render call.
     * */
    public void update(float delta) {

//      Fire truck controls
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            fireTruck.moveLeft(delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            fireTruck.moveRight(delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            fireTruck.stay();
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
                SoundFX.sfx_minigamebgm.pause();
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
            SoundFX.sfx_minigamebgm.pause();
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
        SoundFX.stopMusic();
    }

    /**
     * #Assessment3
     * Method for spawning new aliens at the top of the screen at a random x location.
     * */
    private void spawnAlien() {
        int x = MathUtils.random(0, Constants.GAME_WIDTH - 64);
        int y = Constants.GAME_HEIGHT;
        Alien alien = new Alien(x, y);
        aliens.add(alien);
        lastAlienSpawn = TimeUtils.nanoTime();
    }

    /**
     * #Assessment3
     * Spawns a new droplet at the coordinates of fireTruck and adds it to the list of droplets.
     */
    private void shootDroplet() {
        Droplet droplet = new Droplet(
                fireTruck.getX() + fireTruck.getWidth()/3.7f,
                128
        );
        droplets.add(droplet);
        lastDropTime = TimeUtils.nanoTime();
    }
    
    /**
     * #Assessment3
     * Updates the current score glyph layout.
     * Invoked whenever an alien is destroyed.
     */
    private void updateScoreText() {
        this.scoreText = "Score: " + this.score;
        this.scoreLayout.setText(font, this.scoreText);
    }

    /**
     * #Assessment3
     * Switches the screen back to the main game.
     * By having the parent screen pass itself as a parameter when creating the new minigame screen, we can
     * return back to the parent screen with all the "state data" still intact, and don't have to instantiate
     * a new screen (which would restart the whole game again).
     *
     * @param game Reference to the current instance of the game "controller".
     */
    private void invokeGameOver(Kroy game) {
        dispose();
        game.setScreen(parent);
    }

    /**
     *  Dispose of all resources used in the minigame (e.g. textures, sounds etc.)
     */
    @Override
    public void dispose() {
        batch.dispose();
    }

}
