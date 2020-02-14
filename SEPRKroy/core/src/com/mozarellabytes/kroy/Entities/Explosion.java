package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
    private TextureAtlas atlas;
    private Animation<TextureRegion> animation;
    private int width, height, x, y;
    private float stateTime;

    public Explosion(int width, int height, int x, int y, float frameDuration){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        atlas = new TextureAtlas(Gdx.files.internal("Atlas'/exp.atlas"));
        animation = new Animation<TextureRegion>(frameDuration,atlas.findRegions("exp"));
    }

    public boolean drawExplosion(Batch mapBatch){
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
        mapBatch.draw(currentFrame, x, y, width, height);
        if(stateTime > animation.getAnimationDuration()){
            return true;
        }
        return false;
    }

    public void dispose(){
        atlas.dispose();
    }
}
