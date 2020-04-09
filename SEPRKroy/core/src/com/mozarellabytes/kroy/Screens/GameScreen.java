package com.mozarellabytes.kroy.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.Entities.*;
import com.mozarellabytes.kroy.GameState;
import com.mozarellabytes.kroy.Kroy;
import com.mozarellabytes.kroy.Save.Save;
import com.mozarellabytes.kroy.Save.SaveAlien;
import com.mozarellabytes.kroy.Utilities.*;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;

/**
 * The Screen that our game is played in.
 * Accessed from MenuScreen when the user
 * clicks the Start button, and exits when
 * the player wins or loses the game
 */
public class GameScreen implements Screen {

    /** Instance of our game that allows us the change screens */
    private final Kroy game;

    /** Renders our tiled map */
    private final OrthogonalTiledMapRenderer mapRenderer;

    /** Camera to set the projection for the screen */
    private final OrthographicCamera camera;

    /** Renders shapes such as the health/reserve
     * stat bars above entities */
    private final ShapeRenderer shapeMapRenderer;

    private ShaderProgram shader;

    /** Stores the layers of our tiled map */
    private final MapLayers mapLayers;

    /** Stores the structures layers, stores the background layer */
    private final int[] structureLayersIndices, backgroundLayerIndex;

    /** Batch that has dimensions in line with the 40x25 map */
    private final Batch mapBatch;

    /** Used for shaking the camera when a bomb hits a truck */
    private final CameraShake camShake;

    /** Stores whether the game is running or is paused */
    private PlayState state;


    /**
     * Deals with all the user interface on the screen
     * that does not want to be inline with the map
     * coordinates, e.g. big stat bars, buttons, pause
     * screen
     */
    private final GUI gui;

    /**
     * Stores the progress through the game. It keeps
     * track of trucks/fortresses and will end the game
     * once an end game condition has been met
     */
    public final GameState gameState;

    /** List of Fortresses currently active on the map */
    private final ArrayList<Fortress> fortresses;

    /** creat the fire station */
    private final FireStation station;

    /** creat the truck which stay in the station */
    private final FireTruck stationTruck;

    /**Creat the crazy Alien*/
    private Alien crazyAlien;

    /**Control the start screen shake time, reset the screen position*/
    private int ini_shake = 0;

    /** Alien patrols to attack fire engines */
    private Queue<Alien> aliens;

    /** The FireTruck that the user is currently drawing a path for */
    public FireTruck selectedTruck;

    /**
     * The list of all active explosions
     */
    private ArrayList<Explosion> explosions;
    /**
     * The list of all explosions to be removed
     */
    private ArrayList<Explosion> explosionsToRemove;

    /** The entity that the user has clicked on to show
     * the large stats in the top left corner */
    public Object selectedEntity;

    public PathFinder getPathFinder() {
        return pathFinder;
    }


    /** Play when the game is being played
     * Pause when the pause button is clicked */
    public enum PlayState {
        PLAY, PAUSE
    }


    /**
     * Check weather the station has been destroyed or not
     */
    private static boolean stationExists = true;

    /**
     * Check whether the game is paused. If yes, then pause the clock, otherwise, continue.
     * 0 = unpaused, 1 = paused
     */
    private int flag = 0;

    /**Set go to minigame when destroyed the second fortress*/
    private int MiniGameTime = 1;

    /**Set the max fire truck in the station to be destroyed after nuke attacked*/
    private int fireEngineBlowUp = 6;
    /**
     * Constructor which has the game passed in
     *
     * @param game LibGdx game instance
     */

    public static final int STATION_X = 3;
    public static final int STATION_Y = 8;
    private PathFinder pathFinder;

    public PowerUps powerUps;
    public GameScreen(Kroy game) {
        pathFinder = new PathFinder(this);

        SoundFX.stopMusic();
        ArrayList<Vector2> vertices = new ArrayList<>();
        this.game = game;

        crazyAlien = null;
        stationExists = true;

        state = PlayState.PLAY;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);

