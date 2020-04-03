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
     * The save manager is a container class for static methods that are used when saving and loading the game
     */
public class SaveManager {

    /**
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
        s.station.trucks = new ArrayList();

        // Add firetrucks to station
        for (FireTruck f : g.getStation().getTrucks()) {
            SaveFiretruck saveF = new SaveFiretruck();
            saveF.x = f.getPosition().x;
            saveF.y = f.getPosition().y;
            saveF.HP = f.getHP();
            saveF.type = f.type;
            s.station.trucks.add(saveF);
        }

        s.aliens = new ArrayList();

        // Add aliens
        for (Alien a : g.getAliens()) {
            SaveAlien saveA = new SaveAlien();
            saveA.x = a.getPosition().x;
            saveA.y = a.getPosition().y;
            saveA.HP = a.getHP();
            saveA.speed = a.getSpeed();
            //saveA.path = a.getPath();
            s.aliens.add(saveA);
        }

        // Add crazy alien
        SaveAlien saveC = new SaveAlien();
        saveC.x = g.getCrazyAlien().getPosition().x;
        saveC.y = g.getCrazyAlien().getPosition().y;
        saveC.HP = g.getCrazyAlien().getHP();
        saveC.path = new ArrayList<>();
        saveC.path.add(g.getCrazyAlien().path.first());
        s.crazyAlien = saveC;

        //

        return s;
    }

    /**
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
