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
        buildPathFromVertex(vertices);
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
        distance = 0;
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
                            incrementPathsY(-speed);
                        }
                    } else {
                        distance = vertex.y - this.previousTile.y;
                        for (float i = 0; i < distance; i++) {
                            this.path.addLast(new Vector2(vertex.x, (this.previousTile.y + i)));
                            incrementPathsY(speed);
                        }
                    }
                } else {

                    //increment X
                    if (previousTile.x > vertex.x) {
                        distance = this.previousTile.x - vertex.x;
                        for (float i = 0; i < distance; i++) {
                            this.path.addLast(new Vector2((previousTile.x - i), vertex.y));
                            incrementPathsX(-speed);
                        }
                    } else {
                        distance = vertex.x - this.previousTile.x;
                        for (float i = 0; i < distance; i++) {
                            this.path.addLast(new Vector2((this.previousTile.x + i), vertex.y));
                            incrementPathsX(speed);
                        }
                    }
                }
            }
            this.previousTile = vertex;

            //vertices.removeFirst();
        }
    }

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
    public Queue<Vector2> getPath(){
        return path;
    }
}
