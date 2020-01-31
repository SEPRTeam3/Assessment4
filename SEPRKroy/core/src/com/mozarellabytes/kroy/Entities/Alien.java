package com.mozarellabytes.kroy.Entities;

// #Assesment3
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import java.util.ArrayList;

public class Alien extends Sprite {

    /**
     *Alien is a class for the alien patrols
     * Contains texture of alien
     * contains attack and been attacked methods
     * movement methods similar to FireTruck as they simply handle queues of vector2's
     * contains methods to choose paths from path type class
     * contains display methods and HUD information displaying methods
    **/

    /** cursory max HP value for alien */
    private final float maxHP = 10;

    /** Health points */
    private float HP;

    /** Position of Alien in tiles */
    private Vector2 position;

    /** Current PatrolPath path the alien follows*/
    public Queue<Vector2> path;

    /** List of bombs that are active */
    private final ArrayList<Bomb> bombs;

    /** Whether the truck has an unresolved collision
     * with another truck */
    private boolean inCollision;

    /** Used to check if the truck's image should be
     * changed to match the direction it is facing */
    private Vector2 previousTile;

    /** Texture for each direction the
     * alien is facing */
    private final Texture lookLeft;
    private final Texture lookRight;
    private final Texture lookUp;
    private final Texture lookDown;

    /** PatrolPath initialised for each alien
     */
    private PatrolPath mainPatrol;

    /**
     * Constructs alien at certain position
     *
     * @param x     x coordinate of alien (lower left point)
     * @param y     y coordinate of alien (lower left point)
     */
    public Alien(float x, float y,Queue<Vector2> vertices){
        super(new Texture(Gdx.files.internal("sprites/alien/AlienDown.png")));

        this.mainPatrol = new PatrolPath(vertices);
        this.position = new Vector2(x,y);
        this.HP = maxHP;
        this.path = new Queue<Vector2>();
        //this.setPath();
        this.bombs = new ArrayList<Bomb>();
        this.lookLeft = new Texture(Gdx.files.internal("sprites/alien/AlienLeft.png"));
        this.lookRight = new Texture(Gdx.files.internal("sprites/alien/AlienRight.png"));
        this.lookUp = new Texture(Gdx.files.internal("sprites/alien/AlienUp.png"));
        this.lookDown = new Texture(Gdx.files.internal("sprites/alien/AlienDown.png"));
    }

    /**
     * Changes the direction of the truck depending on the previous tile and the next tile
     *
     * @param nextTile  first tile in the queue (next to be followed)
     */
    private void changeSprite(Vector2 nextTile) {
        if (previousTile != null) {
            if (nextTile.x > previousTile.x) {
                setTexture(lookRight);
            } else if (nextTile.x < previousTile.x) {
                setTexture(lookLeft);
            } else if (nextTile.y > previousTile.y) {
                setTexture(lookUp);
            } else if (nextTile.y < previousTile.y) {
                setTexture(lookDown);
            }
        }
    }

    /**
     * Called every tick and updates the paths to simulate the truck moving along the
     * path
     */
    public void move() {
            if (this.path.size > 0) {
                Vector2 nextTile = this.getFirstAndAppend();
                this.position = nextTile;

                if (!this.inCollision) {
                    changeSprite(nextTile);
                } // add collision case with firetruck or other alien

                previousTile = nextTile;
            }
            if (this.path.isEmpty() && inCollision) {
                inCollision = false;
            }
    }

    /**
     * Draws the mini health indicators relative to the alien
     *
     * @param shapeMapRenderer  Renderer that the stats are being drawn to (map  dependant)
     */
    public void drawStats(ShapeRenderer shapeMapRenderer) {
        shapeMapRenderer.rect(this.getPosition().x + 0.2f, this.getPosition().y + 1.3f, 0.3f, 0.8f, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
        shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, 0.6f, Color.FIREBRICK, Color.FIREBRICK, Color.FIREBRICK, Color.FIREBRICK);
        shapeMapRenderer.rect(this.getPosition().x + 0.25f, this.getPosition().y + 1.4f, 0.2f, this.getHP() / this.maxHP * 0.6f, Color.RED, Color.RED, Color.RED, Color.RED);
}

