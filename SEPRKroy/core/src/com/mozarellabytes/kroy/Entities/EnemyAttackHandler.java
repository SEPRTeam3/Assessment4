package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.Utilities.SoundFX;

import java.util.ArrayList;

public class EnemyAttackHandler {

    private float attackRange;
    private float attackPower;
    private int attackLevel;
    private long delay;
    private Vector2 position;
    private ArrayList<Bomb> bombs;

    EnemyAttackHandler(Fortress entity){
        position = entity.getPosition();
        attackRange = entity.getFortressType().getRange();
        attackPower = entity.getFortressType().getAP();
        attackLevel = entity.getLevel();
        delay = entity.getFortressType().getDelay();
        bombs = new ArrayList<Bomb>();
    }

    EnemyAttackHandler(Alien entity){
        position = entity.getPosition();
        attackRange = 3;
        attackPower = 2;
        attackLevel = 1;
        delay = 500;
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
     */
    public void attack(FireTruck target, boolean randomTarget) {
        if (target.getTimeOfLastAttack() + delay < System.currentTimeMillis()) {
            this.bombs.add(new Bomb(this, target, randomTarget));
            target.setTimeOfLastAttack(System.currentTimeMillis());
            if (SoundFX.music_enabled) {
                SoundFX.sfx_fortress_attack.play();
            }
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
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            bomb.updatePosition();
            if (bomb.checkHit()) {
                bomb.damageTruck();
                this.removeBomb(bomb);
                return true;
            } else if (bomb.hasReachedTargetTile()) {
                this.removeBomb(bomb);
            }
        }
        return false;
    }

    /**
     * Removes Bomb from bomb list. This
     * occurs when the bomb hits or misses
     *
     * @param bomb bomb being removed
     */
    private void removeBomb(Bomb bomb) {
        bombs.remove(bomb);
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

    public void setPosition(Vector2 position) { this.position = position;}
}
