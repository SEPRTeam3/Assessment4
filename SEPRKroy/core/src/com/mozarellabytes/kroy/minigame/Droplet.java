package com.mozarellabytes.kroy.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Droplet extends Entity{

    public Droplet(float x, float y) {
        super(
                new Texture(Gdx.files.internal("sprites/droplet/droplet.png")),
                x,
                y,
                32,
                32
        );
    }

    public void moveUp(float delta) {
        this.updatePos(
                0,
                200*delta
        );

        this.updateRect();
    }
}
