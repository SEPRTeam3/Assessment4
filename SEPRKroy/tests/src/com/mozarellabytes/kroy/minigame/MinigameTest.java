package com.mozarellabytes.kroy.minigame;


import com.mozarellabytes.kroy.GdxTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class MinigameTest {

    @Test
    public void EntityPositionEqualsRectanglePosition() {
        FireTruck fireTruck = new FireTruck(0, 0);

        assertEquals(fireTruck.getX(), fireTruck.getRect().x, 0.0);
        assertEquals(fireTruck.getY(), fireTruck.getRect().y, 0.0);
    }

    @Test
    public void FireTruckPositionMovedToRight() {
        FireTruck fireTruck = new FireTruck(0, 0);

        fireTruck.moveRight(0.01f);
        assertEquals(fireTruck.getX(), 5, 0.0);
    }

    @Test
    public void FireTruckPositionMovedToLeft() {
        FireTruck fireTruck = new FireTruck(0, 0);

        fireTruck.moveLeft(0.01f);
        assertEquals(fireTruck.getX(), -5, 0.0);
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
