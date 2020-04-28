package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.GameState;
import com.mozarellabytes.kroy.GdxTestRunner;
import com.mozarellabytes.kroy.Screens.GameScreen;
import com.mozarellabytes.kroy.Utilities.SoundFX;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static com.mozarellabytes.kroy.Entities.FortressType.*;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class FortressTest {

    @Mock
    GameScreen gameScreenMock;

    @Mock
    GameState gameStateMock;

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
        //Assessment 4 - mocking added for GameState
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate,gameStateMock);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress, 0);

        handler.attack(fireTruck, false, SoundFX.sfx_alien_attack);
        handler.updateBombs();
        assertEquals(132.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromCliffordFortressDamageTest() {
        //Assessment 4 - mocking added for GameState
        Fortress fortress = new Fortress(10, 10, Clifford,gameStateMock);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0 );

        handler.attack(fireTruck, false, SoundFX.sfx_alien_attack);
        handler.updateBombs();
        assertEquals(126.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromRevolutionFortressDamageTest() {
        //Assessment 4 - mocking added for GameState
        Fortress fortress = new Fortress(10, 10, Revs,gameStateMock);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0 );

        handler.attack(fireTruck, false, SoundFX.sfx_alien_attack);
        handler.updateBombs();
        assertEquals(138.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromWalmgateFortressBeforeRangeBoundaryTest() {
        //Assessment 4
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate, gameStateMock); // range = 8
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(17, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromWalmgateFortressOnRangeBoundaryTest() {
        //Assessment 4
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate, gameStateMock); // range = 8
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(18, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromWalmgateFortressAfterRangeBoundaryTest() {
        //Assessment 4
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate, gameStateMock); // range = 8
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(19, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressBeforeRangeBoundaryTest() {
        //Assessment 4
        Fortress fortress = new Fortress(10, 10, FortressType.Clifford, gameStateMock); // range = 4
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(13, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressOnRangeBoundaryTest() {
        //Assessment 4
        Fortress fortress = new Fortress(10, 10, FortressType.Clifford, gameStateMock); // range = 4
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(14, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressAfterRangeBoundaryTest() {
        //Assessment 4
        Fortress fortress = new Fortress(10, 10, FortressType.Clifford, gameStateMock); // range = 4
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(15, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressBeforeRangeBoundaryTest() {
        //Assessment 4
        Fortress fortress = new Fortress(10, 10, FortressType.Revs, gameStateMock); // range = 7
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(16, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressOnRangeBoundaryTest() {
        //Assessment 4
        Fortress fortress = new Fortress(10, 10, FortressType.Revs, gameStateMock); // range = 7
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(17, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressAfterRangeBoundaryTest() {
        //Assessment 4
        Fortress fortress = new Fortress(10, 10, FortressType.Revs, gameStateMock); // range = 7
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(18, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    /*
     * #Assesment3 Tests from here onwards
     */

    @Test
    public void allFortressesDifferentRangeTest() {
        assertTrue(
                Clifford.getRange() != Revs.getRange() &&
                         Revs.getRange() != Walmgate.getRange() &&
                         Walmgate.getRange() != TrainStation.getRange() &&
                         TrainStation.getRange() != Minster.getRange() &&
                         Minster.getRange() != Shambles.getRange()
        );
    }

    @Test
    public void allFortressesDifferentMaxHPTest() {
        assertTrue(
                Clifford.getMaxHP() != Revs.getMaxHP() &&
                         Revs.getMaxHP() != Walmgate.getMaxHP() &&
                         Walmgate.getMaxHP() != TrainStation.getMaxHP() &&
                         TrainStation.getMaxHP() != Minster.getMaxHP() &&
                         Minster.getMaxHP() != Shambles.getMaxHP()
        );
    }

    @Test
    public void allFortressesDifferentFireRateTest() {
        assertTrue(
                Clifford.getDelay() != Revs.getDelay() &&
                         Revs.getDelay() != Walmgate.getDelay() &&
                         Walmgate.getDelay() != TrainStation.getDelay() &&
                         TrainStation.getDelay() != Minster.getDelay() &&
                         Minster.getDelay() != Shambles.getDelay()
        );
    }

    @Test
    public void allFortressesDifferentAPTest() {
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
        //Assessment 4
        Fortress fortress = new Fortress(10, 10, TrainStation, gameStateMock);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);

//        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        handler.attack(fireTruck, false, SoundFX.sfx_alien_attack);
        handler.updateBombs();
        assertEquals(135, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromMinsterDamageTest() {
        Fortress fortress = new Fortress(10, 10, Minster, gameStateMock);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);

//        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        handler.attack(fireTruck, false, SoundFX.sfx_alien_attack);
        handler.updateBombs();
        assertEquals(129, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromShamblesDamageTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Shambles, gameStateMock);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);

        handler.attack(fireTruck, false, SoundFX.sfx_alien_attack);
        handler.updateBombs();
        assertEquals(132, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromTrainStationFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.TrainStation, gameStateMock); // range = 5
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(14, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromTrainStationFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.TrainStation, gameStateMock); // range = 5
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(15, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromTrainStationFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.TrainStation, gameStateMock); // range = 5
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(16, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromMinsterFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Minster, gameStateMock); // range = 6
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(15, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromMinsterFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Minster, gameStateMock); // range = 6
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(16, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromMinsterFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Minster, gameStateMock); // range = 6
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(17, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromShamblesFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Shambles, gameStateMock); // range = 9
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(18, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromShamblesFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Shambles, gameStateMock); // range = 9
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(19, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromShamblesFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Shambles,gameStateMock); // range = 9
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(20, 10), FireTruckType.Speed);
        EnemyAttackHandler handler = new EnemyAttackHandler(fortress,0);
        boolean withinRange = handler.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }
}