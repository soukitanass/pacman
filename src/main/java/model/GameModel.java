package model;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GameModel implements IGameModel {
  private final String LEVELS_PATH = "src\\main\\res\\Levels.json";
  private Levels levelsList;

  private int currentGameFrame = 0;
  private boolean isPaused;
  private boolean isRunning;

    private void updatePacmanPosition() {
    	Level level = this.getCurrentLevel();
    	if (level == null) {
    	    return;
    	}
    	PacMan pacman = level.getPacMan();
    	int width = level.getWidth();
    	int height = level.getHeight();
    	pacman.updatePosition(width, height);
    }

    @Override
    public void update() {
        if (isPaused()) {
            return;
        }
        ++currentGameFrame;
        updatePacmanPosition();
    }
    ++currentGameFrame;
  }

  @Override
  public int getCurrentGameFrame() {
    return currentGameFrame;
  }

  @Override
  public void pause() {
    isPaused = true;
  }

  @Override
  public void unpause() {
    isPaused = false;
  }

  @Override
  public boolean isPaused() {
    return isPaused;
  }

  @Override
  public void togglePause() {
    if (isPaused()) {
      unpause();
    } else {
      pause();
    }
  }

  @Override
  public void quit() {
    setRunning(false);
  }

  @Override
  public boolean isRunning() {
    return isRunning;
  }

  @Override
  public void setRunning(boolean isRunning) {
    this.isRunning = isRunning;
  }

  public Level getCurrentLevel() {
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
