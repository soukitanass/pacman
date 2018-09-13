package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import com.google.gson.Gson;

public class GameModel implements IGameModel {
    private final String LEVELS_PATH = "src\\main\\java\\resources\\Levels.json";
    private static Level levels;

    public void update() {

    }

    public void loadLevels() {
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(LEVELS_PATH));
            levels = gson.fromJson(br, Level.class);
            // TODO Faire quelque chose avec!
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
