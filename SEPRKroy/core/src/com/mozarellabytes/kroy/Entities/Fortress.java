package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.TimeUtils;
import com.mozarellabytes.kroy.GameState;
import com.mozarellabytes.kroy.Utilities.SoundFX;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Fortress {

    /*** Fortress health, destroyed on zero */
    private float HP;

    /*** Position of the Fortress */
    private final Vector2 position;

    /*** Where the Fortress lies on the map */
    private final Rectangle area;

    /*** Gives Fortress certain stats */
    private final FortressType fortressType;

    private long timeSinceLevelUp; //Assessment 3

    private int level; //Assessment 3

    private EnemyAttackHandler attackHandler;

    private List<FireTruck> seenTrucks;
    private List<Alien> fortressAliens;



    private boolean seenTruckDead;

    /**
     * Constructs Fortress at certain position and
     * of a certain type
     *
     * @param x     x coordinate of Fortress (lower left point)
     * @param y     y coordinate of Fortress (lower left point)
     * @param type  Type of Fortress to give certain stats
     */
    public Fortress(float x, float y, FortressType type, GameState gameState) {
        int difficulty = gameState.getDifficulty();
        timeSinceLevelUp = TimeUtils.millis(); //#Assessment3
        level = 1; //#Assessment3
        this.fortressType = type;
        this.position = new Vector2(x, y);
        if(difficulty == 0) {
            this.HP = type.getMaxHP()*0.75f;
        } else if(difficulty == 1){
            this.HP = type.getMaxHP();
        } else {
            this.HP = type.getMaxHP()*1.25f;
        }

        this.area = new Rectangle(this.position.x - (float) this.fortressType.getW()/2, this.position.y - (float) this.fortressType.getH()/2,
                this.fortressType.getW(), this.fortressType.getH());
        attackHandler = new EnemyAttackHandler(this, difficulty);
        this.fortressAliens = new ArrayList<>(); //#Assessment4
        this.seenTrucks = new ArrayList<>();
    }

    /**
     * Draws the health bars above the Fortress
     *
     * @param shapeMapRenderer  The renderer to be drawn to
     */
    public void drawStats(ShapeRenderer shapeMapRenderer) {
        shapeMapRenderer.rect(this.getPosition().x - 0.26f, this.getPosition().y + 1.4f, 0.6f, 1.2f, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
        shapeMapRenderer.rect(this.getPosition().x - 0.13f, this.getPosition().y + 1.5f, 0.36f, 1f, Color.FIREBRICK, Color.FIREBRICK, Color.FIREBRICK, Color.FIREBRICK);
        shapeMapRenderer.rect(this.getPosition().x - 0.13f, this.getPosition().y + 1.5f, 0.36f, this.getHP() / this.fortressType.getMaxHP() * 1f, Color.RED, Color.RED, Color.RED, Color.RED);
    }

    /**
     * Draws the Fortress on the map
     *
     * @param mapBatch  the renderer in line with the map
     */
    public void draw(Batch mapBatch) {
        mapBatch.draw(this.getFortressType().getTexture(), this.getArea().x, this.getArea().y, this.getArea().width, this.getArea().height);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public float getHP() {
        return this.HP;
    }

    //TODO add test
    public int getLevel() {
        if(TimeUtils.timeSinceMillis(timeSinceLevelUp)/60000 >= 1 && level < 5) {
            level = Math.min(level + (int)(TimeUtils.timeSinceMillis(timeSinceLevelUp)/60000), 5);
            timeSinceLevelUp = TimeUtils.millis();
        }
        return level;
    }

    public void damage(float HP){
        this.HP -= HP;
    }

    public Rectangle getArea() {
        return this.area;
    }

    public FortressType getFortressType() {
        return this.fortressType;
    }

    public EnemyAttackHandler getAttackHandler() { return attackHandler; }

    //#Assessment4
    public void removeTruckFromSeen(FireTruck f) {
        this.seenTrucks.remove(f);
    }

    //#Assessment4
    public List<FireTruck> getSeenTrucks() {
        for (int i = 0; i < seenTrucks.size(); i++) {
            FireTruck deadTruck = seenTrucks.get(i);
            if (deadTruck.getHP() <= 0 ) {
                seenTrucks.remove(deadTruck);
                seenTruckDead = true;
            } else if (deadTruck.isInvisible()) {
                seenTrucks.remove(deadTruck);
                seenTruckDead = false;
            } else {
                seenTruckDead = false;
            }

        }
        return this.seenTrucks; }

    //#Assessment4
    public void addTruckToSeen(FireTruck f) {
        //Adds a firetruck to the seen list and then lets its aliens know about it.
        if (!seenTrucks.contains(f) && f.getHP() > 0) {
            this.seenTrucks.add(f);
        }
    }

    //#Assessment 4
    public void addFortressAlien(Alien a) {
        this.fortressAliens.add(a);
    }

    public boolean isSeenTruckDead() { return seenTruckDead; }
    public void setSeenTruckDead(boolean seenTruckDead) {
        this.seenTruckDead = seenTruckDead;
    }
}
