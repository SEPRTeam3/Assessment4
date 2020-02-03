package com.mozarellabytes.kroy.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mozarellabytes.kroy.Utilities.Constants;

public class FireTruck {
    /** FireTruck textures */
    private Texture truckImage;
    private Texture truckLeft;
    private Texture truckRight;

    /** FireTruck sprite */
    private Sprite sprite;

    public FireTruck() {
    }

    public void create() {
        truckLeft = new Texture(Gdx.files.internal("sprites/firetruck/left.png"));
        truckRight = new Texture(Gdx.files.internal("sprites/firetruck/right.png"));

        truckImage = truckLeft;
        this.sprite = new Sprite(truckImage);
        this.sprite.setPosition(
                Constants.GAME_WIDTH / 2,
                Constants.GAME_HEIGHT / 2
        );
    }

    public Sprite getSprite() {
        return this.sprite;
    }


}
