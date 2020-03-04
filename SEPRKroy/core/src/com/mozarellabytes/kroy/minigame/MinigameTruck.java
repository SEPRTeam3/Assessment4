package com.mozarellabytes.kroy.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.Entities.FireTruckType;
import com.mozarellabytes.kroy.Entities.WaterParticle;
import com.mozarellabytes.kroy.Screens.GameScreen;
import com.mozarellabytes.kroy.Utilities.Constants;

public class MinigameTruck extends Entity {
    /** Health points */
    private float HP;

    /** Water Reserve */
    private float reserve;

    private FireTruckType type;

    Texture truckLeft = new Texture(Gdx.files.internal("sprites/firetruck/left.png"));
    Texture truckRight = new Texture(Gdx.files.internal("sprites/firetruck/right.png"));
    Texture truckUp = new Texture(Gdx.files.internal("sprites/firetruck/up.png"));

    public MinigameTruck(float x, float y, float HP, float reserve, FireTruckType type) {
        super(
                new Texture(Gdx.files.internal("sprites/firetruck/left.png")),
                x,
                y,
                64,
                64
        );
        this.HP=HP;
        this.reserve=reserve;
        this.type=type;
    }

    /**
     * Updates the truck's position, moving it to the left.
     * @param delta The amount of time since the last update() function was called.
     */
    public void moveLeft(float delta) {
        this.setTexture(truckLeft);

        this.updatePos(
                -500*delta,
                0
        );

        this.updateRect();
    }

    /**
     * Updates the truck's position moving it to the right.
     * @param delta The amount of time since the last update() function was called.
     */
    public void moveRight(float delta) {
        this.setTexture(truckRight);

        this.updatePos(
                500*delta,
                0
        );
        this.updateRect();
    }

    public void stay(){
        this.setTexture(truckUp);
        this.updatePos(
                0,
                0
        );
        this.updateRect();
    }

    public void drawStats(ShapeRenderer shapeMapRenderer) {
        shapeMapRenderer.rect(this.getX() + 0.2f, this.getY()+ 1.3f, 0.6f, 0.8f, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
        shapeMapRenderer.rect(this.getX() + 0.266f, this.getY() + 1.4f, 0.2f, 0.6f, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
        shapeMapRenderer.rect(this.getX() + 0.266f, this.getY() + 1.4f, 0.2f, this.getReserve() / this.type.getMaxReserve() * 0.6f, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN);
        shapeMapRenderer.rect(this.getX() + 0.533f, this.getY() + 1.4f, 0.2f, 0.6f, Color.FIREBRICK, Color.FIREBRICK, Color.FIREBRICK, Color.FIREBRICK);
        shapeMapRenderer.rect(this.getX() + 0.533f, this.getY() + 1.4f, 0.2f, this.getHP() / this.type.getMaxHP() * 0.6f, Color.RED, Color.RED, Color.RED, Color.RED);
    }
    public float getHP() {
        return this.HP;
    }

    public float getReserve() {
        return this.reserve;
    }

    public FireTruckType getType() {
        return this.type;
    }
}
