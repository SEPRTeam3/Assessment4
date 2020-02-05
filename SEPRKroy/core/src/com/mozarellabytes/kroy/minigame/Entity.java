package com.mozarellabytes.kroy.minigame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.w3c.dom.css.Rect;

public abstract class Entity {
    private Texture texture;
    private Vector2 pos;
    private int width;
    private int height;
    private Rectangle rectangle;

    public Entity(Texture texture, float x, float y, int width, int height) {
        this.texture = texture;
        this.pos = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.rectangle = new Rectangle(this.pos.x, this.pos.y, this.width, this.height);
    }

    public void setPos(float x, float y) {
        this.pos = new Vector2(x, y);
    }

    public void updatePos(float x, float y) {
        this.setPos(this.pos.x + x, this.pos.y + y);
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Rectangle getRect() {
        return this.rectangle;
    }

    public void updateRect() {
        this.rectangle.x = this.pos.x;
        this.rectangle.y = this.pos.y;
    }

    public float getX(){
        return this.pos.x;
    }

    public float getY(){
        return this.pos.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
