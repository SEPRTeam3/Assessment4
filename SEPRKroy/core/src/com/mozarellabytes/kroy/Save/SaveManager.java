package com.mozarellabytes.kroy.Save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Queue;
import com.mozarellabytes.kroy.Entities.Alien;
import com.mozarellabytes.kroy.Entities.FireTruck;
import com.mozarellabytes.kroy.Kroy;
import com.mozarellabytes.kroy.Screens.GameScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * #Assessment4
 * The save manager is a container class for static methods that are used when saving and loading the game
 */
public class SaveManager {

    /**
     * #Assessment4
     * creates a json file in the 'saves' folder given the information of a save object
     * @param s the save to be written to json
     * @throws IOException if the filesystem does not allow writing the saving can fail
     */
    public static void newSave(Save s) throws IOException {
        Json json = new Json();
        System.out.println(json.toJson(s));
        FileHandle file = Gdx.files.local("saves/" + s.saveName + ".json");
        file.writeString(json.toJson(s), false);
    }

    /**
     * #Assessment4
     * Loads a json file from the 'saves' folder of a given name and returns the save object it encodes
     * @param file the filepath of the save to open
     * @return s the save read from json
     * @throws IOException
     */
    public static Save loadSave(FileHandle file) throws IOException{
        Json json = new Json();
        Save s = json.fromJson(Save.class, file.readString());
        System.out.println("Read" + s.saveName);
        return s;
    }

    /**
     * #Assessment4
     * Creates a save object that encodes the state of gamescreen g where the savename is name
     * @param g the GameScreen to encode the state of
     * @param name the savename that should display in the save menu
     * @return s the save object that encodes the state of gamescreen g
     */
    public static Save saveFromGame(GameScreen g, String name) {
        Save s = new Save();

        // Add name
        s.saveName = name;

        // Add GameState
        s.gameState = g.gameState;

        // Add station
        s.station = new SaveStation();
        s.station.x = g.STATION_X;
        s.station.y = g.STATION_Y;
        s.station.destroyed = g.getStation().isDestroyed();
        s.station.trucks = new ArrayList();

        // Add firetrucks to station
        for (FireTruck f : g.getStation().getTrucks()) {
            SaveFiretruck saveFiretruck = new SaveFiretruck();
            saveFiretruck.x = f.getPosition().x;
            saveFiretruck.y = f.getPosition().y;
            saveFiretruck.HP = f.getHP();
            saveFiretruck.type = f.type;
            s.station.trucks.add(saveFiretruck);
        }

        s.aliens = new ArrayList();

        // Add aliens
        for (Alien a : g.getAliens()) {
            SaveAlien saveAlien = new SaveAlien();
            saveAlien.x = a.getPosition().x;
            saveAlien.y = a.getPosition().y;
            saveAlien.HP = a.getHP();
            saveAlien.speed = a.getSpeed();
            saveAlien.waypoints = a.getWaypoints();
            saveAlien.waypointIndex = a.getWaypointIndex();
            saveAlien.state = a.getState();
            s.aliens.add(saveAlien);
        }

        return s;
    }

    /**
     * #Assessment4
     * Gets a list of all the saves that are currently stored as json files in the saves folder
     * @return a map where the key is the savename and the datum is the save object
     */
    public static Map<String, Save> getSaves() {
        HashMap<String, Save> out = new HashMap<>();
        for (FileHandle file : Gdx.files.local("saves").list()) {
            try {
            Save s = loadSave(file);
            out.put(s.saveName, s);
            } catch(IOException e) {
                System.out.println("Couldn't be read.");
            }
        };
        return out;
    }


}
