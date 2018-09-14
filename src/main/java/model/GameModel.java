package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;

public class GameModel implements IGameModel {
	private final String LEVELS_PATH = "src\\main\\res\\Levels.json";
	private Levels levelsList;
	
	public void update() {

	}
	
	public Level getCurrentLevel()
	{
		final int currentLevel = this.levelsList.getCurrentLevel();
		final List<Level> levels = this.levelsList.getLevels();
		return levels.get(currentLevel);
	}
	
	public void loadLevels() {
		Gson gson = new Gson();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(LEVELS_PATH));
			this.levelsList = gson.fromJson(br, Levels.class);
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
