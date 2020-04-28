package com.mozarellabytes.kroy.minigame;


import com.mozarellabytes.kroy.Entities.FireTruckType;
import com.mozarellabytes.kroy.GdxTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class MinigameTest {

    @Test
    public void EntityPositionEqualsRectanglePosition() {
        //assessment 4 change - new variables were added to this signature
        MinigameTruck minigameTruck = new MinigameTruck(0, 0, 10,10, FireTruckType.Attack);

        assertEquals(minigameTruck.getX(), minigameTruck.getRect().x, 0.0);
        assertEquals(minigameTruck.getY(), minigameTruck.getRect().y, 0.0);
    }

    @Test
    public void FireTruckPositionMovedToRight() {
        //assessment 4 change - new variables were added to this signature
        MinigameTruck minigameTruck = new MinigameTruck(0, 0, 10,10, FireTruckType.Attack);
        minigameTruck.moveRight(0.01f);
        assertEquals(minigameTruck.getX(), 5, 0.0);
    }

    @Test
    public void FireTruckPositionMovedToLeft() {
        //assessment 4 change - new variables were added to this signature
        MinigameTruck minigameTruck = new MinigameTruck(0, 0, 10,10, FireTruckType.Attack);

        minigameTruck.moveLeft(0.01f);
        assertEquals(minigameTruck.getX(), -5, 0.0);
    }

    @Test
    public void AlienPositionMovedDown() {
        Alien alien = new Alien(0,0);

        alien.moveDown(0.01f);
        assertEquals(alien.getY(), -0.9, 0.1);
    }

    @Test
    public void DropPositionMovedUp() {
        Droplet droplet = new Droplet(0, 0);

        droplet.moveUp(0.01f);
        assertEquals(droplet.getY(), 3, 0.0);
    }
}
