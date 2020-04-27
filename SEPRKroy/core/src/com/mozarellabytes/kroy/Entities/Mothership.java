package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.Screens.GameScreen;
import com.mozarellabytes.kroy.Utilities.SoundFX;

/**
 * #Assessment4
 * The mothership is the ET that destroys the firestation
 * Adapted from CrazyAlien in Assessment3 code
 */
public class Mothership {
    private Texture texture;
    private Vector2 position;
    private float phaseLength;
    private float period;
    private Vector2 from;
    private Vector2 to;
    private GameScreen game;
    private boolean visible;

    /***
     * Create the mothership ET that turns up to destroy the firestation
     * @param game
     */
    public Mothership(GameScreen game) {
       this.game = game;
       this.visible = false;
       this.texture = new Texture(Gdx.files.internal("sprites/alien/crazyAlien.png"));
    }

    /***
     *
     * @param from
     * @param to
     * @param time
     */
    public void doStationDestruction(Vector2 from, Vector2 to, float time) {
        System.out.println("The mothership is on its way");
        this.visible = true;
        phaseLength = time;
        period = 0f;
        this.position = from.cpy();
        this.from = from.cpy();
        this.to = to.cpy();
    }

    public void draw(Batch mapBatch) {
        if (visible) { mapBatch.draw(this.texture, this.position.x, this.position.y, 4f, 4f);}
    }

    public void update(float deltaTime) {
        if (visible) {
            period += deltaTime;
            this.position = from.cpy().lerp(to, period / phaseLength);
            if (period >= phaseLength) {
                game.destroyStation();
                this.game.addExplosion(new Explosion(10, 10,  (int) this.position.x-2, (int) this.position.y-2, 0.05f));
                if (SoundFX.music_enabled) {
                    SoundFX.sfx_fortress_destroyed.play();
                }
                this.visible = false;
            }
        }
    }

}
