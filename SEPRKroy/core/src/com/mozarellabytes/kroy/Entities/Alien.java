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
     * truck is facing */
    private final Texture lookLeft;
    private final Texture lookRight;
    private final Texture lookUp;
    private final Texture lookDown;

    /**
     * Constructs alien at certain position
     *
     * @param x     x coordinate of alien (lower left point)
     * @param y     y coordinate of alien (lower left point)
     */
    public Alien(float x, float y){
        super(new Texture(Gdx.files.internal("sprites/alien/AlienDown.png")));

        this.HP = maxHP;
        this.path = new Queue<Vector2>();
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
                Vector2 nextTile = path.first();
                this.position = nextTile;

                if (!this.inCollision) {
                    changeSprite(nextTile);
                } // add collision case with firetruck or other alien

                previousTile = nextTile;
                path.removeFirst();

            }
            if (this.path.isEmpty() && inCollision) {
                inCollision = false;
            }
    }

    /**
     * Draws the mini health/reserve indicators relative to the truck
     *
     * @param shapeMapRenderer  Renderer that the stats are being drawn to (map  dependant)
     */
    public void drawStats(ShapeRenderer shapeMapRenderer) {
        shapeMapRenderer.rect(this.getPosition().x + 0.2f, this.getPosition().y + 1.3f, 0.6f, 0.8f, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
        shapeMapRenderer.rect(this.getPosition().x + 0.266f, this.getPosition().y + 1.4f, 0.2f, 0.6f, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
        shapeMapRenderer.rect(this.getPosition().x + 0.533f, this.getPosition().y + 1.4f, 0.2f, 0.6f, Color.FIREBRICK, Color.FIREBRICK, Color.FIREBRICK, Color.FIREBRICK);
        shapeMapRenderer.rect(this.getPosition().x + 0.533f, this.getPosition().y + 1.4f, 0.2f, this.getHP() / this.maxHP * 0.6f, Color.RED, Color.RED, Color.RED, Color.RED);

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

    public void setPath() {
        this.path = new Queue<Vector2>();
        this.path.addFirst(new Vector2(13,5));
        this.path.addLast(new Vector2(13, 4));
        this.path.addLast(new Vector2(13, 3));
        this.path.addLast(new Vector2(13, 2));
        this.path.addLast(new Vector2(13, 1));
        this.path.addLast(new Vector2(14, 1));
        this.path.addLast(new Vector2(15, 4));
        this.path.addLast(new Vector2(16, 4));
        this.path.addLast(new Vector2(17, 4));
    }

}
