package com.mozarellabytes.kroy.Entities;

// #Assesment3
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.mozarellabytes.kroy.Save.SaveAlien;
import com.mozarellabytes.kroy.Screens.GameScreen;
import com.mozarellabytes.kroy.Utilities.PathFinder;
import com.mozarellabytes.kroy.Utilities.SoundFX;

import java.util.*;


public class Alien extends Sprite {

    /**
     *Alien is a class for the alien patrols
     * Contains texture of alien
     * contains attack and been attacked methods
     * movement methods similar to FireTruck as they simply handle queues of vector2s
     * contains methods to choose paths from path type class
     * contains display methods and HUD information displaying methods
    **/
    public final static float VIEW_DISTANCE = 4f;

    /** cursory max HP value for alien */
    private final float maxHP = 10;

    /**
     * Control fire station boom rendering duration
     */
    private int fireStationBoomTimes = 300;

    /** Health points */
    private float HP;

    /** Speed of Alien */
    private float speed;

    /** Position of Alien */
    private Vector2 position;

    /** Position on the grid the alien is coming from */
    private Vector2 fromPosition;

    /** Position on the grid the alien is heading to */
    private Vector2 toPosition;

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
    private final Texture surprise;
    private final Texture confused;
    private  final Texture killConfirmed;
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

    /**
     * The fortress that this et is associated with
     */
    private Fortress masterFortress;

    /** A list of points that the alien circularly pathfinds between in order to patrol */
    private List<Vector2> waypoints;

    /** The index of the waypoint the alien is currently moving towards */
    private int waypointIndex;

    /** The distance the alien is between this waypoint and the next one on a scale of 0 to 1 */
    private float waypointPeriod = 0f;

    /** Handles when the alien attacks trucks */
    private EnemyAttackHandler attackHandler;

    /** Helper object for calculating shortest paths to locations */
    private PathFinder pathfinder;

    /** Where the alien is heading towards */
    private Vector2 goal;

    /** The current AI state the alien is in
     * either of
     *  PURSUING - heading towards a seen truck
     *  PATROLING - following the path designated by the waypoints list
     */
    private AlienState state;
    private  AlienState previousState;

    public boolean seen = false;
    public boolean lost = false;

    private float timer = 0.0f;
    /**
     * Constructs alien at certain position
     *
     * @param x     x coordinate of alien (lower left point)
     * @param y     y coordinate of alien (lower left point)
     * @param waypoints a list of Vectors that form the patrol path that the alien will take
     * @param speed Determines the speed that the alien follows patrols. Unless set to 0, will be replaced with a random value between 0.05 and 0.2 in PatrolPath.java
     */
    public Alien(float x, float y , List<Vector2> waypoints, float speed, Fortress masterFortress, PathFinder pathfinder){
        super(new Texture(Gdx.files.internal("sprites/alien/AlienDown.png")));
        this.speed = speed;
        this.position = new Vector2(x,y);
        this.fromPosition = position.cpy();
        this.HP = maxHP;

        //#Assessment4
        this.waypoints = new ArrayList<>(waypoints);
        waypointIndex = 0;
        this.pathfinder = pathfinder;
        this.goal = waypoints.get(0);
        this.fromPosition = position.cpy();
        this.toPosition = position.cpy();
        this.state = AlienState.PATROLLING;
        this.previousState = AlienState.PATROLLING;
        this.masterFortress = masterFortress;
        this.masterFortress.addFortressAlien(this);

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
        this.killConfirmed = new Texture(Gdx.files.internal("sprites/alien/kill.png"));
        this.surprise = new Texture(Gdx.files.internal("sprites/alien/mark1.png"));
        this.confused = new Texture(Gdx.files.internal("sprites/alien/mark2.png"));
        this.nuke1 = new Texture(Gdx.files.internal("sprites/alien/nuke1.png"));
        this.nuke2 = new Texture(Gdx.files.internal("sprites/alien/nuke2.png"));
        this.nuke3 = new Texture(Gdx.files.internal("sprites/alien/nuke3.png"));
        this.nuke4 = new Texture(Gdx.files.internal("sprites/alien/nuke4.png"));
        this.masterFortress = masterFortress;

        attackHandler = new EnemyAttackHandler(this);
    }

