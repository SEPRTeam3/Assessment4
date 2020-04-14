package com.mozarellabytes.kroy.Save;

import com.badlogic.gdx.utils.Queue;
import com.mozarellabytes.kroy.Entities.Alien;
import com.mozarellabytes.kroy.Entities.FireStation;
import com.mozarellabytes.kroy.Entities.Fortress;
import com.mozarellabytes.kroy.Entities.PowerUps;
import com.mozarellabytes.kroy.GameState;

import java.util.ArrayList;


public class Save {
    public Save() {}

    public String saveName;
    public GameState gameState;
    public SaveStation station;
    public ArrayList<SaveAlien> aliens;
    public SaveAlien crazyAlien;
}
