package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.GdxTestRunner;
import com.mozarellabytes.kroy.Screens.GameScreen;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static com.mozarellabytes.kroy.Entities.FortressType.Clifford;
import static com.mozarellabytes.kroy.Entities.FortressType.Revs;
import static com.mozarellabytes.kroy.Entities.FortressType.Walmgate;
import static org.junit.Assert.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;

@RunWith(GdxTestRunner.class)
public class FortressTest {

    @Mock
    GameScreen gameScreenMock;

    @Test
    public void differentRangeTest() {
        assertTrue(Clifford.getRange() != Revs.getRange() && Revs.getRange() != Walmgate.getRange());

    }

    @Test
    public void differentAPTest() {
        assertTrue(Clifford.getAP() != Revs.getAP() && Revs.getAP() != Walmgate.getAP());
    }

    @Test
    public void differentMaxHPTest() {
        assertTrue(Clifford.getMaxHP() != Revs.getMaxHP() && Revs.getMaxHP() != Walmgate.getMaxHP());
    }

    @Test
    public void differentFireRateTest() {
        assertTrue(Clifford.getDelay() != Revs.getDelay() && Revs.getDelay() != Walmgate.getDelay());
    }

    @Test
    public void attackTruckFromWalmgateFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        new Bomb(fortress, fireTruck, true).damageTruck();
        assertEquals(135, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromCliffordFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Clifford);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 1000);
        fortress.attack(fireTruck, false);
        fortress.updateBombs();
        assertEquals(130.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromRevolutionFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Revs);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        new Bomb(fortress, fireTruck, true).damageTruck();
        assertEquals(140.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromWalmgateFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate); // range = 7
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(16, 10), FireTruckType.Speed);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromWalmgateFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate); // range = 7
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(17, 10), FireTruckType.Speed);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromWalmgateFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate); // range = 7
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(18, 10), FireTruckType.Speed);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Clifford); // range = 4
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(13, 10), FireTruckType.Speed);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Clifford); // range = 4
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(14, 10), FireTruckType.Speed);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Clifford); // range = 4
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(15, 10), FireTruckType.Speed);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Revs); // range = 5
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(14, 10), FireTruckType.Speed);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Revs); // range = 5
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(15, 10), FireTruckType.Speed);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Revs); // range = 5
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(16, 10), FireTruckType.Speed);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressDestroyPassTest() { // TODO this is not actually testing our code
        ArrayList<Fortress> fortresses = new ArrayList<Fortress>();
        fortresses.add(new Fortress(10, 10, FortressType.Clifford));
        fortresses.get(0).damage(150);
        if (fortresses.get(0).getHP() <= 0) {
            fortresses.remove(fortresses.get(0));
        }
        assertEquals(new ArrayList<Fortress>(), fortresses);
    }

    @Test
    public void attackTruckFromCliffordFortressDestroyFailTest() { // TODO this is not actually testing our code
        ArrayList<Fortress> fortresses = new ArrayList<Fortress>();
        fortresses.add(new Fortress(10, 10, FortressType.Clifford));
        fortresses.get(0).damage(100);
        if (fortresses.get(0).getHP() <= 0) {
            fortresses.remove(fortresses.get(0));
        }
        assertNotEquals(new ArrayList<Fortress>(), fortresses);
    }

}