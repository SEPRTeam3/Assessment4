package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.TimeUtils;
import com.mozarellabytes.kroy.Utilities.SoundFX;

import java.sql.Time;
import java.util.ArrayList;

public class Fortress {

    /*** Fortress health, destroyed on zero */
    private float HP;

    /*** Position of the Fortress */
    private final Vector2 position;

    /*** Where the Fortress lies on the map */
    private final Rectangle area;

    /*** Gives Fortress certain stats */
    private final FortressType fortressType;

    private long timeSinceLevelUp;//Assessment 3

    private int level;//Assessment 3

    private EnemyAttackHandler attackHandler;



    /**
     * Constructs Fortress at certain position and
     * of a certain type
     *
     * @param x     x coordinate of Fortress (lower left point)
     * @param y     y coordinate of Fortress (lower left point)
     * @param type  Type of Fortress to give certain stats
     */
    public Fortress(float x, float y, FortressType type) {
        timeSinceLevelUp = TimeUtils.millis(); //Assessment 3
        level = 1;//Assessment 3
        this.fortressType = type;
        this.position = new Vector2(x, y);
        this.HP = type.getMaxHP();
        this.area = new Rectangle(this.position.x - (float) this.fortressType.getW()/2, this.position.y - (float) this.fortressType.getH()/2,
                this.fortressType.getW(), this.fortressType.getH());
        attackHandler = new EnemyAttackHandler(this);
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

    public int getLevel() {
        if(TimeUtils.timeSinceMillis(timeSinceLevelUp)/5000 >= 1 && level < 5) {
            level = Math.min(level + (int)TimeUtils.timeSinceMillis(timeSinceLevelUp)/5000, 5);
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

}