        TiledMap map = new TmxMapLoader().load("maps/YorkMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.TILE_WxH);
        mapRenderer.setView(camera);
//        ShaderProgram.pedantic = false;
//        shader = new ShaderProgram(Gdx.files.internal("shaders/dark.vsh"), Gdx.files.internal("shaders/dark.fsh"));

        shapeMapRenderer = new ShapeRenderer();
        shapeMapRenderer.setProjectionMatrix(camera.combined);

        gui = new GUI(game, this);

        Gdx.input.setInputProcessor(new GameInputHandler(this, gui));

        gameState = new GameState();
        camShake = new CameraShake();

        //Orders renderer to start rendering the background, then the player layer, then structures
        mapLayers = map.getLayers();
        backgroundLayerIndex = new int[]{mapLayers.getIndex("background")};

        structureLayersIndices = new int[]{mapLayers.getIndex("structures"),
                mapLayers.getIndex("transparentStructures")};

        station = new FireStation(STATION_X, STATION_Y);

        // Instantiate a dummy fireTruck at the position (5, 8) for the crazy alien to aim at and attack.
        stationTruck = new FireTruck(this, new Vector2(5,8), FireTruckType.Station);

        spawn(FireTruckType.Ocean);
        spawn(FireTruckType.Speed);
        spawn(FireTruckType.Tank);
        spawn(FireTruckType.Attack);
        spawn(FireTruckType.Station);
        gameState.removeFireTruck();

        fortresses = new ArrayList<>();
        fortresses.add(new Fortress(12, 24.5f, FortressType.Revs));
        fortresses.add(new Fortress(30.5f, 23.5f, FortressType.Walmgate));
        fortresses.add(new Fortress(16, 9.5f, FortressType.Clifford));

        //#Assessment3 Added 3 new fortresses
        fortresses.add(new Fortress (44.5f, 4.5f, FortressType.TrainStation));
        fortresses.add(new Fortress (45, 22, FortressType.Minster));
        fortresses.add(new Fortress (29, 9, FortressType.Shambles));

        //#Assesment3 Added explosion effects
        explosions = new ArrayList<>();
        explosionsToRemove = new ArrayList<>();

        /* #Assessment3
         * Hardcoded alien paths
         */

        aliens = new Queue<>();
//        vertices.add(new Vector2(13,7));
//        vertices.add(new Vector2(18,11));
//        vertices.add(new Vector2(13,11));
//        aliens.addLast(new Alien(13f, 7f, vertices, 0f, fortresses.get(0), this.pathFinder));
//        vertices.clear();
//
        vertices.add(new Vector2(8,5));
        vertices.add(new Vector2(13,5));
        vertices.add(new Vector2(13,1));
        vertices.add(new Vector2(4,1));
        vertices.add(new Vector2(4,4));
        vertices.add(new Vector2(8,4));
        vertices.add(new Vector2(8,5));
        aliens.addLast(new Alien(8f,5f,vertices,0f, fortresses.get(0), this.pathFinder));
//
//        vertices.clear();
//        vertices.add(new Vector2(28,5));
//        vertices.add(new Vector2(31,5));
//        vertices.add(new Vector2(31,6));
//        vertices.add(new Vector2(35,6));
//        vertices.add(new Vector2(35,4));
//        vertices.add(new Vector2(40,4));
//        vertices.add(new Vector2(40,6));
//        vertices.add(new Vector2(48,6));
//        vertices.add(new Vector2(48,1));
//        vertices.add(new Vector2(40,1));
//        vertices.add(new Vector2(35,1));
//        vertices.add(new Vector2(32,1));
//        vertices.add(new Vector2(32,2));
//        vertices.add(new Vector2(28,2));
//        vertices.add(new Vector2(28,5));
//        aliens.addLast(new Alien(28f,5f,vertices,0f, fortresses.get(0), this.pathFinder));
//        vertices.clear();
//
//        vertices.add(new Vector2(48,28));
//        vertices.add(new Vector2(44,28));
//        vertices.add(new Vector2(44,26));
//        vertices.add(new Vector2(47,26));
//        vertices.add(new Vector2(44,26));
//        vertices.add(new Vector2(44,24));
//        vertices.add(new Vector2(48,24));
//        vertices.add(new Vector2(44,24));
//        vertices.add(new Vector2(44,26));
//        vertices.add(new Vector2(47,26));
//        vertices.add(new Vector2(44,26));
//        vertices.add(new Vector2(44,28));
//        vertices.add(new Vector2(48,28));
//        aliens.addLast(new Alien(48f,28f,vertices,0f, fortresses.get(0), this.pathFinder));
//        vertices.clear();
//
//        vertices.add(new Vector2(26,28));
//        vertices.add(new Vector2(37,28));
//        vertices.add(new Vector2(37,23));
//        vertices.add(new Vector2(34,23));
//        vertices.add(new Vector2(34,28));
//        vertices.add(new Vector2(30,28));
//        vertices.add(new Vector2(30,23));
//        vertices.add(new Vector2(26,23));
//        vertices.add(new Vector2(26,28));
//        aliens.addLast(new Alien(26f,28f,vertices,0f, fortresses.get(0), this.pathFinder));
//        vertices.clear();
//
//        vertices.add(new Vector2(17,28));
//        vertices.add(new Vector2(17,21));
//        vertices.add(new Vector2(14,21));
//        vertices.add(new Vector2(14,16));
//        vertices.add(new Vector2(12,16));
//        vertices.add(new Vector2(12,22));
//        vertices.add(new Vector2(8,22));
//        vertices.add(new Vector2(8,28));
//        vertices.add(new Vector2(17,28));
//        aliens.addLast(new Alien(10f,28f,vertices,0f, fortresses.get(0), this.pathFinder));
//        vertices.clear();
//
//        vertices.add(new Vector2(10,18));
//        vertices.add(new Vector2(10,16));
//        vertices.add(new Vector2(8,16));
//        vertices.add(new Vector2(8,14));
//        vertices.add(new Vector2(2,14));
//        vertices.add(new Vector2(2,17));
//        vertices.add(new Vector2(5,17));
//        vertices.add(new Vector2(5,18));
//        vertices.add(new Vector2(10,18));
//        aliens.addLast(new Alien(10f,18f,vertices,0f, fortresses.get(0), this.pathFinder));
//        vertices.clear();
//
//        vertices.add(new Vector2(45,18));
//        vertices.add(new Vector2(45,15));
//        vertices.add(new Vector2(41,15));
//        vertices.add(new Vector2(41,18));
//        vertices.add(new Vector2(45,18));
//        aliens.addLast(new Alien(45f, 18f, vertices,0f, fortresses.get(0), this.pathFinder));
//        vertices.clear();
//
//        vertices.add(new Vector2(42,13));
//        vertices.add(new Vector2(42,9));
//        vertices.add(new Vector2(37,9));
//        vertices.add(new Vector2(37,8));
//        vertices.add(new Vector2(36,8));
//        vertices.add(new Vector2(32,8));
//        vertices.add(new Vector2(32,13));
//        vertices.add(new Vector2(42,13));
//        aliens.addLast(new Alien(42f, 13f, vertices,0f, fortresses.get(0), this.pathFinder));
//        vertices.clear();
//

//        vertices.add(new Vector2(2.9f,25));
//        vertices.add(new Vector2(2.9f,6));
//        crazyAlien = (new Alien(2.9f,30f, vertices,0.0175f, fortresses.get(0), this.pathFinder));
//        crazyAlien.getAttackHandler().setCrazy();

        vertices.clear();

        // Set the origin point to which all of the polygon's local vertices are relative to.
        for (FireTruck truck : station.getTrucks()) {
            truck.setOrigin(Constants.TILE_WxH / 2, Constants.TILE_WxH / 2);
        }

        mapBatch = mapRenderer.getBatch();

        if (SoundFX.music_enabled) {
            SoundFX.sfx_soundtrack.setVolume(.5f);
            SoundFX.sfx_soundtrack.play();
        }

        powerUps = new PowerUps(mapBatch);

    }

