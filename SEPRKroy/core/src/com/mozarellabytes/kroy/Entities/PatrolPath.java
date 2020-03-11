package com.mozarellabytes.kroy.Entities;
// #Assesment3

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import java.util.Random;

/**
 * An alien is an entity that moves according to predifined patrol paths
 * These automatically attack the fire engines when in range of them
 * Conversely they can be attacked by fire engines aswell and destroyed
 */

public class PatrolPath {
    /**
     * PatrolPath class contains hard coded patrols for each alien
     * Hardcoded paths are queues of vector2's handled in the same way as for firetruck
     * Methods to decide which patrol list to return to the given alien (takes alien as input, returns path)
     * MAYBE contains methods to handle collisions with another alien and changing patrol and directions
     **/

    /** Actual path the truck follows; the fewer item in
     * the path the slower the truck will go */
    private Queue<Vector2> path;
    private Vector2 previousTile;
    private float speed;

    public PatrolPath(Queue<Vector2> vertices, float speed) {
        this.path = new Queue<Vector2>();
        this.speed = randFloat(0.05f , 0.2f);
        if(speed != 0){
            this.speed = speed;
        }
        this.speed *= 30;
        buildPathFromVertex(vertices);
    }
    public PatrolPath(Queue<Vector2> path) {
        this.path = path;
    }


    public void addToPatrolPath1(Vector2 coordinate) {
        this.path.addLast(coordinate);
    }

    public Vector2 getPath1First() { return this.path.first(); }


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
    //Optimised path making inc. different speeds incrementer can be negative
    public void incrementPathsX(float incrementer){
        if (incrementer < 0f) {
            //this.path.addLast(new Vector2(previousTile.x + incrementer, this.path.last().y));

            for (float i = (incrementer); i > -1.0f; i = i + incrementer) {
                this.path.addLast(new Vector2(this.path.last().x + incrementer, this.path.last().y));

            }
        }else {
            for (float i = incrementer; i < 1.0f; i = i + incrementer) {
                this.path.addLast(new Vector2(this.path.last().x + incrementer, this.path.last().y));
            }
        }
    }
    public void incrementPathsY(float incrementer){
        if (incrementer < 0f) {
            //this.path.addLast(new Vector2(this.path.last().x, previousTile.y + incrementer));
            for (float i = (incrementer); i > -1.0f; i = i + incrementer) {
                this.path.addLast(new Vector2(this.path.last().x, this.path.last().y + incrementer));
            }
        }else {
            for (float i = incrementer; i < 1.0f; i = i + incrementer) {

                this.path.addLast(new Vector2(this.path.last().x, this.path.last().y + incrementer));
            }
        }
    }

    //Path making from vertices

    public static float randFloat(float min , float max) {
        Random rand = new Random();
        return rand.nextFloat() * (max - min) + min;

    }

    public void buildPathFromVertex(Queue<Vector2> vertices) {
        float distance;
        this.previousTile = null;
        for (Vector2 vertex : vertices) {
            if (this.previousTile == null) {
                this.path.addFirst(vertex);
            } else {

                if (this.previousTile.y != vertex.y) {
                    //increment Y
                    if (previousTile.y > vertex.y) {
                        distance = this.previousTile.y - vertex.y;

                        for (float i = 0; i < distance; i++) {
                            this.path.addLast(new Vector2(vertex.x, (this.previousTile.y - i)));
                            incrementPathsY(-speed*Gdx.graphics.getDeltaTime());
                        }
                    } else {
                        distance = vertex.y - this.previousTile.y;
                        for (float i = 0; i < distance; i++) {
                            this.path.addLast(new Vector2(vertex.x, (this.previousTile.y + i)));
                            incrementPathsY(speed*Gdx.graphics.getDeltaTime());
                        }
                    }
                } else {

                    //increment X
                    if (previousTile.x > vertex.x) {
                        distance = this.previousTile.x - vertex.x;
                        for (float i = 0; i < distance; i++) {
                            this.path.addLast(new Vector2((previousTile.x - i), vertex.y));
                            incrementPathsX(-speed*Gdx.graphics.getDeltaTime());
                        }
                    } else {
                        distance = vertex.x - this.previousTile.x;
                        for (float i = 0; i < distance; i++) {
                            this.path.addLast(new Vector2((this.previousTile.x + i), vertex.y));
                            incrementPathsX(speed*Gdx.graphics.getDeltaTime());
                        }
                    }
                }
            }
            this.previousTile = vertex;

            //vertices.removeFirst();
        }
    }

    public Queue<Vector2> getPath(){
        return path;
    }
}
