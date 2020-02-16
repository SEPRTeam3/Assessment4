package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

//#Assessment 3

/**
 * Class that handles the explosion animation
 */
public class Explosion {
    /**
     * Set of explosion textures, packed for animation
     */
    private TextureAtlas atlas;
    /**
     * Animation which represents the bombs explosion
     */
    private Animation<TextureRegion> animation;
    /**
     * The width of the explosion
     */
    private int width;;
    /**
     * The height of the explosion
     */
    private int height;
    /**
     * The x co-ordinate of the explosion
     */
    private int x;
    /**
     * The y co-ordinate of the explosion
     */
    private int y;
    /**
     * The amount of time that has passed since the explosion was instantiated
     */
    private float elapsedTime;

    /**
     * Initialises the explosion
     * @param width A parameter of the value to which the width is to be set
     * @param height A parameter of the value to which the height is to be set
     * @param x A parameter of the value to which the x position is to be set
     * @param y A parameter of the value to which the y position is to be set
     * @param frameDuration A parameter of the value to which stateTime is to be set
     */
    public Explosion(int width, int height, int x, int y, float frameDuration){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        atlas = new TextureAtlas(Gdx.files.internal("Atlas'/exp.atlas"));
        animation = new Animation<TextureRegion>(frameDuration,atlas.findRegions("exp"));
    }

    /**
     * Method that draws the Explosion
     * @param mapBatch The batch that will be used to draw the explosion
     * @return True if all frames of the animation have reached their maximum duration
     */
    public boolean drawExplosion(Batch mapBatch){
        // Accumulate amount of time that has passed
        elapsedTime += Gdx.graphics.getDeltaTime();

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = animation.getKeyFrame(elapsedTime, false);
        mapBatch.draw(currentFrame, x, y, width, height);
        if(elapsedTime > animation.getAnimationDuration()){
            return true;
        }
        return false;
    }

    public void dispose(){
        atlas.dispose();
    }
}
