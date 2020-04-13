package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.Utilities.SoundFX;
import java.util.concurrent.ThreadLocalRandom;

import java.util.ArrayList;

//#Assessment 3

/**
 * Class that handles hostile attacks
 */
public class EnemyAttackHandler {

    /**
     * The attack range of the attack handler's parent
     */
    private float attackRange;
    /**
     * The attack power of the attack handler's parent
     */
    private float attackPower;
    /**
     * The attack level of the attack handler parent
     */
    private int attackLevel;
    /**
     * The amount of time after an attack that the attack handler has to wait before making another
     */
    private long delay;
    /**
     * The time at which the attack handler last performed an attack
     */
    private long timeOfLastAttack;
    /**
     * The position of the attack handler's parent
     */
    private Vector2 position;
    /**
     * An array list of all active bombs that have been created by the attack handler
     */
    private ArrayList<Bomb> bombs;

    /**
     * The constructor called when an instantiated attack handler has a fortress parent
     * @param entity The fortress to which the attack handler is a child
     */
    EnemyAttackHandler(Fortress entity){
        position = entity.getPosition();
        attackRange = entity.getFortressType().getRange();
        attackPower = entity.getFortressType().getAP();
        attackLevel = entity.getLevel();
        delay = entity.getFortressType().getDelay();
        bombs = new ArrayList<Bomb>();
    }

    /**
     * The constructor called when an instantiated attack handler has an alien parent
     * @param entity The alien to which the attack handler is a child
     */
    EnemyAttackHandler(Alien entity){
        position = entity.getPosition();
        attackRange = ThreadLocalRandom.current().nextInt(2, 4);
        attackPower = 3f;
        attackLevel = 1;
        delay = 100;
        bombs = new ArrayList<Bomb>();
    }

    /**
     * Checks if the truck's position is within the attack range of the selected entity
     *
     * @param targetPos the truck position being checked
     * @return          <code>true</code> if truck within range of fortress
     *                  <code>false</code> otherwise
     */
    public boolean withinRange(Vector2 targetPos) {
        return targetPos.dst(position) <= attackRange;
    }

    /**
     * Generates bombs to attack the FireTruck with
     * @param target        FireTruck being attacked
     * @param randomTarget  whether the bomb hits every time or
     *                      there is a chance it misses
     * @param attackSound The sound to be played when an attack is made
     */
    public void attack(FireTruck target, boolean randomTarget, Sound attackSound) {
        if (timeOfLastAttack + delay < System.currentTimeMillis()) {
            this.bombs.add(new Bomb(this, target, randomTarget));
            attackSound.play();
            timeOfLastAttack = System.currentTimeMillis();
        }
    }

    /**
     * Updates the position of all the bombs and checks whether
     * they have hit their target. If they have, it should deal
     * damage to the truck, remove the bomb and shake the screen
     * @return  <code>true</code> if bomb hits a truck
     *          <code>false</code> if bomb does nt hit a true
     */
    public boolean updateBombs() {
        boolean hasHit = false;
        ArrayList<Bomb> bombsToRemove = new ArrayList<>();
        //Checks if any bombs have hit their target and removes them if they have
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            bomb.updatePosition();
            if (bomb.checkHit()) {
                bomb.damageTruck();
                bombsToRemove.add(bomb);
                hasHit = true;
            }

            else if (bomb.hasReachedTargetTile()) {
                bombsToRemove.add(bomb);
            }
        }
        bombs.removeAll(bombsToRemove);
        return hasHit;
    }


    public Vector2 getPosition(){ return position; }

    public float getAttackPower() { return attackPower; }

    public int getAttackLevel() { return attackLevel; }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public void setAttackLevel(int attackLevel) {
        this.attackLevel = attackLevel;
    }

    /**
     * Set's the delay of the attack handler to 1500 and its attack power to 0.8
     */
    public void setCrazy(){
        delay = 1500;
        attackPower = 0.8f;

    }

    public void setPosition(Vector2 position) { this.position = position;}
}
