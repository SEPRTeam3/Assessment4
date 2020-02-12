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
import com.mozarellabytes.kroy.Screens.GameScreen;
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

    /** Texture for each direction the
     * alien is facing */
    private final Texture lookLeft;
    private final Texture lookRight;
    private final Texture lookUp;
    private final Texture lookDown;
    private final Texture happy;
    private final Texture notHappy;
    private final Texture littleAngry;
    private final Texture angry;
    private final Texture crazyAlien;
    private final Texture fireStationBoom1;
    private final Texture fireStationBoom2;
    private final Texture fireStationBoom3;
    private final Texture fireStationBoom4;
    private final Texture fireStationBoom5;
    private final Texture fireStationBoom6;
    private final Texture fireStationBoom7;
    private final Texture fireStationBoom8;
    private final Texture fireStationBoom9;
    private final Texture fireStationBoom10;
    private final Texture fireStationBoom11;
    private final Texture fireStationBoom12;
    private final Texture fireStationBoom13;
    private final Texture fireStationBoom14;
    private final Texture fireStationBoom15;
    private final Texture fireStationBoom16;
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
        this.fireStationBoom1 = new Texture(Gdx.files.internal("sprites/alien/fe1.png"));
        this.fireStationBoom2 = new Texture(Gdx.files.internal("sprites/alien/fe2.png"));
        this.fireStationBoom3 = new Texture(Gdx.files.internal("sprites/alien/fe3.png"));
        this.fireStationBoom4 = new Texture(Gdx.files.internal("sprites/alien/fe4.png"));
        this.fireStationBoom5 = new Texture(Gdx.files.internal("sprites/alien/fe5.png"));
        this.fireStationBoom6 = new Texture(Gdx.files.internal("sprites/alien/fe6.png"));
        this.fireStationBoom7 = new Texture(Gdx.files.internal("sprites/alien/fe7.png"));
        this.fireStationBoom8 = new Texture(Gdx.files.internal("sprites/alien/fe8.png"));
        this.fireStationBoom9 = new Texture(Gdx.files.internal("sprites/alien/fe9.png"));
        this.fireStationBoom10 = new Texture(Gdx.files.internal("sprites/alien/fe10.png"));
        this.fireStationBoom11 = new Texture(Gdx.files.internal("sprites/alien/fe11.png"));
        this.fireStationBoom12 = new Texture(Gdx.files.internal("sprites/alien/fe12.png"));
        this.fireStationBoom13 = new Texture(Gdx.files.internal("sprites/alien/fe13.png"));
        this.fireStationBoom14 = new Texture(Gdx.files.internal("sprites/alien/fe14.png"));
        this.fireStationBoom15 = new Texture(Gdx.files.internal("sprites/alien/fe15.png"));
        this.fireStationBoom16 = new Texture(Gdx.files.internal("sprites/alien/fe16.png"));
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
        if (CountClock.getTotalTime() - CountClock.getRemainTime() <= CountClock.getTotalTime()) {
            shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, (CountClock.getTotalTime() - CountClock.getRemainTime()) / CountClock.getTotalTime() * 0.6f, Color.RED, Color.RED, Color.RED, Color.RED);
        }
        else {
            switch(ThreadLocalRandom.current().nextInt(1,9)){
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
    }


    /**
     * Draws the Alien sprite
     *
     * @param mapBatch  Batch that the Alien is being
     *                  drawn to (map dependant)
     */
    public void drawSprite(Batch mapBatch, int width, int height) {
        mapBatch.draw(this, this.position.x, this.position.y, width, height);
        if ((CountClock.getRemainTime() / CountClock.getTotalTime() > 0.75f) || CountClock.getRemainTime() == CountClock.getTotalTime()) {
            mapBatch.draw(happy, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
        }
        else if(CountClock.getRemainTime() / CountClock.getTotalTime() < 0.75f && CountClock.getRemainTime() / CountClock.getTotalTime() > 0.5f){
            mapBatch.draw(notHappy, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
        }
        else if(CountClock.getRemainTime() / CountClock.getTotalTime() < 0.5f && CountClock.getRemainTime() / CountClock.getTotalTime() > 0.25f){
            mapBatch.draw(littleAngry, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
        }
        else{
            mapBatch.draw(angry, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
        }
    }


    public void drawSpriteCrazyAlien(Batch mapBatch, int width, int height){
        if (GameScreen.fireStationExist() == true) {
            mapBatch.draw(crazyAlien, this.position.x, this.position.y, width, height);
            mapBatch.draw(nuke1, this.position.x + 1.9f, this.position.y + 1.6f, 1.3f, 2);
            mapBatch.draw(nuke2, this.position.x + 1.9f, this.position.y + 1.6f, 1.3f, 2);
            mapBatch.draw(nuke3, this.position.x + 1.9f, this.position.y + 1.6f, 1.3f, 2);
            mapBatch.draw(nuke4, this.position.x + 1.9f, this.position.y + 1.6f, 1.3f, 2);
            }
        if(fireStationBoomTimes > 0 && this.getPosition().y < 9.1){
                System.out.println(this.getPosition().y);
                fireStationBoomTimes--;
                mapBatch.draw(fireStationBoom1, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom2, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom3, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom4, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom5, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom6, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom7, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom8, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom9, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom10, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom11, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom12, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom13, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom14, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom15, 2, 8, 8, 8);
                mapBatch.draw(fireStationBoom16, 2, 8,8, 8);
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
