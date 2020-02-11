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

    /**
     * The entity abstract can be used to represent all objects in the minigame.
     * @param texture
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Entity(Texture texture, float x, float y, int width, int height) {
        this.texture = texture;
        this.pos = new Vector2(x, y);
        this.width = width;
        this.height = height;

        /*
        Create a rectangle with the same width/height/position as the entity.
        The rectangle is used for easy collision detection between the droplets
        and the aliens as they meet each other in the air.
        */
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

    /*
    This function is called every time an entity moves in a direction.
    It updates the position of the corresponding rectangle to the entities position, so
    collisions are still accurate.
     */
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
