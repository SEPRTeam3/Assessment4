package com.mozarellabytes.kroy.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.mozarellabytes.kroy.Screens.GameScreen;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Stream;

import static com.badlogic.gdx.utils.JsonWriter.OutputType.json;

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
}
