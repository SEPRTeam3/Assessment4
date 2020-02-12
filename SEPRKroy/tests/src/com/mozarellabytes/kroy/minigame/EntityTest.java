package com.mozarellabytes.kroy.minigame;


import com.mozarellabytes.kroy.GdxTestRunner;
import com.mozarellabytes.kroy.Screens.MinigameScreen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.mockito.Mock;

@RunWith(GdxTestRunner.class)
public class EntityTest {

    @Test
    public void EntityPositionEqualsRectanglePosition() {
        FireTruck fireTruck = new FireTruck(0, 0);

        Assert.assertEquals(fireTruck.getX(), fireTruck.getRect().x, 0.0);
        Assert.assertEquals(fireTruck.getY(), fireTruck.getRect().y, 0.0);
    }

    @Test
    public void FireTruckPositionMovedToRight() {
        FireTruck fireTruck = new FireTruck(0, 0);

        fireTruck.moveRight(0.01f);
        Assert.assertEquals(fireTruck.getX(), -5, 0.0);
    }

    @Test
    public void FireTruckPositionMovedToLeft() {
        FireTruck fireTruck = new FireTruck(0, 0);

        fireTruck.moveRight(0.01f);
        Assert.assertEquals(fireTruck.getX(), 5, 0.0);
    }

    @Test
    public void AlienPositionMovedDown() {
        Alien alien = new Alien(0,0);

        alien.moveDown(0.01f);
        Assert.assertEquals(alien.getY(), -1.5, 0.0);
    }

    @Test
    public void DropPositionMovedUp() {
        Droplet droplet = new Droplet(0, 0);

        droplet.moveUp(0.01f);
        Assert.assertEquals(droplet.getY(), 3, 0.0);
    }
}
