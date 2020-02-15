package com.mozarellabytes.kroy.Entities;

// #Assesment3
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.mozarellabytes.kroy.GameState;
import com.mozarellabytes.kroy.Screens.GameScreen;
import com.mozarellabytes.kroy.Utilities.SoundFX;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Alien extends Sprite {

    /**
     *Alien is a class for the alien patrols
     * Contains texture of alien
     * contains attack and been attacked methods
     * movement methods similar to FireTruck as they simply handle queues of vector2's
     * contains methods to choose paths from path type class
     * contains display methods and HUD information displaying methods
    **/

    /** cursory max HP value for alien */
    private final float maxHP = 10;

    /**
     * Control fire station boom rendering duration
     */
    private int fireStationBoomTimes = 300;

    /** Health points */
    private float HP;

    /** Position of Alien in tiles */
    private Vector2 position;

    /** Current PatrolPath path the alien follows*/
    public Queue<Vector2> path;

    /** Used to check if the truck's image should be
     * changed to match the direction it is facing */
    private Vector2 previousTile;

    /**
     * Control the small alien happy time after the fire station destroyed
     */
    private int happyTime = 0;

    /**
     * Control the size change of the nuke, simulate gravity
     */
    private float accelerate = 0;

    /**
    To control the crazyAlien only drop nuke once and check whether the crazy dropped the nuke.
     */
    private boolean dropbomb = true;

    /** Texture for each direction the
     * alien is facing */
    private final Texture lookLeft;
    private final Texture lookRight;
    private final Texture lookUp;
    private final Texture lookDown;
    /**
     * Small aliens facial expression
     */
    private final Texture happy;
    private final Texture notHappy;
    private final Texture littleAngry;
    private final Texture angry;
    private final Texture happyDestroy;

    /**
     * crazyAlien appearance
     */
    private final Texture crazyAlien;

    /**
     * Nuke images
     */
    private final Texture nuke1;
    private final Texture nuke2;
    private final Texture nuke3;
    private final Texture nuke4;




    /** PatrolPath initialised for each alien
     */
    private PatrolPath mainPatrol;

    private EnemyAttackHandler attackHandler;

    /**
     * Constructs alien at certain position
     *
     * @param x     x coordinate of alien (lower left point)
     * @param y     y coordinate of alien (lower left point)
     * @param vertices  A queue of Vector2 types, indicating the next location to move in the patrol path
     * @param speed Determines the speed that the alien follows patrols. Unless set to 0, will be replaced with a random value between 0.05 and 0.2 in PatrolPath.java
     */
    public Alien(float x, float y,Queue<Vector2> vertices, float speed){
        super(new Texture(Gdx.files.internal("sprites/alien/AlienDown.png")));
        this.mainPatrol = new PatrolPath(vertices, speed);
        this.position = new Vector2(x,y);
        this.HP = maxHP;
        this.path = mainPatrol.getPath();

        this.lookLeft = new Texture(Gdx.files.internal("sprites/alien/AlienLeft.png"));
        this.lookRight = new Texture(Gdx.files.internal("sprites/alien/AlienRight.png"));
        this.lookUp = new Texture(Gdx.files.internal("sprites/alien/AlienUp.png"));
        this.lookDown = new Texture(Gdx.files.internal("sprites/alien/AlienDown.png"));
        this.crazyAlien = new Texture(Gdx.files.internal("sprites/alien/crazyAlien.png"));
        this.happy = new Texture(Gdx.files.internal("sprites/alien/happyFace.png"));
        this.notHappy = new Texture(Gdx.files.internal("sprites/alien/notHappy.png"));
        this.littleAngry = new Texture(Gdx.files.internal("sprites/alien/littleAngry.png"));
        this.angry = new Texture(Gdx.files.internal("sprites/alien/Angry.png"));
        this.happyDestroy = new Texture(Gdx.files.internal("sprites/alien/happyDestroy.png"));
        this.nuke1 = new Texture(Gdx.files.internal("sprites/alien/nuke1.png"));
        this.nuke2 = new Texture(Gdx.files.internal("sprites/alien/nuke2.png"));
        this.nuke3 = new Texture(Gdx.files.internal("sprites/alien/nuke3.png"));
        this.nuke4 = new Texture(Gdx.files.internal("sprites/alien/nuke4.png"));


        attackHandler = new EnemyAttackHandler(this);
    }

    /**
     * Changes the direction of the truck depending on the previous tile and the next tile
     *
     * @param nextTile  first tile in the queue (next to be followed)
     */
    private void changeSprite(Vector2 nextTile) {
        if (previousTile != null) {
            if (nextTile.x > previousTile.x) {
                setTexture(lookRight);
            } else if (nextTile.x < previousTile.x) {
                setTexture(lookLeft);
            } else if (nextTile.y > previousTile.y) {
                setTexture(lookUp);
            } else if (nextTile.y < previousTile.y) {
                setTexture(lookDown);
            }
        }
    }

    /**
     * Called every tick and updates the paths to simulate the truck moving along the
     * path
     */
    public void move(ArrayList<FireTruck> fireTrucks) {
        if (this.path.size > 0 && !inCollision(new Vector2(mainPatrol.getPath1First()), fireTrucks)) {
            Vector2 nextTile = mainPatrol.getFirstAndAppend();
            this.position = nextTile;
            changeSprite(nextTile);
            previousTile = nextTile;
        }
    }

    /**
     * #Assesssment3
     *
     * Check if the next tile in an alien's patrol intersects the position of a fireTruck.
     *
     * @param nextTile the next tile in the alien's patrol.
     * @param fireTrucks the array of fireTrucks to iterate through
     * @return true or false, depending on whether the next tile in an alien's patrol intersects the position of a fireTruck.
     */
    private boolean inCollision(Vector2 nextTile, ArrayList<FireTruck> fireTrucks){
        for(FireTruck truck: fireTrucks){
            if(nextTile.x > position.x)
                nextTile.x = (int) position.x + 1;
            else if(nextTile.x < position.x)
                nextTile.x = (int) position.x - 1;

            if(nextTile.y > position.y)
                nextTile.y = (int) position.y + 1;
            else if(nextTile.y < position.y)
                nextTile.y = (int) position.y - 1;


            if(nextTile.x == Math.round(truck.getPosition().x) && nextTile.y == Math.round(truck.getPosition().y)){

                return true;
            }
        }
        return false;
    }

    /**
     * Draws the mini health indicators relative to the alien
     *
     * @param shapeMapRenderer  Renderer that the stats are being drawn to (map  dependant)
     */
    public void drawStats(ShapeRenderer shapeMapRenderer) {
        shapeMapRenderer.rect(this.getPosition().x + 0.2f, this.getPosition().y + 1.3f, 0.3f, 0.8f, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
        shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, 0.6f, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
        if (CountClock.getTotalTime() - CountClock.getRemainTime() <= CountClock.getTotalTime() && GameScreen.fireStationExist() == true) {
            shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, (CountClock.getTotalTime() - CountClock.getRemainTime()) / CountClock.getTotalTime() * 0.6f, Color.RED, Color.RED, Color.RED, Color.RED);
        }
        else if (GameScreen.fireStationExist() == true && CountClock.getRemainTime() <= 0) {
            switch (ThreadLocalRandom.current().nextInt(1, 9)) {
                case 1:
                    shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, this.getHP() / this.maxHP * 0.6f, Color.RED, Color.RED, Color.RED, Color.RED);
                    break;
                case 2:
                    shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, this.getHP() / this.maxHP * 0.6f, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
                    break;
                case 3:
                    shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, this.getHP() / this.maxHP * 0.6f, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY);
                    break;
                case 4:
                    shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, this.getHP() / this.maxHP * 0.6f, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW);
                    break;
                case 5:
                    shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, this.getHP() / this.maxHP * 0.6f, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
                    break;
                case 6:
                    shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, this.getHP() / this.maxHP * 0.6f, Color.ORANGE, Color.ORANGE, Color.ORANGE, Color.ORANGE);
                    break;
                case 7:
                    shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, this.getHP() / this.maxHP * 0.6f, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN);
                    break;
                case 8:
                    shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, this.getHP() / this.maxHP * 0.6f, Color.PURPLE, Color.PURPLE, Color.PURPLE, Color.PURPLE);
                    break;
                default:
                    shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, this.getHP() / this.maxHP * 0.6f, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
            }
        }
        else if (happyTime < 1000){
            shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, this.getHP() / this.maxHP * 0.6f, Color.LIME, Color.LIME, Color.LIME, Color.LIME);
        }
        else{
            shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, this.getHP() / this.maxHP * 0.6f, Color.RED, Color.RED, Color.RED, Color.RED);
        }
    }


    /**
     * Draws the Alien sprite
     *
     * @param mapBatch  Batch that the Alien is being
     *                  drawn to (map dependant)
     */
    public void drawSprite(Batch mapBatch, int width, int height) {
        if(GameScreen.fireStationExist() == true && CountClock.getRemainTime() <= CountClock.getTotalTime()) {
            if ((CountClock.getRemainTime() / CountClock.getTotalTime() > 0.75f) || CountClock.getRemainTime() == CountClock.getTotalTime()) {
                mapBatch.draw(happy, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
            } else if (CountClock.getRemainTime() / CountClock.getTotalTime() < 0.75f && CountClock.getRemainTime() / CountClock.getTotalTime() > 0.5f) {
                mapBatch.draw(notHappy, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
            } else if (CountClock.getRemainTime() / CountClock.getTotalTime() < 0.5f && CountClock.getRemainTime() / CountClock.getTotalTime() > 0.25f) {
                mapBatch.draw(littleAngry, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
            } else {
                mapBatch.draw(angry, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
            }
        }
        else if(happyTime < 1000 && CountClock.getRemainTime() < CountClock.getTotalTime()){
            mapBatch.draw(happyDestroy, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
            happyTime++;
        }
        else if(CountClock.getRemainTime() < CountClock.getTotalTime()){
            mapBatch.draw(angry, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
        }
        mapBatch.draw(this, this.position.x, this.position.y, width, height);
    }


    public void drawSpriteCrazyAlien(Batch mapBatch, int width, int height, ArrayList explosions){
        if (GameScreen.fireStationExist() == true) {
            if (this.getPosition().y > 9.1) {
                mapBatch.draw(crazyAlien, this.position.x, this.position.y, width, height);
                if(this.getPosition().y >10) {
                    mapBatch.draw(nuke1, this.position.x + 1.9f, this.position.y + 1.6f, 1.3f, 2);
                    mapBatch.draw(nuke2, this.position.x + 1.9f, this.position.y + 1.6f, 1.3f, 2);
                    mapBatch.draw(nuke3, this.position.x + 1.9f, this.position.y + 1.6f, 1.3f, 2);
                    mapBatch.draw(nuke4, this.position.x + 1.9f, this.position.y + 1.6f, 1.3f, 2);
                }
                else{
                    if (SoundFX.music_enabled && dropbomb == true) {
                        dropbomb = false;
                        SoundFX.sfx_crazy_alien_drop_bomb.play();
                    }
                    accelerate += 0.02;
                    mapBatch.draw(nuke1, this.position.x + 1.9f + accelerate/2 , this.position.y + 0.1f + accelerate, 1.3f - accelerate, 2 - accelerate);
                    mapBatch.draw(nuke2, this.position.x + 1.9f + accelerate/2, this.position.y + 0.1f + accelerate, 1.3f - accelerate, 2 - accelerate);
                    mapBatch.draw(nuke3, this.position.x + 1.9f + accelerate/2, this.position.y + 0.1f + accelerate, 1.3f - accelerate, 2 - accelerate);
                    mapBatch.draw(nuke4, this.position.x + 1.9f + accelerate/2, this.position.y + 0.1f + accelerate, 1.3f - accelerate, 2 - accelerate);
                }
            }
        }
    }

    /**
     * Helper method that returns where the alien is visually to the player. This is used when
     * checking the range when attacking the fire trucks and getting attacked by the trucks
     *
     * @return a vector where the alien is visually
     */
    public Vector2 getVisualPosition() {
        return new Vector2(this.position.x + 0.5f, this.position.y + 0.5f);
    }

    public Vector2 getPosition() { return this.position;}

    public float getHP() {
        return this.HP;
    }

    public Queue<Vector2> getPath() {
        return this.path;
    }

    public EnemyAttackHandler getAttackHandler() { return attackHandler; }


    /**
     * saves the first element of the path into a temporary vector
     * this is then appended to the end of the path
     * the original first item is returned and removed
     */
    public Vector2 getFirstAndAppend(){
        Vector2 tempVector = new Vector2(this.path.first());
        this.path.addLast(tempVector);
        this.path.removeFirst();
        return tempVector;
    }
}
