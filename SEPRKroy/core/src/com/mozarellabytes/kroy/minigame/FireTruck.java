package com.mozarellabytes.kroy.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.Utilities.Constants;

public class FireTruck extends Entity {

    Texture truckLeft = new Texture(Gdx.files.internal("sprites/firetruck/left.png"));
    Texture truckRight = new Texture(Gdx.files.internal("sprites/firetruck/right.png"));

    public FireTruck(float x, float y) {
        super(
                new Texture(Gdx.files.internal("sprites/firetruck/left.png")),
                x,
                y,
                64,
                64
        );
    }

    public void moveLeft(float delta) {
        this.setTexture(truckLeft);

        this.updatePos(
                -400*delta,
                0
        );

        this.updateRect();
    }

    public void moveRight(float delta) {
        this.setTexture(truckRight);

        this.updatePos(
                400*delta,
                0
        );

        this.updateRect();
    }
}