    // Alternate constructor from savegame
    public GameScreen(Kroy game, Save save) {
        SoundFX.stopMusic();
        Queue<Vector2> vertices;
        vertices = new Queue<>();
        this.game = game;

        crazyAlien = null;
        stationExists = true;

        state = PlayState.PAUSE;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);

        TiledMap map = new TmxMapLoader().load("maps/YorkMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.TILE_WxH);
        mapRenderer.setView(camera);

        shapeMapRenderer = new ShapeRenderer();
        shapeMapRenderer.setProjectionMatrix(camera.combined);

        gui = new GUI(game, this);

        Gdx.input.setInputProcessor(new GameInputHandler(this, gui));

        gameState = save.gameState;
        camShake = new CameraShake();

        //Orders renderer to start rendering the background, then the player layer, then structures
        mapLayers = map.getLayers();
        backgroundLayerIndex = new int[]{mapLayers.getIndex("background")};

        structureLayersIndices = new int[]{mapLayers.getIndex("structures"),
                mapLayers.getIndex("transparentStructures")};

        station = new FireStation(this, save.station);

        // Instantiate a dummy fireTruck at the position (5, 8) for the crazy alien to aim at and attack.
        stationTruck = new FireTruck(this, new Vector2(5,8), FireTruckType.Station);



        fortresses = new ArrayList<>();
        fortresses.add(new Fortress(12, 24.5f, FortressType.Revs));
        fortresses.add(new Fortress(30.5f, 23.5f, FortressType.Walmgate));
        fortresses.add(new Fortress(16, 9.5f, FortressType.Clifford));

        //#Assessment3 Added 3 new fortresses
        fortresses.add(new Fortress (44.5f, 4.5f, FortressType.TrainStation));
        fortresses.add(new Fortress (45, 22, FortressType.Minster));
        fortresses.add(new Fortress (29, 9, FortressType.Shambles));

        //#Assesment3 Added explosion effects
        explosions = new ArrayList<>();
        explosionsToRemove = new ArrayList<>();

        /* #Assessment3
         * Hardcoded alien paths
         */

        aliens = new Queue();
        for (SaveAlien a : save.aliens) {
            aliens.addLast(new Alien(a));

        }

//        crazyAlien = (new Alien(save.crazyAlien.x,save.crazyAlien.y, save.crazyAlien.path,save.crazyAlien.speed, fortresses.get(0), this.pathFinder));
//        crazyAlien.getAttackHandler().setCrazy();


        // Set the origin point to which all of the polygon's local vertices are relative to.
        for (FireTruck truck : station.getTrucks()) {
            truck.setOrigin(Constants.TILE_WxH / 2, Constants.TILE_WxH / 2);
        }

        mapBatch = mapRenderer.getBatch();

        if (SoundFX.music_enabled) {
            SoundFX.sfx_soundtrack.setVolume(.5f);
            SoundFX.sfx_soundtrack.play();
        }
    }