    /**
     * Overloaded version of the alien constructor for loading from a save
     */
    public Alien(SaveAlien s, Fortress masterFortress, PathFinder pathfinder) {
        super(new Texture(Gdx.files.internal("sprites/alien/AlienDown.png")));
        this.speed = s.speed;
        //this.path = s.path;
        this.position = new Vector2(s.x, s.y);
        this.HP = s.HP;
        this.waypoints = s.waypoints;
        this.waypointIndex = s.waypointIndex;
        this.state = s.state;

        this.fromPosition = position.cpy();
        this.goal = waypoints.get(0);

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
        this.surprise = new Texture(Gdx.files.internal("sprites/alien/mark1.png"));
        this.confused = new Texture(Gdx.files.internal("sprites/alien/mark2.png"));
        this.killConfirmed = new Texture(Gdx.files.internal("sprites/alien/kill.png"));
        this.nuke1 = new Texture(Gdx.files.internal("sprites/alien/nuke1.png"));
        this.nuke2 = new Texture(Gdx.files.internal("sprites/alien/nuke2.png"));
        this.nuke3 = new Texture(Gdx.files.internal("sprites/alien/nuke3.png"));
        this.nuke4 = new Texture(Gdx.files.internal("sprites/alien/nuke4.png"));

        this.masterFortress = masterFortress;
        this.pathfinder = pathfinder;

        attackHandler = new EnemyAttackHandler(this);
    }

    /**
     * Changes the direction of the truck (alien) depending on the previous tile and the next tile
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
     * #Assessment4
     * Called once a tick to update the alien's position.
     * If the alien's fortress group has seen one or more trucks they will nondeterministically select one to pursue
     * Once the trucks either become hidden or die they are removed from the seen list and the aliens will return to
     * patrolling.
     * If the alien's fortress group has not seen any trucks the aliens will patrol their set route.
     * @param delta
     * @param fireTrucks
     */
    public void move(float delta, ArrayList<FireTruck> fireTrucks) {
        previousTile = goal;
        switch(this.state) {
            case PURSUING:
                // Chase the first firetruck on the list
                List<FireTruck> seenTrucks = new ArrayList<>(masterFortress.getSeenTrucks());
                if (seenTrucks.size() >= 1) {
                    FireTruck chasedTruck = seenTrucks.get(0);
                    if (chasedTruck != null) {
                        goal = new Vector2(Math.round(chasedTruck.getPosition().x), Math.round(chasedTruck.getPosition().y));

                    }
                } else {
                    this.state = AlienState.PATROLLING;
                }
                ;
                break;
            case PATROLLING:
                // If we're at the current waypoint the new goal is the next waypoint
                if (this.position.equals(waypoints.get(waypointIndex))) {
                    waypointIndex = (waypointIndex + 1) % waypoints.size();
                }
                    // Set new goal
                setNewGoal(waypoints.get(waypointIndex));

                if (masterFortress.getSeenTrucks().size() >= 1) {
                    this.state = AlienState.PURSUING;
                }

                break;
        }

        changeSprite(goal);
        // Move towards current goal
        moveTowardGoal(delta, goal);

       // Report any seen trucks
        reportSeenTrucks(fireTrucks);
    }

    /**
     * #Assessment4
     * Given a goal position, the alien will find a path to this goal and move in the correct direction
     * @param delta the time that has elapsed since the last render call in milliseconds
     * @param goal the position the alien should head towards as a Vector2
     */
    private void moveTowardGoal(float delta, Vector2 goal) {
        if (goal.equals(position)) {
            return;
        } else {
            if (waypointPeriod <= 1f) {
                waypointPeriod += delta;
                Vector2 middlePosition = fromPosition.cpy();
                this.position = middlePosition.lerp(this.toPosition, waypointPeriod);
            } else {
                waypointPeriod = 0f;
                this.fromPosition = this.toPosition;
                if (goal.equals(fromPosition)) {
                    this.toPosition = goal;
                } else {
                    Vector2 errorTest = new Vector2(Math.round(goal.x), Math.round(goal.y));
                    this.toPosition = pathfinder.findPath(errorTest, this.fromPosition)[1];
                }
            }
        }

    }

    /**
     * #Assessment4
     * Setter for 'goal', the position the alien is trying to head towards.
     * @param goal the position the alien is heading towards as a Vector2
     */
    private void setNewGoal(Vector2 goal) {
        this.goal = goal;
        if (!goal.equals(position)) {
            Vector2[] pathToGoal = pathfinder.findPath(goal, this.fromPosition);
            if (pathToGoal.length > 1) {
                this.toPosition = pathfinder.findPath(goal, this.fromPosition)[1];
            }
        }
    }

