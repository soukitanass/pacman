package ca.usherbrooke.pacman.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.google.gson.Gson;
import ca.usherbrooke.pacman.model.exceptions.GameObjectCannotChangeDirectionException;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.exceptions.MovementManagerNotFoundException;
import ca.usherbrooke.pacman.model.random.RandomDirectionGenerator;
import ca.usherbrooke.pacman.model.sound.Observer;
import ca.usherbrooke.pacman.threads.PhysicsThread;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class GameModel implements IGameModel {
  private static final int IS_LEVEL_COMPLETED_PERIOD = 20;
  private static final int GHOSTS_DIRECTION_CHANGE_PERIOD = 3;
  private static final int RANDOM_GENERATOR_SEED = 8544574;
  private static final int JOIN_TIMER = 1000; // ms

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
  private GameState gameState = GameState.GAME_MENU;
  private PacMan pacman;
  private PacmanPacgumCollisionManager pacmanPacgumCollisionManager;
  private PacmanSuperPacgumCollisionManager pacmanSuperPacgumCollisionManager;
  private List<Observer> observers = new ArrayList<>();
  Random randomNumberGenerator = new Random(RANDOM_GENERATOR_SEED);
  IDirectionGenerator randomDirectionGenerator =
      new RandomDirectionGenerator(randomNumberGenerator);
  private List<PeriodicDirectionManager> ghostDirectionManagers;
  private int isLevelCompletedUpdatesCounter = 0;
  private Queue<Level> moveQueue = new ConcurrentLinkedQueue<>();
  private Queue<GameEventObject> eventQueue = new ConcurrentLinkedQueue<>();
  private PhysicsThread physicsThread = new PhysicsThread(moveQueue, eventQueue);

  public GameModel() {
    physicsThread.start();
  }

  public void attach(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void onInterruption() {
    for (Observer observer : observers) {
      observer.onGameInterruption();
    }
  }

  @Override
  public void consumingPacGums() {
    for (Observer observer : observers) {
      observer.consumingPacGums();
    }
  }

  @Override
  public void consumingGhost() {
    for (Observer observer : observers) {
      observer.consumingGhost();
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
    boolean isGameInProgress =
        !isPaused() && !isGameCompleted() && !isGameOver() && gameState == GameState.GAME;
    if (!isGameInProgress) {
      onInterruption();
      return;
    }
    Level level = getCurrentLevel();
    if (level.isCompleted()) {
      isLevelCompleted = true;
      onInterruption();
      updateIsLevelCompleted();
      return;
    }
    ++currentGameFrame;
    if (!isGameStarted) {
      initializeLevel();
    }

    moveQueue.add(getCurrentLevel());

    synchronized (eventQueue) {
      while (!eventQueue.isEmpty()) {
        GameEventObject gameEventObject = eventQueue.poll();
        if (gameEventObject.getGameEvent() == GameEvent.PACGUM_CONSUMED) {
          pacmanPacgumCollisionManager.update();
          consumingPacGums();
        } else {
          movingToEmptySpace();
        }
        if (gameEventObject.getGameEvent() == GameEvent.SUPER_PACGUM_CONSUMED) {
          pacmanSuperPacgumCollisionManager.update();
        }
        if (gameEventObject.getGameEvent() == GameEvent.PACMAN_GHOST_COLLISON) {
          pacmanGhostCollisionManager.update();
          consumingGhost();
        }
        if (gameEventObject.getGameEvent() == GameEvent.PACMAN_MOVE) {
          IGameObject gameObject = gameEventObject.getGameObject();
          gameObject.setPosition(gameEventObject.getPosition());
        }
        if (gameEventObject.getGameEvent() == GameEvent.GHOST_MOVE) {
          
        }
      }
    }

    updateGameObjectsPosition();
  }

  private void goToNextLevel() {
    isLevelCompleted = false;
    levelsList.incrementCurrentLevel();
    initializeLevel();
    updateGameObjectsPosition();
  }

  private void updateIsLevelCompleted() {
    ++isLevelCompletedUpdatesCounter;
    if (isLevelCompletedUpdatesCounter != IS_LEVEL_COMPLETED_PERIOD) {
      return;
    }
    isLevelCompletedUpdatesCounter = 0;
    goToNextLevel();
  }

  private void updateGameObjectsPosition() {
   // pacman.setPosition(position);
  //  pacmanMovementManager.updatePosition();
    for (PeriodicDirectionManager ghostDirectionManager : ghostDirectionManagers) {
      ghostDirectionManager.update();
    }
    for (MovementManager ghostMovementManager : ghostMovementManagers) {
      ghostMovementManager.updatePosition();
    }
  }

  private void initializeLevel() {
    Level level = getCurrentLevel();
    Level initialLevel = getCurrentLevel();
    IMoveValidator pacmanMoveValidator = new PacmanMoveValidator(level);
    IMoveValidator ghostMoveValidator = new GhostMoveValidator(level);
    pacman = level.getPacMan();
    pacmanMovementManager = new MovementManager(pacman, pacmanMoveValidator);

    ghostMovementManagers = new ArrayList<>();
    ghostDirectionManagers = new ArrayList<>();

    for (Ghost ghost : level.getGhosts()) {
      ghostDirectionManagers.add(new PeriodicDirectionManager(this, randomDirectionGenerator, ghost,
          GHOSTS_DIRECTION_CHANGE_PERIOD));
      ghostMovementManagers.add(new MovementManager(ghost, ghostMoveValidator));
    }

    pacmanPacgumCollisionManager = new PacmanPacgumCollisionManager(level);
    pacmanSuperPacgumCollisionManager = new PacmanSuperPacgumCollisionManager(level);
    pacmanGhostCollisionManager = new PacmanGhostCollisionManager(level, initialLevel);

    isGameStarted = true;
    isGameOver = false;
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
    stopPhysicsThread();
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
    gameObject.setDesiredDirection(direction);
  //  MovementManager movementManager;
 //   try {
  //    movementManager = getMovementManagerFromGameObject(gameObject);
 //   } catch (MovementManagerNotFoundException e) {
 //     throw new GameObjectCannotChangeDirectionException(
 //         "Could not find a movement manager for the given game object", e);
 //   }
 //   movementManager.setDirection(direction);
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

  @Override
  public boolean isGameCompleted() {
    return levelsList.isGameCompleted();
  }

  public void stopPhysicsThread() {
    if (physicsThread == null) {
      return;
    }

    try {
      physicsThread.stopThread();
      physicsThread.join(JOIN_TIMER);
      if (physicsThread.isAlive()) {
        throw new InterruptedException();
      }
    } catch (InterruptedException exception) {
      physicsThread.interrupt();
      WarningDialog.display("Error stoping physicsThread. ", exception);
    }
  }

  @Override
  public GameState getGameState() {
    return this.gameState;
  }

  @Override
  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

}
