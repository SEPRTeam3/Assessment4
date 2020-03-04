package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.GdxTestRunner;
import com.mozarellabytes.kroy.Screens.GameScreen;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static com.mozarellabytes.kroy.Entities.FireTruckType.*;
import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class MinigameTruckTest {

    @Mock
    GameScreen gameScreenMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    /**
     * Compares all the values passed in and checks that they are all unique
     * @param values
     * @return whether every value is different
     */
    public boolean allNotEqual(float ...values) {
        for (int i = 0; i < values.length-1; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[i] == values[j]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Test
    public void differentSpeedTest() {
        // Assert that the speeds of all tank types are different to each other.
        assertTrue(allNotEqual(
                Ocean.getSpeed(),
                Speed.getSpeed(),
                Tank.getSpeed(),
                Attack.getSpeed()
        ));
    }

    @Test
    public void speedTruckShouldMove3TilesIn15FramesTest() {
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10,10), Speed);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,10);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,11);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,11);
        fireTruck.setMoving(true);
        fireTruck.addTileToPath(new Vector2(10,10));
        fireTruck.addTileToPath(new Vector2(10,11));
        fireTruck.addTileToPath(new Vector2(11,11));
        for (int i=0; i<15; i++) {
            fireTruck.move();
        }
        Vector2 expectedPosition = new Vector2(11, 11);
        assertEquals(expectedPosition, fireTruck.getPosition());
    }

    @Test
    public void oceanTruckShouldNotMove3TilesIn25FramesTest() {
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10,10), Ocean);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,10);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,11);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,11);
        fireTruck.setMoving(true);
        fireTruck.addTileToPath(new Vector2(10,10));
        fireTruck.addTileToPath(new Vector2(10,11));
        fireTruck.addTileToPath(new Vector2(11,11));
        for (int i=0; i<25; i++) {
            fireTruck.move();
        }
        Vector2 expectedPosition = new Vector2(11, 11);
        assertNotEquals(expectedPosition, fireTruck.getPosition());
    }

    @Test
    public void oceanTruckShouldMove3TilesIn50FramesTest() {
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10,10), Ocean);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,10);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,11);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,11);
        fireTruck.setMoving(true);
        fireTruck.addTileToPath(new Vector2(10,10));
        fireTruck.addTileToPath(new Vector2(10,11));
        fireTruck.addTileToPath(new Vector2(11,11));
        for (int i=0; i<50; i++) {
            fireTruck.move();
        }
        Vector2 expectedPosition = new Vector2(11, 11);
        assertEquals(expectedPosition, fireTruck.getPosition());
    }

    @Test
    public void tankTruckShouldMove3TilesIn50FramesTest() {
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10,10), Tank);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,10);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,11);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,11);
        fireTruck.setMoving(true);
        fireTruck.addTileToPath(new Vector2(10,10));
        fireTruck.addTileToPath(new Vector2(10,11));
        fireTruck.addTileToPath(new Vector2(11,11));
        for (int i=0; i<50; i++) {
            fireTruck.move();
        }
        Vector2 expectedPosition = new Vector2(11, 11);
        assertEquals(expectedPosition, fireTruck.getPosition());
    }

    @Test
    public void attackTruckShouldMove3TilesIn25FramesTest() {
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10,10), Attack);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,10);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,11);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,11);
        fireTruck.setMoving(true);
        fireTruck.addTileToPath(new Vector2(10,10));
        fireTruck.addTileToPath(new Vector2(10,11));
        fireTruck.addTileToPath(new Vector2(11,11));
        for (int i=0; i<50; i++) {
            fireTruck.move();
        }
        Vector2 expectedPosition = new Vector2(11, 11);
        assertEquals(expectedPosition, fireTruck.getPosition());
    }

    @Test
    public void differentMaxVolumeTest() {
        //#Assessment3
        assertTrue(allNotEqual(
                Ocean.getMaxReserve(),
                Speed.getMaxReserve(),
                Tank.getMaxReserve(),
                Attack.getMaxReserve()
        ));
    }

    @Test
    public void differentAPTest() {
        //#Assessment3
        assertTrue(allNotEqual(
                Ocean.getAP(),
                Speed.getAP(),
                Tank.getAP(),
                Attack.getAP()
        ));
    }

    @Test
    public void checkTrucksFillToDifferentLevels() {
        FireTruck fireTruck1 = new FireTruck(gameScreenMock, new Vector2(9,10), Speed);
        FireTruck fireTruck2 = new FireTruck(gameScreenMock, new Vector2(10,10), Ocean);
        FireTruck fireTruck3 = new FireTruck(gameScreenMock, new Vector2(9,10), Attack);
        FireTruck fireTruck4 = new FireTruck(gameScreenMock, new Vector2(10,10), Tank);

        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate);
        FireStation fireStation = new FireStation(8, 10);

        fireStation.spawn(fireTruck1);
        fireStation.spawn(fireTruck2);
        fireStation.spawn(fireTruck3);
        fireStation.spawn(fireTruck4);

        fireTruck1.setAttacking(true);
        fireTruck2.setAttacking(true);
        fireTruck3.setAttacking(true);
        fireTruck4.setAttacking(true);

        for (int i=0; i<2000; i++) {
            fireTruck1.attack(fortress);
            fireTruck1.updateSpray();
            fireTruck2.attack(fortress);
            fireTruck2.updateSpray();
            fireTruck3.attack(fortress);
            fireTruck3.updateSpray();
            fireTruck4.attack(fortress);
            fireTruck4.updateSpray();
        }

        float fireTruck1ReserveEmpty = fireTruck1.getReserve();
        float fireTruck2ReserveEmpty = fireTruck2.getReserve();
        //#Assessment3
        float fireTruck3ReserveEmpty = fireTruck3.getReserve();
        float fireTruck4ReserveEmpty = fireTruck4.getReserve();

        for (int i=0; i<2000; i++) {
            fireStation.restoreTrucks();
        }

        boolean checkEmptyReservesAreSame = (fireTruck1ReserveEmpty == fireTruck2ReserveEmpty) && (fireTruck2ReserveEmpty == fireTruck3ReserveEmpty) && (fireTruck3ReserveEmpty == fireTruck4ReserveEmpty);
        boolean checkSpeedTruckIsFull = fireTruck1.getReserve() == Speed.getMaxReserve();
        boolean checkOceanTruckIsNotFull = fireTruck2.getReserve() !=  Ocean.getMaxReserve();
        boolean checkAttackTruckIsFull = fireTruck3.getReserve() == Attack.getMaxReserve();
        boolean checkTankTruckIsNotFull = fireTruck4.getReserve() != Tank.getMaxReserve();

        System.out.println(fireTruck1.getReserve());
        System.out.println(fireTruck2.getReserve());
        System.out.println(fireTruck3.getReserve());
        System.out.println(fireTruck4.getReserve());

        assertTrue(checkEmptyReservesAreSame
                && checkSpeedTruckIsFull
                && checkOceanTruckIsNotFull
                && checkAttackTruckIsFull
                && checkTankTruckIsNotFull
        );
    }

    @Test
    public void differentMaxHPTest() {
        //#Assessment3
        assertTrue(allNotEqual(
                Ocean.getMaxHP(),
                Speed.getMaxHP(),
                Tank.getMaxHP(),
                Attack.getMaxHP()
        ));
    }

    @Test
    public void checkTrucksRepairToDifferentLevels() {
        FireTruck fireTruck1 = new FireTruck(gameScreenMock, new Vector2(9,10), Speed);
        FireTruck fireTruck2 = new FireTruck(gameScreenMock, new Vector2(10,10), Ocean);
        FireTruck fireTruck3 = new FireTruck(gameScreenMock, new Vector2(9,10), Attack);
        FireTruck fireTruck4 = new FireTruck(gameScreenMock, new Vector2(10,10), Tank);
        FireStation fireStation = new FireStation(8, 10);

        fireStation.spawn(fireTruck1);
        fireStation.spawn(fireTruck2);
        //#Assessment3
        fireStation.spawn(fireTruck3);
        fireStation.spawn(fireTruck4);

        fireTruck1.repair(Speed.getMaxHP()*-1);
        fireTruck2.repair(Ocean.getMaxHP()*-1);
        //#Assessment3
        fireTruck3.repair(Attack.getMaxHP()*-1);
        fireTruck4.repair(Tank.getMaxHP()*-1);

        float fireTruck1Health0 = fireTruck1.getHP();
        float fireTruck2Health0 = fireTruck2.getHP();
        //#Assessment3
        float fireTruck3Health0 = fireTruck3.getHP();
        float fireTruck4Health0 = fireTruck4.getHP();

        for (int i=0; i<3000; i++) {
            fireStation.restoreTrucks();
        }

        //#Assessent3
        boolean checkHealth0IsSame = (fireTruck1Health0 == fireTruck2Health0) && (fireTruck2Health0 == fireTruck3Health0) && (fireTruck3Health0 == fireTruck4Health0);
        boolean checkSpeedTruckIsNotFullyRepaired = fireTruck1.getHP() !=  Speed.getMaxHP();
        boolean checkOceanTruckIsFullyRepaired = fireTruck2.getHP() == Ocean.getMaxHP();
        boolean checkAttackTruckIsFullyRepaired = fireTruck3.getHP() == Attack.getMaxHP();
        boolean checkTankTruckIsNotFullyRepaired = fireTruck4.getHP() != Tank.getMaxHP();

        assertTrue(checkHealth0IsSame
                && checkSpeedTruckIsNotFullyRepaired
                && checkOceanTruckIsFullyRepaired
                && checkAttackTruckIsFullyRepaired
                && checkTankTruckIsNotFullyRepaired
        );
    }

    @Test
    public void differentRangeTest() {
        //#Assessment3
        assertTrue(allNotEqual(
                Ocean.getRange(),
                Speed.getRange(),
                Tank.getRange(),
                Attack.getRange()
        ));
    }

    @Test
    public void checkDifferentRangeTest() {
        FireTruck fireTruck1 = new FireTruck(gameScreenMock, new Vector2(10, 15), Speed); // Range = 5
        FireTruck fireTruck2 = new FireTruck(gameScreenMock, new Vector2(10, 15), Ocean); // Range = 6
        FireTruck fireTruck3 = new FireTruck(gameScreenMock, new Vector2(10, 15), Tank); // Range = 7
        FireTruck fireTruck4 = new FireTruck(gameScreenMock, new Vector2(10, 15), Attack); // Range = 4

        Fortress fortress = new Fortress(10, 10, FortressType.Clifford);
        System.out.println(fireTruck1.fortressInRange(fortress.getPosition()));
        System.out.println(fireTruck2.fortressInRange(fortress.getPosition()));
        System.out.println(fireTruck3.fortressInRange(fortress.getPosition()));
        System.out.println(fireTruck4.fortressInRange(fortress.getPosition()));

        assertTrue(
                fireTruck1.fortressInRange(fortress.getPosition()) != fireTruck2.fortressInRange(fortress.getPosition()) &&
                fireTruck3.fortressInRange(fortress.getPosition()) != fireTruck4.fortressInRange(fortress.getPosition())
        );
    }

    @Test
    public void truckShouldDecreaseHealthOfFortress() {
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), Speed);
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate);
        fireTruck.setAttacking(true);
        float healthBefore = fortress.getHP();
        fireTruck.attack(fortress);
        for (int i=0; i<200; i++) {
            fireTruck.updateSpray();
        }
        float healthAfter = fortress.getHP();
        assertTrue(healthBefore > healthAfter);
    }

    @Test
    public void truckShouldDecreaseReserveWhenAttackingFortress() {
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), Speed);
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate);
        fireTruck.setAttacking(true);
        float reserveBefore = fireTruck.getReserve();
        fireTruck.attack(fortress);
        for (int i=0; i<100; i++) {
            fireTruck.updateSpray();
        }
        float reserveAfter = fireTruck.getReserve();
        assertTrue(reserveBefore > reserveAfter);
    }

    @Test
    public void damageFortressWithSpeedByDamageTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), Speed);
        fireTruck.setAttacking(true);
        fireTruck.attack(fortress);
        for (int i=0; i<200; i++) {
            fireTruck.updateSpray();
        }
        float fortressHealthAfter = fortress.getHP();
        assertEquals(FortressType.Walmgate.getMaxHP() - Speed.getAP(), fortressHealthAfter, 1.0);
    }

    @Test
    public void damageFortressWithSpeedByReserveTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), Speed);
        fireTruck.setAttacking(true);
        fireTruck.attack(fortress);
        for (int i=0; i<100; i++) {
            fireTruck.updateSpray();
        }
        float fireTruckReserveAfter = fireTruck.getReserve();
        assertEquals(Speed.getMaxReserve() - Speed.getAP(), fireTruckReserveAfter, 0.0);
    }

    @Test
    public void damageFortressWithOceanByDamageTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), Ocean);
        fireTruck.setAttacking(true);
        fireTruck.attack(fortress);
        for (int i=0; i<200; i++) {
            fireTruck.updateSpray();
        }
        float fortressHealthAfter = fortress.getHP();
        assertEquals(FortressType.Walmgate.getMaxHP() - Ocean.getAP(), fortressHealthAfter, 1.0);
    }

    @Test
    public void damageFortressWithOceanByReserveTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), Ocean);
        fireTruck.setAttacking(true);
        fireTruck.attack(fortress);
        for (int i=0; i<100; i++) {
            fireTruck.updateSpray();
        }
        float fireTruckReserveAfter = fireTruck.getReserve();
        assertEquals(Ocean.getMaxReserve() - Ocean.getAP(), fireTruckReserveAfter, 0.0);
    }

    @Test
    public void damageFortressWithTankByDamageTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), Tank);
        fireTruck.setAttacking(true);
        fireTruck.attack(fortress);
        for (int i=0; i<200; i++) {
            fireTruck.updateSpray();
        }
        float fortressHealthAfter = fortress.getHP();
        assertEquals(FortressType.Walmgate.getMaxHP() - Tank.getAP(), fortressHealthAfter, 1.0);
    }

    @Test
    public void damageFortressWithTankByReserveTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), Tank);
        fireTruck.setAttacking(true);
        fireTruck.attack(fortress);
        for (int i=0; i<100; i++) {
            fireTruck.updateSpray();
        }
        float fireTruckReserveAfter = fireTruck.getReserve();
        assertEquals(Tank.getMaxReserve() - Tank.getAP(), fireTruckReserveAfter, 1.0);
    }

    @Test
    public void damageFortressWithAttackByDamageTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), Attack);
        fireTruck.setAttacking(true);
        fireTruck.attack(fortress);
        for (int i=0; i<200; i++) {
            fireTruck.updateSpray();
        }
        float fortressHealthAfter = fortress.getHP();
        assertEquals(FortressType.Walmgate.getMaxHP() - Attack.getAP(), fortressHealthAfter, 1.0);
    }

    @Test
    public void damageFortressWithAttackByReserveTest() {
        Fortress fortress = new Fortress(10, 10, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10, 10), Attack);
        fireTruck.setAttacking(true);
        fireTruck.attack(fortress);
        for (int i=0; i<100; i++) {
            fireTruck.updateSpray();
        }
        float fireTruckReserveAfter = fireTruck.getReserve();
        assertEquals(Attack.getMaxReserve() - Attack.getAP(), fireTruckReserveAfter, 1.0);
    }

    @Test
    public void moveTest() {
        FireTruck fireTruck = new FireTruck(gameScreenMock, new Vector2(10,10), Speed);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,10);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,11);
        fireTruck.setMoving(true);
        fireTruck.addTileToPath(new Vector2(10,10));
        fireTruck.addTileToPath(new Vector2(10,11));
        for (int i=0; i<50; i++) {
            fireTruck.move();
        }
        Vector2 expectedPosition = new Vector2(10, 11);
        assertEquals(expectedPosition, fireTruck.getPosition());
    }
}