    /**
     * Draws the Alien sprite
     *
     * @param mapBatch  Batch that the Alien is being
     *                  drawn to (map dependant)
     */
    public void drawSprite(Batch mapBatch) {
        mapBatch.draw(this, this.position.x, this.position.y, 1, 1);
    }

    /**
     * Helper method that returns where the alien is visually to the player. This is used when
     * checking the range when attacking the fire trucks and getting attacked by the trucks
     *
     * @return a vector where the alien is visually
     */
    public Vector2 getVisualPosition() {
        return new Vector2(this.position.x + 0.5f, this.position.y + 0.5f);
    }

    public Vector2 getPosition() { return this.position;}

    public float getHP() {
        return this.HP;
    }

    public Queue<Vector2> getPath() {
        return this.path;
    }

    public ArrayList<Bomb> getBombs() {
        return this.bombs;
    }

    /**
     * saves the first element of the path into a temporary vector
     * this is then appended to the end of the path
     * the original first item is returned and removed
     */
    public Vector2 getFirstAndAppend(){
        Vector2 tempVector = new Vector2(this.path.first());
        this.path.addLast(tempVector);
        this.path.removeFirst();
        return tempVector;
    }


/**
    public void setPath(){
        this.path.addFirst(new Vector2(13, 5));
        this.path.addLast(new Vector2(13, 4.9f));
        this.path.addLast(new Vector2(13, 4.8f));
        this.path.addLast(new Vector2(13, 4.7f));
        this.path.addLast(new Vector2(13, 4.6f));
        this.path.addLast(new Vector2(13, 4.5f));
        this.path.addLast(new Vector2(13, 4.4f));
        this.path.addLast(new Vector2(13, 4.3f));
        this.path.addLast(new Vector2(13, 4.2f));
        this.path.addLast(new Vector2(13, 4.1f));
        this.path.addLast(new Vector2(13, 4));
        this.path.addLast(new Vector2(13, 3.9f));
        this.path.addLast(new Vector2(13, 3.8f));
        this.path.addLast(new Vector2(13, 3.7f));
        this.path.addLast(new Vector2(13, 3.6f));
        this.path.addLast(new Vector2(13, 3.5f));
        this.path.addLast(new Vector2(13, 3.4f));
        this.path.addLast(new Vector2(13, 3.3f));
        this.path.addLast(new Vector2(13, 3.2f));
        this.path.addLast(new Vector2(13, 3.1f));
        this.path.addLast(new Vector2(13, 3));
        this.path.addLast(new Vector2(13, 2.9f));
        this.path.addLast(new Vector2(13, 2.8f));
        this.path.addLast(new Vector2(13, 2.7f));
        this.path.addLast(new Vector2(13, 2.6f));
        this.path.addLast(new Vector2(13, 2.5f));
        this.path.addLast(new Vector2(13, 2.4f));
        this.path.addLast(new Vector2(13, 2.3f));
        this.path.addLast(new Vector2(13, 2.2f));
        this.path.addLast(new Vector2(13, 2.1f));
        this.path.addLast(new Vector2(13, 2));
        this.path.addLast(new Vector2(13, 1.9f));
        this.path.addLast(new Vector2(13, 1.8f));
        this.path.addLast(new Vector2(13, 1.7f));
        this.path.addLast(new Vector2(13, 1.6f));
        this.path.addLast(new Vector2(13, 1.5f));
        this.path.addLast(new Vector2(13, 1.4f));
        this.path.addLast(new Vector2(13, 1.3f));
        this.path.addLast(new Vector2(13, 1.2f));
        this.path.addLast(new Vector2(13, 1.1f));
        this.path.addLast(new Vector2(13, 1));
        this.path.addLast(new Vector2(13.1f, 1));
        this.path.addLast(new Vector2(13.2f, 1));
        this.path.addLast(new Vector2(13.3f, 1));
        this.path.addLast(new Vector2(13.4f, 1));
        this.path.addLast(new Vector2(13.5f, 1));
        this.path.addLast(new Vector2(13.6f, 1));
        this.path.addLast(new Vector2(13.7f, 1));
        this.path.addLast(new Vector2(13.8f, 1));
        this.path.addLast(new Vector2(13.9f, 1));
        this.path.addLast(new Vector2(14, 1));
        this.path.addLast(new Vector2(14.1f, 1));
        this.path.addLast(new Vector2(14.2f, 1));
        this.path.addLast(new Vector2(14.3f, 1));
        this.path.addLast(new Vector2(14.4f, 1));
        this.path.addLast(new Vector2(14.5f, 1));
        this.path.addLast(new Vector2(14.6f, 1));
        this.path.addLast(new Vector2(14.7f, 1));
        this.path.addLast(new Vector2(14.8f, 1));
        this.path.addLast(new Vector2(14.9f, 1));
        this.path.addLast(new Vector2(15, 1));
        this.path.addLast(new Vector2(15.1f, 1));
        this.path.addLast(new Vector2(15.2f, 1));
        this.path.addLast(new Vector2(15.3f, 1));
        this.path.addLast(new Vector2(15.4f, 1));
        this.path.addLast(new Vector2(15.5f, 1));
        this.path.addLast(new Vector2(15.6f, 1));
        this.path.addLast(new Vector2(15.7f, 1));
        this.path.addLast(new Vector2(15.8f, 1));
        this.path.addLast(new Vector2(15.9f, 1));
        this.path.addLast(new Vector2(16, 1));
        this.path.addLast(new Vector2(16.1f, 1));
        this.path.addLast(new Vector2(16.2f, 1));
        this.path.addLast(new Vector2(16.3f, 1));
        this.path.addLast(new Vector2(16.4f, 1));
        this.path.addLast(new Vector2(16.5f, 1));
        this.path.addLast(new Vector2(16.6f, 1));
        this.path.addLast(new Vector2(16.7f, 1));
        this.path.addLast(new Vector2(16.8f, 1));
        this.path.addLast(new Vector2(16.9f, 1));
        this.path.addLast(new Vector2(17, 1));
        this.path.addLast(new Vector2(17.1f, 1));
        this.path.addLast(new Vector2(17.2f, 1));
        this.path.addLast(new Vector2(17.3f, 1));
        this.path.addLast(new Vector2(17.4f, 1));
        this.path.addLast(new Vector2(17.5f, 1));
        this.path.addLast(new Vector2(17.6f, 1));
        this.path.addLast(new Vector2(17.7f, 1));
        this.path.addLast(new Vector2(17.8f, 1));
        this.path.addLast(new Vector2(17.9f, 1));
        this.path.addLast(new Vector2(18, 1));
        this.path.addLast(new Vector2(18, 1.1f));
        this.path.addLast(new Vector2(18, 1.2f));
        this.path.addLast(new Vector2(18, 1.3f));
        this.path.addLast(new Vector2(18, 1.4f));
        this.path.addLast(new Vector2(18, 1.5f));
        this.path.addLast(new Vector2(18, 1.6f));
        this.path.addLast(new Vector2(18, 1.7f));
        this.path.addLast(new Vector2(18, 1.8f));
        this.path.addLast(new Vector2(18, 1.9f));
        this.path.addLast(new Vector2(18, 2));
        this.path.addLast(new Vector2(18, 2.1f));
        this.path.addLast(new Vector2(18, 2.2f));
        this.path.addLast(new Vector2(18, 2.3f));
        this.path.addLast(new Vector2(18, 2.4f));
        this.path.addLast(new Vector2(18, 2.5f));
        this.path.addLast(new Vector2(18, 2.6f));
        this.path.addLast(new Vector2(18, 2.7f));
        this.path.addLast(new Vector2(18, 2.8f));
        this.path.addLast(new Vector2(18, 2.9f));
        this.path.addLast(new Vector2(18, 3));
        this.path.addLast(new Vector2(18, 3.1f));
        this.path.addLast(new Vector2(18, 3.2f));
        this.path.addLast(new Vector2(18, 3.3f));
        this.path.addLast(new Vector2(18, 3.4f));
        this.path.addLast(new Vector2(18, 3.5f));
        this.path.addLast(new Vector2(18, 3.6f));
        this.path.addLast(new Vector2(18, 3.7f));
        this.path.addLast(new Vector2(18, 3.8f));
        this.path.addLast(new Vector2(18, 3.9f));
        this.path.addLast(new Vector2(18, 4));
        this.path.addLast(new Vector2(18, 4.1f));
        this.path.addLast(new Vector2(18, 4.2f));
        this.path.addLast(new Vector2(18, 4.3f));
        this.path.addLast(new Vector2(18, 4.4f));
        this.path.addLast(new Vector2(18, 4.5f));
        this.path.addLast(new Vector2(18, 4.6f));
        this.path.addLast(new Vector2(18, 4.7f));
        this.path.addLast(new Vector2(18, 4.8f));
        this.path.addLast(new Vector2(18, 4.9f));
        this.path.addLast(new Vector2(18, 5));
        this.path.addLast(new Vector2(17.9f, 5));
        this.path.addLast(new Vector2(17.8f, 5));
        this.path.addLast(new Vector2(17.7f, 5));
        this.path.addLast(new Vector2(17.6f, 5));
        this.path.addLast(new Vector2(17.5f, 5));
        this.path.addLast(new Vector2(17.4f, 5));
        this.path.addLast(new Vector2(17.3f, 5));
        this.path.addLast(new Vector2(17.2f, 5));
        this.path.addLast(new Vector2(17.1f, 5));
        this.path.addLast(new Vector2(17, 5));
        this.path.addLast(new Vector2(16.9f, 5));
        this.path.addLast(new Vector2(16.8f, 5));
        this.path.addLast(new Vector2(16.7f, 5));
        this.path.addLast(new Vector2(16.6f, 5));
        this.path.addLast(new Vector2(16.5f, 5));
        this.path.addLast(new Vector2(16.4f, 5));
        this.path.addLast(new Vector2(16.3f, 5));
        this.path.addLast(new Vector2(16.2f, 5));
        this.path.addLast(new Vector2(16.1f, 5));
        this.path.addLast(new Vector2(16, 5));
        this.path.addLast(new Vector2(15.9f, 5));
        this.path.addLast(new Vector2(15.8f, 5));
        this.path.addLast(new Vector2(15.7f, 5));
        this.path.addLast(new Vector2(15.6f, 5));
        this.path.addLast(new Vector2(15.5f, 5));
        this.path.addLast(new Vector2(15.4f, 5));
        this.path.addLast(new Vector2(15.3f, 5));
        this.path.addLast(new Vector2(15.2f, 5));
        this.path.addLast(new Vector2(15.1f, 5));
        this.path.addLast(new Vector2(15, 5));
        this.path.addLast(new Vector2(14.9f, 5));
        this.path.addLast(new Vector2(14.8f, 5));
        this.path.addLast(new Vector2(14.7f, 5));
        this.path.addLast(new Vector2(14.6f, 5));
        this.path.addLast(new Vector2(14.5f, 5));
        this.path.addLast(new Vector2(14.4f, 5));
        this.path.addLast(new Vector2(14.3f, 5));
        this.path.addLast(new Vector2(14.2f, 5));
        this.path.addLast(new Vector2(14.1f, 5));
        this.path.addLast(new Vector2(14, 5));
        this.path.addLast(new Vector2(13.9f, 5));
        this.path.addLast(new Vector2(13.8f, 5));
        this.path.addLast(new Vector2(13.7f, 5));
        this.path.addLast(new Vector2(13.6f, 5));
        this.path.addLast(new Vector2(13.5f, 5));
        this.path.addLast(new Vector2(13.4f, 5));
        this.path.addLast(new Vector2(13.3f, 5));
        this.path.addLast(new Vector2(13.2f, 5));
        this.path.addLast(new Vector2(13.1f, 5));
    }
 */
}
