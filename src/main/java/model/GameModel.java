package model;

import com.google.gson.Gson;
import model.sound.Observer;
import view.utilities.WarningDialog;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.IIOException;

public class GameModel implements IGameModel {
  private Levels levelsList;

  private int currentGameFrame = 0;
  private boolean isPaused;
  private boolean isRunning;
  private MovementManager movementManager;
  private boolean isGameStarted = false;
  private PacMan pacman;
  private PacmanPacgumCollisionManager pacmanPacgumCollisionManager;
  private PacmanSuperPacgumCollisionManager pacmanSuperPacgumCollisionManager;
  private List<Observer> observers = new ArrayList<>();

  public void attach(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void consumingPacGums() {
    for (Observer observer : observers) {
      observer.consumingPacGums();
    }
  }

  @Override
  public void movingToEmptySpace() {
    for (Observer observer : observers) {
      observer.movingToEmptySpace();
    }
  }

  @Override
  public void update() {
    if (isPaused()) {
      return;
    }
    ++currentGameFrame;
    if (!isGameStarted()) {
      startGame();
    }
    if (pacmanPacgumCollisionManager.isPacgumConsumed()) {
      consumingPacGums();
    } else {
      movingToEmptySpace();
    }
    pacmanSuperPacgumCollisionManager.update();
    movementManager.updatePacmanPosition();
  }

  private void startGame() {
    Level level = getCurrentLevel();
    pacman = level.getPacMan();
    IMoveValidator moveValidator = new MoveValidator(level);
    movementManager = new MovementManager(pacman, moveValidator);
    pacmanPacgumCollisionManager = new PacmanPacgumCollisionManager(pacman, level);
    pacmanSuperPacgumCollisionManager = new PacmanSuperPacgumCollisionManager(pacman, level);
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

  public void loadLevels(String levelsPath) {
    Gson gson = new Gson();
    BufferedReader br = null;
    try {
      br = new BufferedReader(
          new InputStreamReader(this.getClass().getResourceAsStream("/" + levelsPath)));
      this.levelsList = gson.fromJson(br, Levels.class);
    } catch (Exception exception) {
      WarningDialog.display("Error while opening level file. ", exception);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException exception) {
          WarningDialog.display("Error while closing level file. ", exception);
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
