package model;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GameModel implements IGameModel {
  private Levels levelsList;

  private int currentGameFrame = 0;
  private boolean isPaused;
  private boolean isRunning;
  private MovementManager movementManager;
  private boolean isGameStarted = false;
  private PacMan pacman;

  @Override
  public void update() {
    if (isPaused()) {
      return;
    }
    ++currentGameFrame;
    if(!isGameStarted()) {
      startGame();
    }
    movementManager.updatePacmanPosition();
  }

  private void startGame() {
    Level level = getCurrentLevel();
    pacman = new PacMan();
    pacman.setPosition(new Position(5, 5));
    IMoveValidator moveValidator = new MoveValidator(level);
    movementManager = new MovementManager(level, pacman, moveValidator);
    isGameStarted = true;
  }

  private boolean isGameStarted() {
    return isGameStarted;
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
    if (this.levelsList == null) {
      return null;
    }
    final int currentLevel = this.levelsList.getCurrentLevel();
    final List<Level> levels = this.levelsList.getLevels();
    return levels.get(currentLevel);
  }

  public void loadLevels(String LEVELS_PATH) {
    Gson gson = new Gson();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(LEVELS_PATH));
      this.levelsList = gson.fromJson(br, Levels.class);
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

  @Override
  public PacMan getPacman() {
    return pacman;
  }

  @Override
  public void setPacmanDirection(Direction direction) {
    movementManager.setPacmanDirection(direction);
  }
}
