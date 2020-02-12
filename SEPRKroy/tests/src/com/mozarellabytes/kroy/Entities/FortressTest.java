package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.GdxTestRunner;
import com.mozarellabytes.kroy.Screens.GameScreen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static com.mozarellabytes.kroy.Entities.FortressType.*;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class FortressTest {

    @Mock
    GameScreen gameScreenMock;

    @Test
    public void differentRangeTest() {
        assertTrue(Clifford.getRange() != Revs.getRange() && Revs.getRange() != Walmgate.getRange());
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
    public void differentAPTest() {
        assertTrue(Clifford.getAP() != Revs.getAP() && Revs.getAP() != Walmgate.getAP());
    }

    @Test
    public void attackTruckFromWalmgateFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);

        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        handler.attack(fireTruck, false);
        handler.updateBombs();
        assertEquals(144, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromCliffordFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Clifford);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);

        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        handler.attack(fireTruck, false);
        handler.updateBombs();
        assertEquals(142.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromRevolutionFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Revs);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);

        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        handler.attack(fireTruck, false);
        handler.updateBombs();
        assertEquals(146.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromWalmgateFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate); // range = 8
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(17, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromWalmgateFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate); // range = 8
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(18, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromWalmgateFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate); // range = 8
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(19, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Clifford); // range = 4
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(13, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Clifford); // range = 4
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(14, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Clifford); // range = 4
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(15, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Revs); // range = 7
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(16, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Revs); // range = 7
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(17, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Revs); // range = 7
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(18, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    /*
     * #Assesment3 Tests from here onwards
     */

    @Test
    public void differentRangeTest2() {
        assertTrue(
                Clifford.getRange() != Revs.getRange() &&
                         Revs.getRange() != Walmgate.getRange() &&
                         Walmgate.getRange() != TrainStation.getRange() &&
                         TrainStation.getRange() != Minster.getRange() &&
                         Minster.getRange() != Shambles.getRange()
        );
    }

    @Test
    public void differentMaxHPTest2() {
        assertTrue(
                Clifford.getMaxHP() != Revs.getMaxHP() &&
                         Revs.getMaxHP() != Walmgate.getMaxHP() &&
                         Walmgate.getMaxHP() != TrainStation.getMaxHP() &&
                         TrainStation.getMaxHP() != Minster.getMaxHP() &&
                         Minster.getMaxHP() != Shambles.getMaxHP()
        );
    }

    @Test
    public void differentFireRateTest2() {
        assertTrue(
                Clifford.getDelay() != Revs.getDelay() &&
                         Revs.getDelay() != Walmgate.getDelay() &&
                         Walmgate.getDelay() != TrainStation.getDelay() &&
                         TrainStation.getDelay() != Minster.getDelay() &&
                         Minster.getDelay() != Shambles.getDelay()
        );
    }

    @Test
    public void differentAPTest2() {
        assertTrue(
                Clifford.getAP() != Revs.getAP() &&
                        Revs.getAP() != Walmgate.getAP() &&
                        Walmgate.getAP() != TrainStation.getAP() &&
                        TrainStation.getAP() != Minster.getAP() &&
                        Minster.getAP() != Shambles.getAP()
        );
    }

    @Test
    public void attackTruckFromTrainStationDamageTest() {
        Fortress fortress = new Fortress(10, 10, TrainStation);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);

        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        handler.attack(fireTruck, false);
        handler.updateBombs();
        assertEquals(145, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromMinsterDamageTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Minster);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);

        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        handler.attack(fireTruck, false);
        handler.updateBombs();
        assertEquals(143, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromShamblesDamageTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Shambles);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);

        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        handler.attack(fireTruck, false);
        handler.updateBombs();
        assertEquals(144, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromTrainStationFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.TrainStation); // range = 5
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(14, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromTrainStationFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.TrainStation); // range = 5
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(15, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromTrainStationFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.TrainStation); // range = 5
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(16, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromMinsterFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Minster); // range = 6
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(15, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromMinsterFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Minster); // range = 6
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(16, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromMinsterFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Minster); // range = 6
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(17, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromShamblesFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Shambles); // range = 9
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(18, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromShamblesFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Shambles); // range = 9
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(19, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromShamblesFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Shambles); // range = 9
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(20, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }
}