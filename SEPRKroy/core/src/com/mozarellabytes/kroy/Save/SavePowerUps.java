package com.mozarellabytes.kroy.Save;

import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.Entities.PowerUps;

import java.util.ArrayList;
import java.util.HashMap;

public class SavePowerUps {
    public HashMap<String, Boolean> powerUpPositionSpawn = new HashMap<>();
    public HashMap<String, Boolean> itemBoxSpawn = new HashMap<>();
    public ArrayList<Vector2> stickyRoadPositions = new ArrayList<>();

    public PowerUps.PowerUp state;
    public PowerUps.PowerUp leftstate;
    public PowerUps.PowerUp rightstate;
    public boolean spawning;
    public  boolean isInvisTimer;

}
