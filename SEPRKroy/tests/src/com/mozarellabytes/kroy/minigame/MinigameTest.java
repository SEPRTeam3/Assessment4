package com.mozarellabytes.kroy.minigame;


import com.mozarellabytes.kroy.GdxTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class MinigameTest {

    @Test
    public void EntityPositionEqualsRectanglePosition() {
        MinigameTruck minigameTruck = new MinigameTruck(0, 0);

        assertEquals(minigameTruck.getX(), minigameTruck.getRect().x, 0.0);
        assertEquals(minigameTruck.getY(), minigameTruck.getRect().y, 0.0);
    }

    @Test
    public void FireTruckPositionMovedToRight() {
        MinigameTruck minigameTruck = new MinigameTruck(0, 0);

        minigameTruck.moveRight(0.01f);
        assertEquals(minigameTruck.getX(), 5, 0.0);
    }

    @Test
    public void FireTruckPositionMovedToLeft() {
        MinigameTruck minigameTruck = new MinigameTruck(0, 0);

        minigameTruck.moveLeft(0.01f);
        assertEquals(minigameTruck.getX(), -5, 0.0);
    }

    @Test
    public void AlienPositionMovedDown() {
        Alien alien = new Alien(0,0);

        alien.moveDown(0.01f);
        assertEquals(alien.getY(), -1.5, 0.0);
    }

    @Test
    public void DropPositionMovedUp() {
        Droplet droplet = new Droplet(0, 0);

        droplet.moveUp(0.01f);
        assertEquals(droplet.getY(), 3, 0.0);
    }
}