    @Override
    public void show() {
        if (SoundFX.music_enabled) {
            SoundFX.sfx_soundtrack.setVolume(.5f);
            SoundFX.sfx_soundtrack.play();
        }
    }

    public static boolean fireStationExist(){
        return stationExists;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (ini_shake < 5) {
            ini_shake ++;
            CameraShake.update(delta, camera, new Vector2(camera.viewportWidth / 2f, camera.viewportHeight / 2f));
            camShake.shakeIt(.01f);
            CountClock.set_remain_Time(CountClock.getTotalTime());
            if(ini_shake == 1){
                SoundFX.playGameMusic();
            }
        }
        if(MiniGameTime == 0){
            if(SoundFX.music_enabled) {
                SoundFX.playGameMusic();
            }
        }
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render(backgroundLayerIndex);

        mapBatch.begin();
        mapBatch.setShader(shader);





        for (FireTruck truck : station.getTrucks()) {
            powerUps.OnPowerUpTile(truck);

            if(!truck.getVisualPosition().equals(new Vector2(9.5f,8.5f))) {
                truck.drawPath(mapBatch);
                truck.drawSprite(mapBatch);
            }
        }

        if(stationExists) {
            station.draw(mapBatch);
        }

        for (Fortress fortress : this.fortresses) {
            fortress.draw(mapBatch);
        }

        mapBatch.end();
        mapRenderer.render(structureLayersIndices);

        mapBatch.begin();
        mapBatch.setShader(shader);

        //#Assessment3
        for(Alien alien:aliens) {
            alien.drawSprite(mapBatch,1,1);
        }

        if(fireStationExist()) {
//            crazyAlien.drawSpriteCrazyAlien(mapBatch, 5, 5,explosions);
        }

        for(Explosion explosion:explosions){
            if(explosion.drawExplosion(mapBatch)){
                explosionsToRemove.add(explosion);
            }
        }
        explosions.removeAll(explosionsToRemove);

        powerUps.spawnPowerUps();
        powerUps.ItemBoxUpdate();

        mapBatch.end();

        shapeMapRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (FireTruck truck : station.getTrucks()) {
            truck.drawStats(shapeMapRenderer);

        }

        stationTruck.drawStats(shapeMapRenderer);

        for (Fortress fortress : fortresses) {
            fortress.drawStats(shapeMapRenderer);
            for (Bomb bomb : fortress.getAttackHandler().getBombs()) {
                bomb.drawBomb(shapeMapRenderer);
            }
        }

        //#Assessment3
        for(Alien alien:aliens) {
            alien.drawStats(shapeMapRenderer);
            for (Bomb bomb : alien.getAttackHandler().getBombs()) {
                bomb.alienDrawBomb(shapeMapRenderer);
            }
        }

//        for (Bomb bomb : crazyAlien.getAttackHandler().getBombs()) {
//            bomb.crazyAlienDrawBomb(shapeMapRenderer);
//        }

        shapeMapRenderer.end();

        gui.renderSelectedEntity(selectedEntity);

        switch (state) {
            case PLAY:
                flag = 0;
                this.update(delta);
                break;
            case PAUSE:
                // render dark background
                flag = 1;
                Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
                shapeMapRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeMapRenderer.setColor(0, 0, 0, 0.5f);
                shapeMapRenderer.rect(0, 0, this.camera.viewportWidth, this.camera.viewportHeight);
                shapeMapRenderer.end();
                gui.renderPauseScreenText();
        }
        gui.renderButtons();
        gui.renderClock(flag);
    }



