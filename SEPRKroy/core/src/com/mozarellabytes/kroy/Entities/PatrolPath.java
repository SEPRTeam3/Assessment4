package com.mozarellabytes.kroy.Entities;
// #Assesment3

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

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
    public final Queue<Vector2> path1;

    public PatrolPath() {
        this.path1 = new Queue<Vector2>();
        this.path1.addFirst(new Vector2(13,5));
        this.path1.addLast(new Vector2(13, 4));
        this.path1.addLast(new Vector2(13, 3));
        this.path1.addLast(new Vector2(13, 2));
        this.path1.addLast(new Vector2(13, 1));
        this.path1.addLast(new Vector2(14, 1));
        this.path1.addLast(new Vector2(15, 4));
        this.path1.addLast(new Vector2(16, 4));
        this.path1.addLast(new Vector2(17, 4));
    }

    public void addToPatrolPath1(Vector2 coordinate) {
        this.path1.addLast(coordinate);
    }

    public Vector2 getPath1First() { return this.path1.first(); }


    /**
     * saves the first element of the path into a temporary vector
     * this is then appended to the end of the path
     * the original first item is returned and removed
     */
    public Vector2 getFirstAndAppend(){
        Vector2 tempVector = new Vector2(this.path1.first());
        this.path1.addLast(tempVector);
        this.path1.removeFirst();
        return tempVector;
    }
}
