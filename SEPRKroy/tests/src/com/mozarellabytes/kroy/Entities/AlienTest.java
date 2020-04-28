/*
package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.mozarellabytes.kroy.GameState;
import com.mozarellabytes.kroy.GdxTestRunner;
import com.mozarellabytes.kroy.Screens.GameScreen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static com.mozarellabytes.kroy.Entities.FireTruckType.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

@RunWith(GdxTestRunner.class)
public class AlienTest {

    @Mock
    GameScreen gameScreenMock;

    @Mock
    GameState gameStateMock;

    @Test
    public void alienMoveRightTest() {
        Queue<Vector2> vectorQueue = new Queue<>();
        vectorQueue.addFirst(new Vector2(1, 0));

        ArrayList<FireTruck> truckArrayList = new ArrayList<>();

        Alien alien = new Alien(0, 0, gameStateMock);

        alien.move(truckArrayList);

        assertEquals(new Vector2(1, 0), alien.getPosition());
    }

    @Test
    public void alienMoveLeftTest() {
        Queue<Vector2> vectorQueue = new Queue<>();
        vectorQueue.addFirst(new Vector2(-1, 0));

        ArrayList<FireTruck> truckArrayList = new ArrayList<>();

        Alien alien = new Alien(0, 0, vectorQueue, 1);

        alien.move(truckArrayList);

        assertEquals(new Vector2(-1, 0), alien.getPosition());
    }

    @Test
    public void alienMoveUpTest() {
        Queue<Vector2> vectorQueue = new Queue<>();
        vectorQueue.addFirst(new Vector2(0, 1));

        ArrayList<FireTruck> truckArrayList = new ArrayList<>();

        Alien alien = new Alien(0, 0, vectorQueue, 1);

        alien.move(truckArrayList);

        assertEquals(new Vector2(0, 1), alien.getPosition());
    }

    @Test
    public void alienMoveDownTest() {
        Queue<Vector2> vectorQueue = new Queue<>();
        vectorQueue.addFirst(new Vector2(0, -1));

        ArrayList<FireTruck> truckArrayList = new ArrayList<>();

        Alien alien = new Alien(0, 0, vectorQueue, 1);

        alien.move(truckArrayList);

        assertEquals(new Vector2(0, -1), alien.getPosition());
    }

    @Test
    public void alienShouldCollideTest() {
        ArrayList<FireTruck> truckArrayList = new ArrayList<>();
        truckArrayList.add(new FireTruck(gameScreenMock, new Vector2(5, 5), Speed));

        Queue<Vector2> vectorQueue = new Queue<>();
        vectorQueue.addFirst(new Vector2(5, 5));

        Alien alien = new Alien(4, 5, vectorQueue , 1);

        alien.move(truckArrayList);

        assertEquals(new Vector2(4, 5), alien.getPosition());
    }

    @Test
    public void alienShouldNotCollideTest() {
        ArrayList<FireTruck> truckArrayList = new ArrayList<>();
        truckArrayList.add(new FireTruck(gameScreenMock, new Vector2(5, 5), Speed));

        Queue<Vector2> vectorQueue = new Queue<>();
        vectorQueue.addFirst(new Vector2(4, 5));

        Alien alien = new Alien(3, 5, vectorQueue , 1);

        alien.move(truckArrayList);

        assertEquals(new Vector2(4, 5), alien.getPosition());
    }
}
*/
