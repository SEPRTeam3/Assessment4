package com.mozarellabytes.kroy.Save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.mozarellabytes.kroy.Entities.FireTruck;
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

    public static Save loadSave() throws IOException{
//        FileReader fileReader = new FileReader("saves/testSave.json");
        Json json = new Json();
        FileHandle file = Gdx.files.local("saves/testSave.json");
        Save s = json.fromJson(Save.class, file.readString());
        System.out.println("Read" + s.saveName);
        return s;
    }

    public static Save saveFromGame(GameScreen g) {
        Save s = new Save();

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

            s.station.trucks.add(saveF);
        }
        return s;
    }
}
