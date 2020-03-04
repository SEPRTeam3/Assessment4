package com.mozarellabytes.kroy.Utilities;

import com.badlogic.gdx.utils.Queue;
import com.mozarellabytes.kroy.Entities.Alien;
import com.mozarellabytes.kroy.Entities.FireStation;
import com.mozarellabytes.kroy.Entities.Fortress;
import com.mozarellabytes.kroy.GameState;

import java.util.ArrayList;


public class Save {
    public Save() {}

    public String saveName;
    public GameState gameState;
    public ArrayList<Fortress> fortresses;
    public FireStation station;
    public Queue<Alien> aliens;
}