    /**
     * Manages all of the updates/checks during the game
     *
     * @param delta The time in millisecond since the last render
     */
    private void update(float delta) {
        gameState.hasGameEnded(game);
        CameraShake.update(delta, camera, new Vector2(camera.viewportWidth / 2f, camera.viewportHeight / 2f));

        station.restoreTrucks();
        station.checkForCollisions();
        gameState.setTrucksInAttackRange(0);

//        if (gui.getCountClock() != null) {
//            if (gui.getCountClock().hasEnded()) {
//                if (crazyAlien.getPosition().y > 9) {
//                    crazyAlien.move(station.getTrucks());
//                } else {
//                    if(stationExists) {
//                        stationExists = false;
//                        explosions.add(new Explosion(8,8,2,7,0.1f));
//                    }
//                }
//            }
//
//            // Once crazyAlien disappears off the screen, it should stop attacking the fireStation.
//            if (crazyAlien.getPosition().y >= 9.1f && crazyAlien.getPosition().y < 30) {
//                crazyAlien.getAttackHandler().setPosition(new Vector2(crazyAlien.getPosition().x + 3, crazyAlien.getPosition().y));
//                crazyAlien.getAttackHandler().attack(stationTruck, false, SoundFX.sfx_crazy_alien_attack);
//                if (crazyAlien.getAttackHandler().updateBombs()) {
//                    camShake.shakeIt(.4f);
//                }
//            }
//        }

        //#Assessment3
        for(Alien alien:aliens){
            alien.move(delta, station.getTrucks());
        }

//        //Iterate To check that when nuke explosion, any trucks in the station.
//        for (int i = 0; i < station.getTrucks().size(); i++) {
//            if (crazyAlien.getPosition().y < 9.1 && fireEngineBlowUp > 0) {
//                FireTruck truck = station.getTruck(i);
//                fireEngineBlowUp--;
//                if ((truck.getPosition().x == 4 && truck.getPosition().y == 8) || (truck.getPosition().x == 5 && truck.getPosition().y == 8) || (truck.getPosition().x == 6 && truck.getPosition().y == 8)) {
//                    gameState.removeFireTruck();
//                    station.destroyTruck(truck);
//                }
//                if (SoundFX.music_enabled) {
//                    SoundFX.sfx_station_destroyed.play();
//                }
//            }
//        }

        for (int i = 0; i < station.getTrucks().size(); i++) {
            FireTruck truck = station.getTruck(i);

            truck.move();
            truck.updateSpray();
            System.out.print((truck.getType()));
            // manages attacks between trucks and fortresses
            for (Fortress fortress : this.fortresses) {
                if(!truck.isInvisible()) {
                    if (fortress.getAttackHandler().withinRange(truck.getVisualPosition())) {
                        fortress.getAttackHandler().setAttackLevel(fortress.getLevel());
                        fortress.getAttackHandler().attack(truck, true, SoundFX.sfx_fortress_attack);
                    }
                }
                if (truck.fortressInRange(fortress.getPosition())) {
                    gameState.incrementTrucksInAttackRange();
                    truck.attack(fortress);

                    if (fortress.getHP() <= 0) {
                        // #Assessment3
                        // ...and if so, switch screen to the minigame.
                        MiniGameTime = 0;
                        SoundFX.stopMusic();
                        toMinigameScreen(truck);
                    }

                    if(truck.getAttacking() && gui.getCountClock() == null) {
                        gui.newClock();
                    }
                    break;
                }

                // Check if fortress is destroyed...

            }

            // #Assessment3
            for(Alien alien : this.aliens) {
                alien.getAttackHandler().setPosition(alien.getPosition());
                if (!truck.isInvisible()) {
                    if (alien.getAttackHandler().withinRange(truck.getVisualPosition())) {
                        alien.getAttackHandler().attack(truck, true, SoundFX.sfx_alien_attack);
                    }
                    if (alien.getAttackHandler().updateBombs()) {
                        if (SoundFX.music_enabled) {
                            SoundFX.sfx_alien_hit_fire_engine_sound.play();
                        } else {
                            camShake.shakeIt(.2f);
                        }
                    }
                }
            }

            // Check if truck is destroyed
            if (truck.getHP() <= 0) {
                if (!truck.hasResurrection()) {
                    if (SoundFX.music_enabled) {
                        SoundFX.sfx_fire_engine_destroyed.play();
                    }
                    mapBatch.begin();
                    explosions.add(new Explosion(3, 3, (int) truck.getPosition().x - 1, (int) truck.getPosition().y - 1, 0.025f));
                    mapBatch.end();
                    gameState.removeFireTruck();
                    station.destroyTruck(truck);
                    if (truck.equals(this.selectedTruck)) {
                        this.selectedTruck = null;
                    }
                } else {
                    if (SoundFX.music_enabled) {
                        // play sound
                    }
                    //[;ay aniation
                    powerUps.Resurrection(truck);
                }
            }
        }

        for (int i = 0; i < this.fortresses.size(); i++) {
            Fortress fortress = this.fortresses.get(i);

            boolean hitTruck = fortress.getAttackHandler().updateBombs();
            if (hitTruck) {
                camShake.shakeIt(.2f);
            }
            if (fortress.getHP() <= 0) {
                gameState.addFortress();
                this.fortresses.remove(fortress);
                mapBatch.begin();
                explosions.add(new Explosion(10, 10, (int) fortress.getPosition().x - 5, (int) fortress.getPosition().y - 5, 0.05f));
                mapBatch.end();
                if (SoundFX.music_enabled) {
                    SoundFX.sfx_fortress_destroyed.play();
                }
            }
        }


        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (gameState.getTrucksInAttackRange() > 0) {
                if (SoundFX.music_enabled) {
                    SoundFX.playTruckAttack();
                }
            }
            else {
                SoundFX.stopTruckAttack();
            }
        }


