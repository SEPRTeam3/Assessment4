package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.mozarellabytes.kroy.GameState;
import com.mozarellabytes.kroy.GdxTestRunner;
import com.mozarellabytes.kroy.Screens.GameScreen;
import com.mozarellabytes.kroy.Utilities.PathFinder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@RunWith(GdxTestRunner.class)
public class AlienTest {

    @Mock
    GameScreen gameScreenMock = mock(GameScreen.class);

    @Test
    public void alienMoveRightTest() {

        when(gameScreenMock.isRoad(anyInt(), anyInt())).thenReturn(true);
        List<Vector2> vectorList = new ArrayList<>();
        vectorList.add(new Vector2(4, 1));  //Waypoint to move towards
        GameState gameState = new GameState();
        Fortress masterFortress = new Fortress(0f, 0f, FortressType.Clifford, gameState);

        Alien alien = new Alien(1f, 1f, vectorList, 1f, masterFortress, new PathFinder(gameScreenMock), gameState);
        alien.move(1f, new ArrayList<FireTruck>());

        assertEquals(new Vector2(2, 1), alien.getPosition());
    }

    @Test
    public void alienMoveLeftTest() {
        when(gameScreenMock.isRoad(anyInt(), anyInt())).thenReturn(true);
        List<Vector2> vectorList = new ArrayList<>();
        vectorList.add(new Vector2(0, 1));     //Waypoint to move towards
        GameState gameState = new GameState();
        Fortress masterFortress = new Fortress(0f, 0f, FortressType.Clifford, gameState);

        Alien alien = new Alien(1f, 1f, vectorList, 1f, masterFortress, new PathFinder(gameScreenMock), gameState);
        alien.move(1f, new ArrayList<FireTruck>());

        assertEquals(new Vector2(0, 1), alien.getPosition());
    }

    @Test
    public void alienMoveUpTest() {
        when(gameScreenMock.isRoad(anyInt(), anyInt())).thenReturn(true);
        List<Vector2> vectorList = new ArrayList<>();
        vectorList.add(new Vector2(1, 10));     //Waypoint to move towards
        GameState gameState = new GameState();
        Fortress masterFortress = new Fortress(0f, 0f, FortressType.Clifford, gameState);

        Alien alien = new Alien(1f, 1f, vectorList, 1f, masterFortress, new PathFinder(gameScreenMock), gameState);
        alien.move(1f, new ArrayList<FireTruck>());

        assertEquals(new Vector2(1, 2), alien.getPosition());
    }


    @Test
    public void alienMoveDownTest() {
        when(gameScreenMock.isRoad(anyInt(), anyInt())).thenReturn(true);
        List<Vector2> vectorList = new ArrayList<>();
        vectorList.add(new Vector2(1, 0));     //Waypoint to move towards
        GameState gameState = new GameState();
        Fortress masterFortress = new Fortress(0f, 0f, FortressType.Clifford, gameState);

        Alien alien = new Alien(1f, 1f, vectorList, 1f, masterFortress, new PathFinder(gameScreenMock), gameState);
        alien.move(1f, new ArrayList<FireTruck>());

        assertEquals(new Vector2(1, 0), alien.getPosition());
    }

    @Test
    public void alienShouldCollideTest() {
        when(gameScreenMock.isRoad(2, 1)).thenReturn(false);
        List<Vector2> vectorList = new ArrayList<>();
        vectorList.add(new Vector2(3, 1));     //Waypoint to move towards
        GameState gameState = new GameState();
        Fortress masterFortress = new Fortress(0f, 0f, FortressType.Clifford, gameState);

        Alien alien = new Alien(1f, 1f, vectorList, 1f, masterFortress, new PathFinder(gameScreenMock), gameState);
        alien.move(1f, new ArrayList<FireTruck>());

        assertNotEquals(new Vector2(2, 1), alien.getPosition());
    }
    /**
     * Helper function that returns true if an inte
     */

}