    /**
     * #Assessment4
     * Notifies the fortress of any firetrucks that are within its vision radius
     * @param fireTrucks
     * @return whether a truck is within its vision radius
     */
    private boolean reportSeenTrucks(ArrayList<FireTruck> fireTrucks) {
        for (FireTruck f : fireTrucks) {
            Rectangle viewVolume = null;

            if (this.getTexture() == this.lookUp) {
               viewVolume = new Rectangle(position.x-.5f, position.y-.5f, 1f, VIEW_DISTANCE);
            } else if (this.getTexture() == this.lookDown) {
               viewVolume = new Rectangle(position.x-.5f, position.y+.5f-VIEW_DISTANCE, 1f, VIEW_DISTANCE);
            } else if (this.getTexture() == this.lookLeft) {
               viewVolume = new Rectangle(position.x+.5f-VIEW_DISTANCE, position.y-.5f, VIEW_DISTANCE, 1f);
            } else {
               viewVolume = new Rectangle(position.x-.5f, position.y-.5f, VIEW_DISTANCE, 1f);
            }
            if (viewVolume.contains(f.getPosition())) {
                if(!f.isInvisible() && f.getType().getName() != "stationTruck") {
                    masterFortress.addTruckToSeen(f);
                } else {
                    lost = true;
                }
            }
        }
       return true;
    }

    /**
     * Draws the mini health indicators relative to the alien
     * @param shapeMapRenderer  Renderer that the stats are being drawn to (map  dependant)
     */
    public void drawStats(ShapeRenderer shapeMapRenderer) {
        /*
        shapeMapRenderer.rect(this.getPosition().x + 0.2f, this.getPosition().y + 1.3f, 0.3f, 0.8f, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
        shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, 0.6f, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
        if (CountClock.getTotalTime() - CountClock.getRemainTime() <= CountClock.getTotalTime() && GameScreen.fireStationExist() == true) {
            shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, (CountClock.getTotalTime() - CountClock.getRemainTime()) / CountClock.getTotalTime() * 0.6f, Color.RED, Color.RED, Color.RED, Color.RED);
        }
        else if (GameScreen.fireStationExist() && CountClock.getRemainTime() <= 0) {
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

         */
    }



    /**
     * Draws the Alien sprite
     *
     * @param mapBatch  Batch that the Alien is being
     *                  drawn to (map dependant)
     */
    public void drawSprite(Batch mapBatch, int width, int height, float delta) {
/*
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
        */

        if(previousState == state) {
        } else {
            if(previousState == AlienState.PATROLLING) {
                seen = true;
            } else {
                if(masterFortress.isSeenTruckDead()) {
                } else {
                    lost = true;
                }
            }
            previousState = state;
        }

        if(seen) {
            timer += delta;
            mapBatch.draw(surprise, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
            if(timer >= 2) {
                seen = false;
                timer = 0.0f;
            }
        } else if (lost) {
            timer += delta;
            mapBatch.draw(confused, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
            if(timer >= 3) {
                lost = false;
                timer = 0.0f;
            }
        } else if(masterFortress.isSeenTruckDead()) {
            timer += delta;
            mapBatch.draw(killConfirmed, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
            if (timer >= 2) {
                masterFortress.setSeenTruckDead(false);
                timer = 0.0f;
            }

        } else if(state == AlienState.PATROLLING) {
            mapBatch.draw(happy, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
        } else {
            mapBatch.draw(angry, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
        }
        //mapBatch.draw(surprise, this.position.x + 0.7f, this.position.y + 1.25f, width, height);
        //mapBatch.draw(confused, this.position.x + 0.7f, this.position.y + 1.25f, width, height);


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

    public Vector2 getPosition() { return this.position;}

    public float getHP() {
        return this.HP;
    }

    public Queue<Vector2> getPath() {
        return this.path;
    }

    /**
     * Returns the attack handler for this alien
     * @return attackHandler
     */
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

    public float getSpeed() { return speed; }

    public List<Vector2> getWaypoints() { return this.waypoints; }

    public int getWaypointIndex() { return this.waypointIndex; }

    public AlienState getState() { return state; }
}