        shapeMapRenderer.end();
        shapeMapRenderer.setColor(Color.WHITE);
        gui.renderSelectedEntity(selectedEntity);
    }



    /**
     * Changes the current screen to the minigame.
     * Passes in the current <code>game</code> instance so the minigame can return back to the main game.
     */
    public void toMinigameScreen(FireTruck truck) {
        game.setScreen(new MinigameScreen(game, this, truck));
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

    @Override
    public void dispose() {
        mapRenderer.dispose();
        shapeMapRenderer.dispose();
        mapBatch.dispose();
        SoundFX.sfx_soundtrack.stop();
    }

    /**
     * Checks whether the player has clicked on a truck and sets that
     * truck to selected truck and entity
     *
     * @param position  coordinates of where the user clicked
     * @return          <code>true</code> if player clicks on a truck
     *                  <code>false</code> otherwise
     */
    public boolean checkClick(Vector2 position) {
        for (int i = this.station.getTrucks().size() - 1; i >= 0; i--) {
            FireTruck selectedTruck = this.station.getTruck(i);
            Vector2 truckTile = getTile(selectedTruck.getPosition());
            if (position.equals(truckTile) &&!selectedTruck.getMoving()) {
                this.selectedTruck = this.station.getTruck(i);
                this.selectedEntity = this.station.getTruck(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the coordinates of the tile that the truck is closest to
     *
     * @param position  coordinates of truck
     * @return          coordinates of closest tile
     */
    private Vector2 getTile(Vector2 position) {
        return new Vector2((float) Math.round((position.x)), (float) Math.round(position.y));
    }

    /**
     * Checks whether the user has clicked on a the last tile in a
     * truck's trail path and selects the truck as active truck and
     * entity
     *
     * @param position  the coordinates where the user clicked
     * @return          <code>true</code> if player clicks on the
     *                  last tile in a truck's path
     *                  <code>false</code> otherwise
     */
    public boolean checkTrailClick(Vector2 position) {
        for (int i=this.station.getTrucks().size()-1; i>=0; i--) {
            if (!this.station.getTruck(i).path.isEmpty()) {
                if (position.equals(this.station.getTruck(i).path.last())) {
                    this.selectedTruck = this.station.getTruck(i);
                    this.selectedEntity = this.station.getTruck(i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the tile that the user is trying to add to the
     *  truck's path is on the road. This uses the custom "road"
     * boolean property in the collisions layer within the tiled map
     *
     * @param x x coordinate of tile
     * @param y y coordinate of tile
     * @return  <code>true</code> if the tile is a road
     *          <code>false</code> otherwise
     */
    public boolean isRoad(int x, int y) {
        return ((TiledMapTileLayer) mapLayers.get("collisions")).getCell(x, y).getTile().getProperties().get("road").equals(true);
    }

    /**
     * Changes from GameScreen to Control screen, passing "game" so that when
     * the player exits the control screen, it knows to return to the Game
     */
    public void toControlScreen() {
        game.setScreen(new ControlsScreen(game, this, "game"));
    }

    /** Exits the main game screen and goes to the menu, called when the home
     * button is clicked */
    public void toHomeScreen() {
        game.setScreen(new MenuScreen(game));
        SoundFX.sfx_soundtrack.dispose();
    }

    /**
     * Creates a new FireEngine, plays a sound and adds it gameState to track
     * @param type Type of truck to be spawned (Ocean, Speed)
     */
    private void spawn(FireTruckType type) {
        SoundFX.sfx_truck_spawn.play();
        if(type.equals(FireTruckType.Station)) {
            station.spawn(new FireTruck(this, new Vector2(9, 8), type));
        }
        else{
            station.spawn(new FireTruck(this, new Vector2(6, 8), type));
        }
        gameState.addFireTruck();
    }

    /** Toggles between Play and Pause state when the Pause button is clicked */
    public void changeState() {
        if (this.state.equals(PlayState.PLAY)) {
            this.state = PlayState.PAUSE;
        } else {
            this.state = PlayState.PLAY;
        }
    }

    public GUI getGui() {
        return gui;
    }

    public FireStation getStation() {
        return this.station;
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }

    public ArrayList<Fortress> getFortresses() {
        return this.fortresses;
    }

    public PlayState getState() {
        return this.state;
    }

    public Queue<Alien> getAliens() { return this.aliens; }

    public void saveState() {
        game.setScreen(new SaveScreen(game, this));
    }

    public Alien getCrazyAlien() { return this.crazyAlien; }
}

