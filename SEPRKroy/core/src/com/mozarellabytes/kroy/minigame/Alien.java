package com.mozarellabytes.kroy.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Alien extends Entity {
    public Alien(float x, float y) {
        super(
                new Texture(Gdx.files.internal("sprites/alien/alien.png")),
                x,
                y,
                64,
                64
        );
    }

    public void moveDown(float delta) {
        this.updatePos(
                0,
                -150*delta
        );

        this.updateRect();
    }
}
