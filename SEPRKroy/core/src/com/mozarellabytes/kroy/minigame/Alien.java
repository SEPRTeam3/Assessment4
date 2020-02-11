package com.mozarellabytes.kroy.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * #Assessment3
 *
 * Alien class for the minigame. Spawns at the top of the screen and moves downwards to the bottom.
 */
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

    /**
     * Updates the alien's position, moving it downwards.
     * @param delta The amount of time since the last update() was called.
     */
    public void moveDown(float delta) {
        this.updatePos(
                0,
                -150*delta
        );

        this.updateRect();
    }
}
