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

public class SaveManager {

//    public final JsonWriter writer = new JsonWriter(java.io.Writer);

    public static void newSave(Save s) throws IOException {
        Json json = new Json();
        System.out.println(json.toJson(s));
        FileHandle file = Gdx.files.local("saves/testSave.json");
        file.writeString(json.toJson(s), false);
    }

    public static Save loadSave(FileHandle file) throws IOException{
//        FileReader fileReader = new FileReader("saves/testSave.json");
        Json json = new Json();
        Save s = json.fromJson(Save.class, file.readString());
        System.out.println("Read" + s.saveName);
        return s;
    }

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
            saveA.path = a.mainPatrol.getPath();
            s.aliens.add(saveA);
        }

        // Add crazy alien
        SaveAlien saveC = new SaveAlien();
        saveC.x = g.getCrazyAlien().getPosition().x;
        saveC.y = g.getCrazyAlien().getPosition().y;
        saveC.HP = g.getCrazyAlien().getHP();
        saveC.path = new Queue<>();
        saveC.path.addLast(g.getCrazyAlien().path.first());
        s.crazyAlien = saveC;

        return s;
    }

    public static ArrayList<Save> getSaves() {
        ArrayList<Save> out = new ArrayList();
        for (FileHandle file : Gdx.files.local("saves").list()) {
            try {
            out.add(loadSave(file));
            } catch(IOException e) {
                System.out.println("Couldn't be read.");
            }
        };
        return out;
    }
}
