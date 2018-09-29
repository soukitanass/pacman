package ca.usherbrooke.pacman.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import ca.usherbrooke.pacman.model.exceptions.GameObjectCannotChangeDirectionException;
import ca.usherbrooke.pacman.model.exceptions.MovementManagerNotFoundException;
import ca.usherbrooke.pacman.model.sound.Observer;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class GameModel implements IGameModel {

  static final int IS_LEVEL_COMPLETED_TIMEOUT = 3000;

  private Levels levelsList;
  private int currentGameFrame = 0;
  private boolean isManuallyPaused = false;
  private boolean isPaused;
  private boolean isRunning;
  private boolean isLevelCompleted;
  private boolean isGameOver;
  private MovementManager pacmanMovementManager;
  private List<MovementManager> ghostMovementManagers;
  private PacmanGhostCollisionManager pacmanGhostCollisionManager;
  private boolean isGameStarted = false;
  private PacMan pacman;
  private PacmanPacgumCollisionManager pacmanPacgumCollisionManager;
  private PacmanSuperPacgumCollisionManager pacmanSuperPacgumCollisionManager;
  private List<Observer> observers = new ArrayList<>();


  public void attach(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void onLevelCompleted() {
    for (Observer observer : observers) {
      observer.onLevelCompleted();
    }
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
    if (isPaused() || isLevelCompleted) {
      onLevelCompleted();
      return;
    }
    ++currentGameFrame;
    if (!isGameStarted()) {
      startLevel();
    }
    if (pacmanPacgumCollisionManager.isPacgumConsumed()) {
      consumingPacGums();
    } else {
      movingToEmptySpace();
    }
    pacmanSuperPacgumCollisionManager.update();
    pacmanGhostCollisionManager.update();
    pacmanMovementManager.updatePosition();
    for (MovementManager ghostMovementManager : ghostMovementManagers) {
      ghostMovementManager.updatePosition();
    }

    Level level = getCurrentLevel();
    if (level.isCompleted()) {
      isLevelCompleted = true;
      setTimeout(this::startNextLevel, IS_LEVEL_COMPLETED_TIMEOUT);
    }
  }

  private void setTimeout(Runnable runnable, int delay) {
    new Thread(() -> {
      try {
        Thread.sleep(delay);
        runnable.run();
      } catch (Exception exception) {
        WarningDialog.display("Error while setting timeout. ", exception);
      }
    }).start();
  }

  private void startLevel() {
    Level level = getCurrentLevel();
    IMoveValidator pacmanMoveValidator = new PacmanMoveValidator(level);
    IMoveValidator ghostMoveValidator = new GhostMoveValidator(level);
    pacman = level.getPacMan();
    pacmanMovementManager = new MovementManager(pacman, pacmanMoveValidator);
    ghostMovementManagers = new ArrayList<MovementManager>();
    for (Ghost ghost : level.getGhost()) {
      ghostMovementManagers.add(new MovementManager(ghost, ghostMoveValidator));
    }
    pacmanPacgumCollisionManager = new PacmanPacgumCollisionManager(pacman, level);
    pacmanSuperPacgumCollisionManager = new PacmanSuperPacgumCollisionManager(pacman, level);
    pacmanGhostCollisionManager = new PacmanGhostCollisionManager(pacman, level);
    isGameStarted = true;
    isGameOver = false;
  }

  private void startNextLevel() {
    this.isLevelCompleted = false;
    this.levelsList.incrementCurrentLevel();
    startLevel();
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
  public int getCurrentLevelIndex() {
    return levelsList.getCurrentLevel();
  }

  @Override
  public boolean isLevelCompleted() {
    return isLevelCompleted;
  }

  @Override
  public boolean isPaused() {
    return isPaused;
  }

  @Override
  public void togglePause(boolean isManuallyPaused) {

    if (isManuallyPaused) {
      setManuallyPaused(!isManuallyPaused());
    }

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

  public void loadLevels(String levelsPath) {
    Gson gson = new Gson();
    File file = new File(GameModel.class.getClassLoader().getResource(levelsPath).getFile());

    try (FileReader fileReader = new FileReader(file)) {
      this.levelsList = gson.fromJson(new BufferedReader(fileReader), Levels.class);
      this.pacman = getCurrentLevel().getPacMan();
    } catch (Exception exception) {
      WarningDialog.display("Error while opening level file. ", exception);
    }
  }

  @Override
  public PacMan getPacman() {
    return pacman;
  }

  @Override
  public void setDirection(IHasDesiredDirection gameObject, Direction direction)
      throws GameObjectCannotChangeDirectionException {
    if (isPaused()) {
      return;
    }
    MovementManager movementManager;
    try {
      movementManager = getMovementManagerFromGameObject(gameObject);
    } catch (MovementManagerNotFoundException e) {
      throw new GameObjectCannotChangeDirectionException(
          "Could not find a movement manager for the given game object", e);
    }
    movementManager.setDirection(direction);
  }

  @Override
  public void setManuallyPaused(boolean isManuallyPaused) {
    this.isManuallyPaused = isManuallyPaused;
  }

  @Override
  public boolean isManuallyPaused() {
    return isManuallyPaused;
  }

  private MovementManager getMovementManagerFromGameObject(IHasDesiredDirection gameObject)
      throws MovementManagerNotFoundException {
    if (gameObject == pacmanMovementManager.getManagedGameObject()) {
      return pacmanMovementManager;
    }
    for (MovementManager ghostMovementManager : ghostMovementManagers) {
      if (gameObject == ghostMovementManager.getManagedGameObject()) {
        return ghostMovementManager;
      }
    }
    throw new MovementManagerNotFoundException(
        "Could not find a movement manager for the given game object");
  }

  public boolean isGameOver() {
    return isGameOver;
  }

  public void setGameOver() {
    isGameOver = true;
  }

}
