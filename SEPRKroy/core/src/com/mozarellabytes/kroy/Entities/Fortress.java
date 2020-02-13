package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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

    public void drawDestroy(Batch mapBatch, int width, int height) {
        mapBatch.draw(fireStationBoom1, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom2, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom3, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom4, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom5, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom6, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom7, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom8, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom9, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom10, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom11, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom12, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom13, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom14, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom15, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
        mapBatch.draw(fireStationBoom16, this.getPosition().x - 5, this.getPosition().y - 5, width, height);
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
