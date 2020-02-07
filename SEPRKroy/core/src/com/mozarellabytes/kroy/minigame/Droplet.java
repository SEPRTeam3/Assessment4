package com.mozarellabytes.kroy.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * #Assessment3
 *
 * Droplet class, fired by the firetruck. Spawns at roughly the  coordinates of the firetruck
 */
public class Droplet extends Entity{

    public Droplet(float x, float y) {
        super(
                new Texture(Gdx.files.internal("sprites/droplet/droplet180.png")),
                x,
                y,
                32,
                32
        );
    }

    public void moveUp(float delta) {
        this.updatePos(
                0,
                300*delta
        );

        this.updateRect();
    }
}
